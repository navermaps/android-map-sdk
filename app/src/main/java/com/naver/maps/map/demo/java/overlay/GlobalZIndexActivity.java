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
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.MarkerIcons;

public class GlobalZIndexActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<LatLng> PATH_COORDS = Arrays.asList(
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
        PathOverlay pathOverlay = new PathOverlay();
        pathOverlay.setCoords(PATH_COORDS);
        pathOverlay.setWidth(getResources().getDimensionPixelSize(R.dimen.path_overlay_width));
        pathOverlay.setColor(ResourcesCompat.getColor(getResources(), R.color.primary, getTheme()));
        pathOverlay.setOutlineWidth(getResources().getDimensionPixelSize(R.dimen.path_overlay_outline_width));
        pathOverlay.setOutlineColor(Color.WHITE);
        pathOverlay.setMap(naverMap);

        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(37.5701761, 126.9799315));
        marker1.setCaptionText(getString(R.string.marker_over_path));
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.5664663, 126.9772952));
        marker2.setIcon(MarkerIcons.BLUE);
        marker2.setCaptionText(getString(R.string.marker_under_path));
        marker2.setGlobalZIndex(PathOverlay.DEFAULT_GLOBAL_Z_INDEX - 1);
        marker2.setMap(naverMap);
    }
}
