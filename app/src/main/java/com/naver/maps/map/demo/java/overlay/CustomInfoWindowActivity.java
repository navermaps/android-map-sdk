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

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

public class CustomInfoWindowActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static class InfoWindowAdapter extends InfoWindow.ViewAdapter {
        @NonNull
        private final Context context;
        private View rootView;
        private ImageView icon;
        private TextView text;

        private InfoWindowAdapter(@NonNull Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(@NonNull InfoWindow infoWindow) {
            if (rootView == null) {
                rootView = View.inflate(context, R.layout.view_custom_info_window, null);
                icon = rootView.findViewById(R.id.icon);
                text = rootView.findViewById(R.id.text);
            }

            if (infoWindow.getMarker() != null) {
                icon.setImageResource(R.drawable.ic_place_black_24dp);
                text.setText((String)infoWindow.getMarker().getTag());
            } else {
                icon.setImageResource(R.drawable.ic_my_location_black_24dp);
                text.setText(context.getString(
                    R.string.format_coord, infoWindow.getPosition().latitude, infoWindow.getPosition().longitude));
            }

            return rootView;
        }
    }

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
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAnchor(new PointF(0, 1));
        infoWindow.setOffsetX(getResources().getDimensionPixelSize(R.dimen.custom_info_window_offset_x));
        infoWindow.setOffsetY(getResources().getDimensionPixelSize(R.dimen.custom_info_window_offset_y));
        infoWindow.setAdapter(new InfoWindowAdapter(this));
        infoWindow.setOnClickListener(overlay -> {
            infoWindow.close();
            return true;
        });

        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(37.57000, 126.97618));
        marker1.setOnClickListener(overlay -> {
            infoWindow.open(marker1);
            return true;
        });
        marker1.setTag("Marker 1");
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.56138, 126.97970));
        marker2.setAngle(270f);
        marker2.setOnClickListener(overlay -> {
            infoWindow.open(marker2, Align.Left);
            return true;
        });
        marker2.setTag("Marker 2");
        marker2.setMap(naverMap);

        infoWindow.open(marker1);

        naverMap.setOnMapClickListener((point, coord) -> {
            infoWindow.setPosition(coord);
            infoWindow.open(naverMap);
        });
    }
}
