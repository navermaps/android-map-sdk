/*
 * Copyright 2018-2023 NAVER Corp.
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
package com.naver.maps.map.demo.java.option;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;

public class ContentPaddingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final LatLng COORD_1 = new LatLng(37.5666102, 126.9783881);
    private static final LatLng COORD_2 = new LatLng(35.1798159, 129.0750222);

    private boolean positionFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content_padding);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .camera(new CameraPosition(COORD_1, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
                .contentPadding(
                    getResources().getDimensionPixelSize(R.dimen.map_padding_left),
                    getResources().getDimensionPixelSize(R.dimen.map_padding_top),
                    getResources().getDimensionPixelSize(R.dimen.map_padding_right),
                    getResources().getDimensionPixelSize(R.dimen.map_padding_bottom)));
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
        Marker marker1 = new Marker();
        marker1.setPosition(COORD_1);
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(COORD_2);
        marker2.setMap(naverMap);

        findViewById(R.id.fab).setOnClickListener(v -> {
            LatLng coord = positionFlag ? COORD_1 : COORD_2;
            naverMap.moveCamera(CameraUpdate.scrollTo(coord).animate(CameraAnimation.Fly, 3000));
            positionFlag = !positionFlag;
        });

        TextView contentBounds = findViewById(R.id.content_bounds);
        TextView coveringBounds = findViewById(R.id.covering_bounds);
        naverMap.addOnCameraChangeListener((reason, animated) -> {
            LatLngBounds content = naverMap.getContentBounds();
            contentBounds.setText(getString(R.string.format_bounds, content.getSouthLatitude(),
                content.getWestLongitude(), content.getNorthLatitude(), content.getEastLongitude()));

            LatLngBounds covering = naverMap.getCoveringBounds();
            coveringBounds.setText(getString(R.string.format_bounds, covering.getSouthLatitude(),
                covering.getWestLongitude(), covering.getNorthLatitude(), covering.getEastLongitude()));
        });
    }
}
