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
package com.naver.maps.map.demo.java.misc;

import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.TileId;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.util.TileCoverHelper;

public class TileCoverHelperActivity extends AppCompatActivity implements OnMapReadyCallback {
    private LongSparseArray<Overlay> overlays;

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

        overlays = new LongSparseArray<>();
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
        TileCoverHelper tileCoverHelper = new TileCoverHelper();
        tileCoverHelper.setListener((addedTileIds, removedTileIds) -> {
            for (long tileId : addedTileIds) {
                LatLngBounds bounds = TileId.toLatLngBounds(tileId);

                PolygonOverlay overlay = new PolygonOverlay();
                overlay.setCoords(Arrays.asList(
                    bounds.getSouthWest(),
                    bounds.getNorthWest(),
                    bounds.getNorthEast(),
                    bounds.getSouthEast(),
                    bounds.getSouthWest()));
                overlay.setColor(Color.argb(
                    63,
                    (int)(Math.random() * 255 + 0.5),
                    (int)(Math.random() * 255 + 0.5),
                    (int)(Math.random() * 255 + 0.5)
                ));
                overlay.setMap(naverMap);

                overlays.put(tileId, overlay);
            }

            for (long tileId : removedTileIds) {
                Overlay overlay = overlays.get(tileId);
                if (overlay != null) {
                    overlay.setMap(null);
                    overlays.remove(tileId);
                }
            }
        });
        tileCoverHelper.setMap(naverMap);
    }
}
