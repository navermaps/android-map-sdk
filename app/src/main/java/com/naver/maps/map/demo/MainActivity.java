/*
 * Copyright 2018 NAVER Corp.
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
package com.naver.maps.map.demo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.map.NaverMapSdk;

public class MainActivity extends AppCompatActivity {
    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setItem(@NonNull Object item);
    }

    public static class TitleViewHolder extends ViewHolder {
        private final TextView title;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        @Override
        public void setItem(@NonNull Object item) {
            title.setText((String)item);
        }
    }

    public static class DemoViewHolder extends ViewHolder {
        private final TextView title;
        private final TextView description;

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        @Override
        public void setItem(@NonNull Object item) {
            Demo demo = (Demo)item;
            title.setText(demo.title);
            description.setText(demo.description);
            itemView.setOnClickListener(v -> {
                Context context = itemView.getContext();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(context.getPackageName(), demo.className));
                context.startActivity(intent);
            });
        }
    }

    public static class DemoListAdapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        private final List<Object> items;

        public DemoListAdapter(@NonNull List<Object> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == 0) {
                return new TitleViewHolder(inflater.inflate(R.layout.item_title, parent, false));
            } else {
                return new DemoViewHolder(inflater.inflate(R.layout.item_demo, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setItem(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position) instanceof String ? 0 : 1;
        }
    }

    private static class Demo {
        @NonNull
        public final String className;
        @NonNull
        public final String category;
        @NonNull
        public final String title;
        @NonNull
        public final String description;

        public Demo(@NonNull String className, @NonNull String category, @NonNull String title,
            @NonNull String description) {
            this.className = className;
            this.category = category;
            this.title = title;
            this.description = description;
        }
    }

    @NonNull
    private static List<Demo> getDemos(@NonNull Context context) {
        String packageName = context.getPackageName();
        PackageInfo app;
        try {
            app = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return Collections.emptyList();
        }

        List<Demo> demos = new ArrayList<>();

        for (ActivityInfo info : app.activities) {
            if (!info.name.startsWith(packageName) || info.name.equals(MainActivity.class.getName())) {
                continue;
            }
            String subPackage = info.name.substring(packageName.length() + 1);
            String category = subPackage.substring(0, subPackage.indexOf('.'));
            String title = context.getString(info.labelRes);
            String description = context.getString(info.descriptionRes);
            demos.add(new Demo(info.name, category, title, description));
        }

        return demos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.recycler_view);

        List<Demo> demos = getDemos(this);

        List<Object> result = new ArrayList<>();
        String currentCategory = null;
        for (Demo demo : demos) {
            if (!demo.category.equals(currentCategory)) {
                currentCategory = demo.category;
                result.add(
                    getString(getResources().getIdentifier("category_" + demo.category, "string", getPackageName())));
            }
            result.add(demo);
        }

        list.setAdapter(new DemoListAdapter(result));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_flush_cache:
            NaverMapSdk.getInstance(this).flushCache(() ->
                Toast.makeText(this, R.string.cache_flushed, Toast.LENGTH_SHORT).show());
            return true;
        case R.id.action_open_source_notice:
            showOpenSourceNotice();
            return true;
        case R.id.action_info:
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @UiThread
    private void showOpenSourceNotice() {
        int padding = (int)(getResources().getDisplayMetrics().density * 12);

        TextView tv = new TextView(this);
        tv.setText(readTextAsset("navermap-sdk/NOTICE"));

        ScrollView sv = new ScrollView(this);
        sv.setPadding(padding, padding, padding, 0);
        sv.addView(tv);

        new AlertDialog.Builder(this)
            .setTitle(R.string.open_source_notice)
            .setView(sv)
            .setPositiveButton(R.string.ok, null)
            .show();
    }

    @UiThread
    private void showInfo() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.sdk_info_title)
            .setMessage(getString(R.string.sdk_info_body_format,
                com.naver.maps.map.BuildConfig.VERSION_NAME,
                readTextAsset("navermap-sdk/LICENSE")))
            .setPositiveButton(R.string.ok, null)
            .show();
    }

    @NonNull
    private String readTextAsset(@NonNull String assetName) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(getAssets().open(assetName));
            int l;
            char[] buffer = new char[4096];
            while ((l = reader.read(buffer, 0, buffer.length)) != -1) {
                sb.append(buffer, 0, l);
            }
        } catch (IOException e) {
            // ignore
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return sb.toString().trim();
    }
}
