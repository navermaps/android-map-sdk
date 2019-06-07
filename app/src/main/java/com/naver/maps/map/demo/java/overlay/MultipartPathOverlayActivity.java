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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.MultipartPathOverlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.GeometryUtils;

public class MultipartPathOverlayActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<List<LatLng>> COORDS_1 = Arrays.asList(
        Arrays.asList(
            new LatLng(37.5594084, 126.9745830),
            new LatLng(37.5599980, 126.9748245),
            new LatLng(37.5601083, 126.9748951),
            new LatLng(37.5601980, 126.9749873),
            new LatLng(37.5601998, 126.9749896),
            new LatLng(37.5602478, 126.9750492),
            new LatLng(37.5603158, 126.9751371),
            new LatLng(37.5604241, 126.9753616),
            new LatLng(37.5604853, 126.9755401),
            new LatLng(37.5605225, 126.9756157),
            new LatLng(37.5605353, 126.9756405),
            new LatLng(37.5605652, 126.9756924),
            new LatLng(37.5606143, 126.9757679),
            new LatLng(37.5606903, 126.9758432),
            new LatLng(37.5608510, 126.9758919),
            new LatLng(37.5611353, 126.9759964),
            new LatLng(37.5611949, 126.9760186)),
        Arrays.asList(
            new LatLng(37.5611949, 126.9760186),
            new LatLng(37.5612383, 126.9760364),
            new LatLng(37.5615796, 126.9761721),
            new LatLng(37.5619326, 126.9763123),
            new LatLng(37.5621502, 126.9763991),
            new LatLng(37.5622776, 126.9764492),
            new LatLng(37.5624374, 126.9765137),
            new LatLng(37.5630911, 126.9767753),
            new LatLng(37.5631345, 126.9767931),
            new LatLng(37.5635163, 126.9769240),
            new LatLng(37.5635506, 126.9769351),
            new LatLng(37.5638061, 126.9770239),
            new LatLng(37.5639153, 126.9770605),
            new LatLng(37.5639577, 126.9770749),
            new LatLng(37.5640074, 126.9770927),
            new LatLng(37.5644783, 126.9771755),
            new LatLng(37.5649229, 126.9772482)),
        Arrays.asList(
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
            new LatLng(37.5672785, 126.9773122)),
        Arrays.asList(
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
            new LatLng(37.5701869, 126.9784990)),
        Arrays.asList(
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
            new LatLng(37.5701996, 126.9821860)));

    private static final List<MultipartPathOverlay.ColorPart> COLORS_1 = Arrays.asList(
        new MultipartPathOverlay.ColorPart(Color.RED, Color.WHITE, Color.GRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.YELLOW, Color.WHITE, Color.GRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.GREEN, Color.WHITE, Color.GRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.RED, Color.WHITE, Color.GRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.YELLOW, Color.WHITE, Color.GRAY, Color.WHITE));

    private static final List<List<LatLng>> COORDS_2 = Arrays.asList(
        Arrays.asList(
            new LatLng(37.5660645, 126.9826732),
            new LatLng(37.5660294, 126.9826723),
            new LatLng(37.5658526, 126.9826611),
            new LatLng(37.5658040, 126.9826580),
            new LatLng(37.5657697, 126.9826560),
            new LatLng(37.5654413, 126.9825880),
            new LatLng(37.5652157, 126.9825273),
            new LatLng(37.5650560, 126.9824843),
            new LatLng(37.5647789, 126.9824114),
            new LatLng(37.5646788, 126.9823861),
            new LatLng(37.5644062, 126.9822963),
            new LatLng(37.5642519, 126.9822566),
            new LatLng(37.5641517, 126.9822312),
            new LatLng(37.5639965, 126.9821915),
            new LatLng(37.5636536, 126.9820920),
            new LatLng(37.5634424, 126.9820244),
            new LatLng(37.5633241, 126.9819890),
            new LatLng(37.5632772, 126.9819712),
            new LatLng(37.5629404, 126.9818433),
            new LatLng(37.5627733, 126.9817584),
            new LatLng(37.5626694, 126.9816980)),
        Arrays.asList(
            new LatLng(37.5626694, 126.9816980),
            new LatLng(37.5624588, 126.9815738),
            new LatLng(37.5620376, 126.9813140),
            new LatLng(37.5619426, 126.9812252),
            new LatLng(37.5613227, 126.9814831),
            new LatLng(37.5611995, 126.9815372),
            new LatLng(37.5609414, 126.9816749),
            new LatLng(37.5606785, 126.9817390),
            new LatLng(37.5605659, 126.9817499),
            new LatLng(37.5604892, 126.9817459),
            new LatLng(37.5604540, 126.9817360),
            new LatLng(37.5603484, 126.9816993),
            new LatLng(37.5602092, 126.9816097),
            new LatLng(37.5600048, 126.9814390)),
        Arrays.asList(
            new LatLng(37.5600048, 126.9814390),
            new LatLng(37.5599702, 126.9813612),
            new LatLng(37.5599401, 126.9812923),
            new LatLng(37.5597114, 126.9807346),
            new LatLng(37.5596905, 126.9806826),
            new LatLng(37.5596467, 126.9805663),
            new LatLng(37.5595203, 126.9801199),
            new LatLng(37.5594901, 126.9800149),
            new LatLng(37.5594544, 126.9798883),
            new LatLng(37.5594186, 126.9797436),
            new LatLng(37.5593948, 126.9796634),
            new LatLng(37.5593132, 126.9793526),
            new LatLng(37.5592831, 126.9792622),
            new LatLng(37.5590904, 126.9788854),
            new LatLng(37.5589081, 126.9786365),
            new LatLng(37.5587088, 126.9784125),
            new LatLng(37.5586699, 126.9783698)));

    private static final List<MultipartPathOverlay.ColorPart> COLORS_2 = Arrays.asList(
        new MultipartPathOverlay.ColorPart(Color.LTGRAY, Color.WHITE, Color.LTGRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.GRAY, Color.WHITE, Color.GRAY, Color.WHITE),
        new MultipartPathOverlay.ColorPart(Color.DKGRAY, Color.WHITE, Color.DKGRAY, Color.WHITE));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_overlay);

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
        int width = getResources().getDimensionPixelSize(R.dimen.path_overlay_width);
        int outlineWidth = getResources().getDimensionPixelSize(R.dimen.path_overlay_outline_width);

        MultipartPathOverlay multipartPathOverlay = new MultipartPathOverlay();
        multipartPathOverlay.setCoordParts(COORDS_1);
        multipartPathOverlay.setColorParts(COLORS_1);
        multipartPathOverlay.setWidth(width);
        multipartPathOverlay.setOutlineWidth(outlineWidth);
        multipartPathOverlay.setProgress(0.3);
        multipartPathOverlay.setMap(naverMap);

        MultipartPathOverlay multipartPathOverlayWithPattern = new MultipartPathOverlay();
        multipartPathOverlayWithPattern.setCoordParts(COORDS_2);
        multipartPathOverlayWithPattern.setColorParts(COLORS_2);
        multipartPathOverlayWithPattern.setWidth(width);
        multipartPathOverlayWithPattern.setOutlineWidth(0);
        multipartPathOverlayWithPattern.setPatternImage(OverlayImage.fromResource(R.drawable.path_pattern));
        multipartPathOverlayWithPattern.setPatternInterval(
            getResources().getDimensionPixelSize(R.dimen.overlay_pattern_interval));
        multipartPathOverlayWithPattern.setMap(naverMap);

        SeekBar progressSeekBar = findViewById(R.id.seek_bar_progress);
        TextView progressValue = findViewById(R.id.value_progress);
        progressValue.setOnClickListener(v -> progressSeekBar.setProgress(100));
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = progress / 100.0;
                progressValue.setText(getString(R.string.format_progress, progress));
                if (fromUser) {
                    multipartPathOverlay.setProgress(value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        naverMap.setOnMapClickListener((point, coord) -> {
            double progress = GeometryUtils.getProgressForCoordParts(multipartPathOverlay.getCoordParts(), coord);
            multipartPathOverlay.setProgress(progress);
            progressSeekBar.setProgress((int)(progress * 100));
        });
    }
}
