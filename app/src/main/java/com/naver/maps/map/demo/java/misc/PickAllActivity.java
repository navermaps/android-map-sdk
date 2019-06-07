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

import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.Pickable;
import com.naver.maps.map.Symbol;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;

public class PickAllActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        private ViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView;
        }

        private void setItem(@NonNull Pickable item) {
            Context context = itemView.getContext();

            if (item instanceof Symbol) {
                text.setText(context.getString(R.string.format_pickable_symbol, ((Symbol)item).getCaption()));
            } else if (item instanceof Marker) {
                text.setText(
                    context.getString(R.string.format_pickable_marker, ((Marker)item).getCaptionText()));
            } else {
                text.setText(context.getString(R.string.pickable_overlay));
            }
        }
    }

    private static class Adapter extends ListAdapter<Pickable, ViewHolder> {
        private Adapter() {
            super(new DiffUtil.ItemCallback<Pickable>() {
                @Override
                public boolean areItemsTheSame(@NonNull Pickable oldItem, @NonNull Pickable newItem) {
                    return oldItem.equals(newItem);
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Pickable oldItem, @NonNull Pickable newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pickable, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setItem(getItem(position));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick_all);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .enabledLayerGroups(NaverMap.LAYER_GROUP_TRANSIT));
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
        marker1.setPosition(new LatLng(37.56752, 126.97385));
        marker1.setCaptionText("Marker 1");
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.56211, 126.98266));
        marker2.setCaptionText("Marker 2");
        marker2.setMap(naverMap);

        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(37.563028, 126.97629));
        marker3.setCaptionText("Marker 3");
        marker3.setMap(naverMap);

        Marker marker4 = new Marker();
        marker4.setPosition(new LatLng(37.56992, 126.98005));
        marker4.setCaptionText("Marker 4");
        marker4.setMap(naverMap);

        int radius = getResources().getDimensionPixelSize(R.dimen.pick_radius);

        Adapter adapter = new Adapter();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        View circle = findViewById(R.id.circle);

        naverMap.setOnMapClickListener((point, coord) -> {
            circle.setX(point.x - radius);
            circle.setY(point.y - radius);
            circle.setVisibility(View.VISIBLE);
            adapter.submitList(naverMap.pickAll(point, radius));
        });

        naverMap.addOnCameraChangeListener((reason, animated) -> {
            circle.setVisibility(View.GONE);
            adapter.submitList(Collections.emptyList());
        });
    }
}
