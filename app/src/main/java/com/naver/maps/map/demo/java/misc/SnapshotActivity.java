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
package com.naver.maps.map.demo.java.misc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.demo.ToolbarActivity;

public class SnapshotActivity extends ToolbarActivity implements OnMapReadyCallback {
    private NaverMap map;
    private CheckedTextView showControls;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_snapshot);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        map = naverMap;

        showControls = findViewById(R.id.toggle_show_controls);
        fab = findViewById(R.id.fab);

        showControls.setOnClickListener(v -> showControls.setChecked(!showControls.isChecked()));

        fab.setOnClickListener(v -> {
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(this);
            progressDrawable.setStyle(CircularProgressDrawable.LARGE);
            progressDrawable.setColorSchemeColors(Color.WHITE);
            progressDrawable.start();
            fab.setImageDrawable(progressDrawable);

            if (naverMap.isFullyRendered() && naverMap.isRenderingStable()) {
                takeSnapshot();
            } else {
                naverMap.addOnMapRenderedListener(new NaverMap.OnMapRenderedListener() {
                    @Override
                    public void onMapRendered(boolean fully, boolean stable) {
                        if (fully && stable) {
                            takeSnapshot();
                            naverMap.removeOnMapRenderedListener(this);
                        }
                    }
                });
            }
        });
    }

    private void takeSnapshot() {
        ImageView snapshot = findViewById(R.id.snapshot);
        map.takeSnapshot(showControls.isChecked(), bitmap -> {
            fab.setImageResource(R.drawable.ic_photo_camera_black_24dp);
            snapshot.setImageBitmap(bitmap);
        });
    }
}
