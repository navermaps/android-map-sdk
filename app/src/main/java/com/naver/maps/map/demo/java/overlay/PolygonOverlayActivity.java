/*
 * Copyright 2018-2019 NAVER Corp.
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.PolygonOverlay;

public class PolygonOverlayActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<LatLng> COORDS_1 = Arrays.asList(
        new LatLng(37.5734571, 126.975335),
        new LatLng(37.5738912, 126.9825649),
        new LatLng(37.5678124, 126.9812127),
        new LatLng(37.5694007, 126.9739434));

    private static final List<LatLng> COORDS_2 = Arrays.asList(
        new LatLng(37.5640984, 126.9712268),
        new LatLng(37.5651279, 126.9767904),
        new LatLng(37.5625365, 126.9832241),
        new LatLng(37.5585305, 126.9809297),
        new LatLng(37.5590777, 126.974617));

    private static final List<List<LatLng>> HOLES = Collections.singletonList(Arrays.asList(
        new LatLng(37.5612243, 126.9768938),
        new LatLng(37.5627692, 126.9795502),
        new LatLng(37.5628377, 126.976066)));

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
            mapFragment = MapFragment.newInstance();
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
        int color = ResourcesCompat.getColor(getResources(), R.color.primary, getTheme());

        PolygonOverlay polygon = new PolygonOverlay();
        polygon.setCoords(COORDS_1);
        polygon.setColor(ColorUtils.setAlphaComponent(color, 31));
        polygon.setOutlineColor(color);
        polygon.setOutlineWidth(getResources().getDimensionPixelSize(R.dimen.overlay_line_width));
        polygon.setMap(naverMap);

        PolygonOverlay polygonWithHole = new PolygonOverlay();
        polygonWithHole.setCoords(COORDS_2);
        polygonWithHole.setHoles(HOLES);
        polygonWithHole.setColor(
            ColorUtils.setAlphaComponent(ResourcesCompat.getColor(getResources(), R.color.gray, getTheme()), 127));
        polygonWithHole.setMap(naverMap);
    }
}
