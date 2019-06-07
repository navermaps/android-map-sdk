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
package com.naver.maps.map.demo.java.camera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;

public class CameraEventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final LatLng COORD_1 = new LatLng(35.1798159, 129.0750222);
    private static final LatLng COORD_2 = new LatLng(37.5666102, 126.9783881);

    private boolean positionFlag;
    private boolean moving;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_event);

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
        Marker marker1 = new Marker();
        marker1.setPosition(COORD_1);
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(COORD_2);
        marker2.setMap(naverMap);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if (moving) {
                naverMap.cancelTransitions();
            } else {
                LatLng coord = positionFlag ? COORD_2 : COORD_1;

                naverMap.moveCamera(CameraUpdate.scrollTo(coord)
                    .animate(CameraAnimation.Fly, 5000)
                    .cancelCallback(() -> {
                        moving = false;
                        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        Toast.makeText(this, R.string.camera_update_cancelled, Toast.LENGTH_SHORT).show();
                    })
                    .finishCallback(() -> {
                        moving = false;
                        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        Toast.makeText(this, R.string.camera_update_finished, Toast.LENGTH_SHORT).show();
                    }));

                moving = true;
                fab.setImageResource(R.drawable.ic_stop_black_24dp);

                positionFlag = !positionFlag;
            }
        });

        TextView cameraChange = findViewById(R.id.camera_change);
        naverMap.addOnCameraChangeListener((reason, animated) -> {
            CameraPosition position = naverMap.getCameraPosition();
            cameraChange.setText(getString(R.string.format_camera_position,
                position.target.latitude, position.target.longitude, position.zoom, position.tilt, position.bearing));
        });

        TextView cameraIdle = findViewById(R.id.camera_idle);
        naverMap.addOnCameraIdleListener(() -> {
            CameraPosition position = naverMap.getCameraPosition();
            cameraIdle.setText(getString(R.string.format_camera_position,
                position.target.latitude, position.target.longitude, position.zoom, position.tilt, position.bearing));
        });
    }
}
