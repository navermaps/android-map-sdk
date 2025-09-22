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
package com.naver.maps.map.demo.java.location;

import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.demo.ToolbarActivity;

public class CustomLocationSourceActivity extends ToolbarActivity implements OnMapReadyCallback {
    private static class CustomLocationSource implements LocationSource, NaverMap.OnMapClickListener {
        private OnLocationChangedListener listener;

        @Override
        public void activate(@NonNull OnLocationChangedListener listener) {
            this.listener = listener;
        }

        @Override
        public void deactivate() {
            listener = null;
        }

        @Override
        public void onMapClick(@NonNull PointF point, @NonNull LatLng coord) {
            if (listener == null) {
                return;
            }

            Location location = new Location("CustomLocationSource");
            location.setLatitude(coord.latitude);
            location.setLongitude(coord.longitude);
            location.setAccuracy(100);
            listener.onLocationChanged(location);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_fragment);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        CustomLocationSource locationSource = new CustomLocationSource();

        naverMap.setLocationSource(locationSource);
        naverMap.setOnMapClickListener(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
        naverMap.addOnLocationChangeListener(location ->
            Toast.makeText(this,
                getString(R.string.format_location_changed, location.getLatitude(), location.getLongitude()),
                Toast.LENGTH_SHORT).show());
    }
}
