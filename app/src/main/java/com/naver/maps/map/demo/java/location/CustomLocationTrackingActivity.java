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
package com.naver.maps.map.demo.java.location;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.LocationOverlay;

public class CustomLocationTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_INTERVAL = 1000;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private boolean trackingEnabled;
    private boolean locationEnabled;
    private boolean waiting;
    private FloatingActionButton fab;
    private NaverMap map;

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (map == null) {
                return;
            }
            Location lastLocation = locationResult.getLastLocation();
            if (lastLocation == null) {
                return;
            }
            LatLng coord = new LatLng(lastLocation);
            LocationOverlay locationOverlay = map.getLocationOverlay();
            locationOverlay.setPosition(coord);
            locationOverlay.setBearing(lastLocation.getBearing());
            map.moveCamera(CameraUpdate.scrollTo(coord));
            if (waiting) {
                waiting = false;
                fab.setImageResource(R.drawable.ic_location_disabled_black_24dp);
                locationOverlay.setVisible(true);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fab);

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

        fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_my_location_black_24dp);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PermissionChecker.PERMISSION_GRANTED) {
                    fab.setImageResource(R.drawable.ic_my_location_black_24dp);
                    return;
                }
            }

            tryEnableLocation();
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (trackingEnabled) {
            tryEnableLocation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        disableLocation();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        map = naverMap;

        fab.setOnClickListener(v -> {
            if (trackingEnabled) {
                disableLocation();
                fab.setImageResource(R.drawable.ic_my_location_black_24dp);
            } else {
                CircularProgressDrawable progressDrawable = new CircularProgressDrawable(this);
                progressDrawable.setStyle(CircularProgressDrawable.LARGE);
                progressDrawable.setColorSchemeColors(Color.WHITE);
                progressDrawable.start();
                fab.setImageDrawable(progressDrawable);
                tryEnableLocation();
            }
            trackingEnabled = !trackingEnabled;
        });
    }

    private void tryEnableLocation() {
        if (ContextCompat.checkSelfPermission(this, PERMISSIONS[0]) == PermissionChecker.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, PERMISSIONS[1]) == PermissionChecker.PERMISSION_GRANTED) {
            enableLocation();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    private void enableLocation() {
        locationEnabled = true;
        waiting = true;
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(
                new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL).build(),
                locationCallback, Looper.getMainLooper());
    }

    private void disableLocation() {
        if (!locationEnabled) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
        locationEnabled = false;
    }
}
