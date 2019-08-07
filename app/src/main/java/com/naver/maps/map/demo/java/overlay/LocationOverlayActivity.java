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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.LocationOverlay;

public class LocationOverlayActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Animator animator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_overlay);

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
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setSubIcon(LocationOverlay.DEFAULT_SUB_ICON_ARROW);
        locationOverlay.setCircleOutlineWidth(0);
        locationOverlay.setVisible(true);
        locationOverlay.setOnClickListener(overlay -> {
            animateCircle(locationOverlay);
            return true;
        });

        naverMap.setOnMapClickListener((point, coord) -> {
            locationOverlay.setPosition(coord);
            animateCircle(locationOverlay);
        });

        SeekBar bearingSeekBar = findViewById(R.id.seek_bar_bearing);
        TextView bearingValue = findViewById(R.id.value_bearing);
        bearingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                locationOverlay.setBearing(progress);
                bearingValue.setText(getString(R.string.format_bearing, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        animateCircle(locationOverlay);
    }

    private void animateCircle(@NonNull LocationOverlay locationOverlay) {
        if (animator != null) {
            animator.cancel();
        }

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator radiusAnimator = ObjectAnimator.ofInt(locationOverlay, "circleRadius",
            0, getResources().getDimensionPixelSize(R.dimen.location_overlay_circle_raduis));
        radiusAnimator.setRepeatCount(2);

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(locationOverlay, "circleColor",
            Color.argb(127, 148, 186, 250), Color.argb(0, 148, 186, 250));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setRepeatCount(2);

        animatorSet.setDuration(1000);
        animatorSet.playTogether(radiusAnimator, colorAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                locationOverlay.setCircleRadius(0);
            }
        });
        animatorSet.start();

        animator = animatorSet;
    }
}
