/*
 * Copyright 2018-2024 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naver.maps.map.demo.java.clustering;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.clustering.Clusterer;
import com.naver.maps.map.clustering.ClusteringKey;
import com.naver.maps.map.clustering.DefaultClusterMarkerUpdater;
import com.naver.maps.map.clustering.DefaultClusterOnClickListener;
import com.naver.maps.map.clustering.DefaultDistanceStrategy;
import com.naver.maps.map.clustering.DefaultMarkerManager;
import com.naver.maps.map.clustering.DistanceStrategy;
import com.naver.maps.map.clustering.Node;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

public class ComplexClusteringActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static class ItemKey implements ClusteringKey {
        private final int id;
        @NonNull
        private final LatLng position;

        public ItemKey(int id, @NonNull LatLng position) {
            this.id = id;
            this.position = position;
        }

        public int getId() {
            return id;
        }

        @Override
        @NonNull
        public LatLng getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            ItemKey itemKey = (ItemKey)o;

            return id == itemKey.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    private static class ItemData {
        @NonNull
        private final String name;
        @NonNull
        private final String gu;

        public ItemData(@NonNull String name, @NonNull String gu) {
            this.name = name;
            this.gu = gu;
        }

        @NonNull
        public String getName() {
            return name;
        }

        @NonNull
        public String getGu() {
            return gu;
        }
    }

    // 데이터 © 서울특별시 (CC BY)
    // 서울 열린데이터 광장 - 서울시 공중화장실 위치정보
    // http://data.seoul.go.kr/dataList/OA-1370/S/1/datasetView.do
    // 2023.04.19.
    @NonNull
    private static final String CSV_ASSET_PATH = "seoul_toilet.csv";

    @Nullable
    private Clusterer<ItemKey> clusterer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complex_clustering);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .camera(new CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 10)));
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        clusterer = new Clusterer.ComplexBuilder<ItemKey>()
            .minClusteringZoom(9)
            .maxClusteringZoom(16)
            .maxScreenDistance(200)
            .thresholdStrategy(zoom -> {
                if (zoom <= 11) {
                    return 0;
                } else {
                    return 70;
                }
            })
            .distanceStrategy(new DistanceStrategy() {
                private final DistanceStrategy defaultDistanceStrategy = new DefaultDistanceStrategy();

                @Override
                public double getDistance(int zoom, @NonNull Node node1, @NonNull Node node2) {
                    if (zoom <= 9) {
                        return -1;
                    }
                    assert node1.getTag() != null;
                    assert node2.getTag() != null;
                    if (((ItemData)node1.getTag()).getGu().equals(((ItemData)node2.getTag()).getGu())) {
                        if (zoom <= 11) {
                            return -1;
                        } else {
                            return defaultDistanceStrategy.getDistance(zoom, node1, node2);
                        }
                    }
                    return 10000;
                }
            })
            .tagMergeStrategy(cluster -> {
                if (cluster.getMaxZoom() <= 9) {
                    return null;
                } else {
                    ItemData tag = (ItemData)cluster.getChildren().get(0).getTag();
                    assert tag != null;
                    return new ItemData("", tag.gu);
                }
            })
            .markerManager(new DefaultMarkerManager() {
                @NonNull
                @Override
                public Marker createMarker() {
                    Marker marker = super.createMarker();
                    marker.setSubCaptionTextSize(10f);
                    marker.setSubCaptionColor(Color.WHITE);
                    marker.setSubCaptionHaloColor(Color.TRANSPARENT);
                    return marker;
                }
            })
            .clusterMarkerUpdater((info, marker) -> {
                int size = info.getSize();
                if (info.getMinZoom() <= 10) {
                    marker.setIcon(MarkerIcons.CLUSTER_HIGH_DENSITY);
                } else if (size < 10) {
                    marker.setIcon(MarkerIcons.CLUSTER_LOW_DENSITY);
                } else {
                    marker.setIcon(MarkerIcons.CLUSTER_MEDIUM_DENSITY);
                }
                if (info.getMinZoom() == 10) {
                    assert info.getTag() != null;
                    marker.setSubCaptionText(((ItemData)info.getTag()).getGu());
                } else {
                    marker.setSubCaptionText("");
                }
                marker.setAnchor(DefaultClusterMarkerUpdater.DEFAULT_CLUSTER_ANCHOR);
                marker.setCaptionText(Integer.toString(size));
                marker.setCaptionAligns(Align.Center);
                marker.setCaptionColor(Color.WHITE);
                marker.setCaptionHaloColor(Color.TRANSPARENT);
                marker.setOnClickListener(new DefaultClusterOnClickListener(info));
            })
            .leafMarkerUpdater((info, marker) -> {
                assert info.getTag() != null;
                marker.setIcon(Marker.DEFAULT_ICON);
                marker.setAnchor(Marker.DEFAULT_ANCHOR);
                marker.setCaptionText(((ItemData)info.getTag()).getName());
                marker.setCaptionAligns(Align.Bottom);
                marker.setCaptionColor(Color.BLACK);
                marker.setCaptionHaloColor(Color.WHITE);
                marker.setSubCaptionText("");
                marker.setOnClickListener(null);
            })
            .build();

        new Thread() {
            @Override
            public void run() {
                Map<ItemKey, ItemData> keyTagMap = new HashMap<>();
                try (BufferedReader r = new BufferedReader(new InputStreamReader(getAssets().open(CSV_ASSET_PATH)))) {
                    int i = 0;
                    String line;
                    while ((line = r.readLine()) != null) {
                        String[] split = line.split(",");
                        keyTagMap.put(
                            new ItemKey(i++, new LatLng(Double.parseDouble(split[3]), Double.parseDouble(split[2]))),
                            new ItemData(split[0], split[1]));
                    }
                } catch (Exception ignored) {
                }
                runOnUiThread(() -> {
                    clusterer.addAll(keyTagMap);
                    clusterer.setMap(naverMap);
                });
            }
        }.start();
    }
}
