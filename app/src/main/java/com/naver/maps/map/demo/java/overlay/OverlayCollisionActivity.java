/*
 * Copyright 2018-2022 NAVER Corp.
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
package com.naver.maps.map.demo.java.overlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.MarkerIcons;

public class OverlayCollisionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<LatLng> PATH_COORDS = Arrays.asList(
        new LatLng(37.5594084, 126.9745830),
        new LatLng(37.5599980, 126.9748245),
        new LatLng(37.5601083, 126.9748951),
        new LatLng(37.5601980, 126.9749873),
        new LatLng(37.5601998, 126.9749896),
        new LatLng(37.5602478, 126.9750492),
        new LatLng(37.5603158, 126.9751371),
        new LatLng(37.5604241, 126.9753616),
        new LatLng(37.5604853, 126.9755401),
        new LatLng(37.5605225, 126.9756157),
        new LatLng(37.5605353, 126.9756405),
        new LatLng(37.5605652, 126.9756924),
        new LatLng(37.5606143, 126.9757679),
        new LatLng(37.5606903, 126.9758432),
        new LatLng(37.5608510, 126.9758919),
        new LatLng(37.5611353, 126.9759964),
        new LatLng(37.5611949, 126.9760186),
        new LatLng(37.5612383, 126.9760364),
        new LatLng(37.5615796, 126.9761721),
        new LatLng(37.5619326, 126.9763123),
        new LatLng(37.5621502, 126.9763991),
        new LatLng(37.5622776, 126.9764492),
        new LatLng(37.5624374, 126.9765137),
        new LatLng(37.5630911, 126.9767753),
        new LatLng(37.5631345, 126.9767931),
        new LatLng(37.5635163, 126.9769240),
        new LatLng(37.5635506, 126.9769351),
        new LatLng(37.5638061, 126.9770239),
        new LatLng(37.5639153, 126.9770605),
        new LatLng(37.5639577, 126.9770749),
        new LatLng(37.5640074, 126.9770927),
        new LatLng(37.5644783, 126.9771755),
        new LatLng(37.5649229, 126.9772482),
        new LatLng(37.5650330, 126.9772667),
        new LatLng(37.5652152, 126.9772971),
        new LatLng(37.5654569, 126.9773170),
        new LatLng(37.5655173, 126.9773222),
        new LatLng(37.5656534, 126.9773258),
        new LatLng(37.5660418, 126.9773004),
        new LatLng(37.5661985, 126.9772914),
        new LatLng(37.5664663, 126.9772952),
        new LatLng(37.5668827, 126.9773047),
        new LatLng(37.5669467, 126.9773054),
        new LatLng(37.5670567, 126.9773080),
        new LatLng(37.5671360, 126.9773097),
        new LatLng(37.5671910, 126.9773116),
        new LatLng(37.5672785, 126.9773122),
        new LatLng(37.5674668, 126.9773120),
        new LatLng(37.5677264, 126.9773124),
        new LatLng(37.5680410, 126.9773068),
        new LatLng(37.5689242, 126.9772871),
        new LatLng(37.5692829, 126.9772698),
        new LatLng(37.5693829, 126.9772669),
        new LatLng(37.5696659, 126.9772615),
        new LatLng(37.5697524, 126.9772575),
        new LatLng(37.5698659, 126.9772499),
        new LatLng(37.5699671, 126.9773070),
        new LatLng(37.5700151, 126.9773395),
        new LatLng(37.5700748, 126.9773866),
        new LatLng(37.5701164, 126.9774373),
        new LatLng(37.5701903, 126.9776225),
        new LatLng(37.5701905, 126.9776723),
        new LatLng(37.5701897, 126.9777006),
        new LatLng(37.5701869, 126.9784990),
        new LatLng(37.5701813, 126.9788591),
        new LatLng(37.5701770, 126.9791139),
        new LatLng(37.5701741, 126.9792702),
        new LatLng(37.5701743, 126.9793098),
        new LatLng(37.5701752, 126.9795182),
        new LatLng(37.5701761, 126.9799315),
        new LatLng(37.5701775, 126.9800380),
        new LatLng(37.5701800, 126.9804048),
        new LatLng(37.5701832, 126.9809189),
        new LatLng(37.5701845, 126.9810197),
        new LatLng(37.5701862, 126.9811986),
        new LatLng(37.5701882, 126.9814375),
        new LatLng(37.5701955, 126.9820897),
        new LatLng(37.5701996, 126.9821860));

    private static final LatLngBounds BOUNDS = LatLngBounds.from(PATH_COORDS).buffer(200);

    private boolean forceShowIcon;
    private boolean forceShowCaption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overlay_collision);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(
                new NaverMapOptions().camera(new CameraPosition(BOUNDS.getCenter(), 14)));
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
        PathOverlay path = new PathOverlay();
        path.setCoords(PATH_COORDS);
        path.setWidth(getResources().getDimensionPixelSize(R.dimen.path_overlay_width));
        path.setOutlineWidth(getResources().getDimensionPixelSize(R.dimen.path_overlay_outline_width));
        path.setColor(ResourcesCompat.getColor(getResources(), R.color.primary, getTheme()));
        path.setOutlineColor(Color.WHITE);
        path.setMap(naverMap);

        List<Marker> markers = new ArrayList<>();

        for (int i = 0; i < 50; ++i) {
            Marker marker = new Marker();
            setImportant(marker, i < 10);
            marker.setPosition(new LatLng(
                (BOUNDS.getNorthLatitude() - BOUNDS.getSouthLatitude()) * Math.random() + BOUNDS.getSouthLatitude(),
                (BOUNDS.getEastLongitude() - BOUNDS.getWestLongitude()) * Math.random() + BOUNDS.getWestLongitude()
            ));
            marker.setCaptionText("Marker #" + i);
            marker.setOnClickListener(overlay -> {
                boolean important = (boolean)marker.getTag();
                setImportant(marker, !important);
                return true;
            });
            marker.setMap(naverMap);
            markers.add(marker);
        }

        findViewById(R.id.hide_collided_symbols).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            path.setHideCollidedSymbols(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedSymbols(checked);
            }
        });

        findViewById(R.id.hide_collided_markers).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            path.setHideCollidedMarkers(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedMarkers(checked);
            }
        });

        findViewById(R.id.hide_collided_captions).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            path.setHideCollidedCaptions(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedCaptions(checked);
            }
        });

        findViewById(R.id.force_show_icon).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            forceShowIcon = checked;
            for (Marker marker : markers) {
                boolean important = (boolean)marker.getTag();
                marker.setForceShowIcon(important && forceShowIcon);
            }
        });

        findViewById(R.id.force_show_caption).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            forceShowCaption = checked;
            for (Marker marker : markers) {
                boolean important = (boolean)marker.getTag();
                marker.setForceShowCaption(important && forceShowCaption);
            }
        });

        CheckedTextView bottom = findViewById(R.id.bottom);
        CheckedTextView edges = findViewById(R.id.edges);
        CheckedTextView apexes = findViewById(R.id.apexes);
        CheckedTextView outsides = findViewById(R.id.outsides);

        bottom.setTag(Marker.DEFAULT_CAPTION_ALIGNS);
        edges.setTag(Align.EDGES);
        apexes.setTag(Align.APEXES);
        outsides.setTag(Align.OUTSIDES);

        View.OnClickListener onCaptionAlignsClickListener = v -> {
            bottom.setChecked(bottom == v);
            edges.setChecked(edges == v);
            apexes.setChecked(apexes == v);
            outsides.setChecked(outsides == v);
            Align[] aligns = (Align[])v.getTag();
            for (Marker marker : markers) {
                marker.setCaptionAligns(aligns);
            }
        };

        bottom.setOnClickListener(onCaptionAlignsClickListener);
        edges.setOnClickListener(onCaptionAlignsClickListener);
        apexes.setOnClickListener(onCaptionAlignsClickListener);
        outsides.setOnClickListener(onCaptionAlignsClickListener);
    }

    private void setImportant(@NonNull Marker marker, boolean important) {
        marker.setIcon(important ? MarkerIcons.GREEN : MarkerIcons.GRAY);
        marker.setZIndex(important ? 1 : 0);
        marker.setTag(important);
        marker.setForceShowIcon(important && forceShowIcon);
        marker.setForceShowCaption(important && forceShowCaption);
    }
}
