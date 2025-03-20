/*
 * Copyright 2018-2025 NAVER Corp.
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

import java.util.HashMap;
import java.util.Map;

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
import com.naver.maps.map.clustering.DefaultLeafMarkerUpdater;
import com.naver.maps.map.clustering.LeafMarkerInfo;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MapConstants;
import com.naver.maps.map.util.MarkerIcons;

public class ClusteringActivity extends AppCompatActivity implements OnMapReadyCallback {
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

    @NonNull
    private static final OverlayImage[] ICONS = {
        Marker.DEFAULT_ICON, MarkerIcons.BLUE, MarkerIcons.RED, MarkerIcons.YELLOW
    };

    @Nullable
    private Clusterer<ItemKey> clusterer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_fragment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .camera(new CameraPosition(MapConstants.EXTENT_KOREA.getCenter(), 4)));
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
        clusterer = new Clusterer.Builder<ItemKey>()
            .leafMarkerUpdater(new DefaultLeafMarkerUpdater() {
                @Override
                public void updateLeafMarker(@NonNull LeafMarkerInfo info, @NonNull Marker marker) {
                    super.updateLeafMarker(info, marker);
                    assert info.getTag() != null;
                    marker.setIcon(ICONS[(int)info.getTag()]);
                    marker.setOnClickListener(o -> {
                        if (clusterer != null) {
                            clusterer.remove((ItemKey)info.getKey());
                        }
                        return true;
                    });
                }
            })
            .build();

        double south = MapConstants.EXTENT_KOREA.getSouthLatitude();
        double west = MapConstants.EXTENT_KOREA.getWestLongitude();
        double height = MapConstants.EXTENT_KOREA.getNorthLatitude() - south;
        double width = MapConstants.EXTENT_KOREA.getEastLongitude() - west;

        Map<ItemKey, Integer> keyTagMap = new HashMap<>(5000);
        for (int i = 0; i < 5000; ++i) {
            keyTagMap.put(new ItemKey(i, new LatLng(height * Math.random() + south, width * Math.random() + west)),
                (int)(Math.random() * ICONS.length));
        }

        clusterer.addAll(keyTagMap);
        clusterer.setMap(naverMap);
    }
}
