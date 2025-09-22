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
package com.naver.maps.map.demo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

open class ToolbarActivity : AppCompatActivity() {
    protected val root: ViewGroup by lazy { findViewById(R.id.root)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.setContentView(R.layout.activity_toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toolbarHeight = toolbar.layoutParams.height
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            toolbar.updateLayoutParams {
                height = toolbarHeight + insets.top
            }
            toolbar.updatePadding(top = insets.top)

            v.updatePadding(bottom = insets.bottom)

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    override fun setContentView(layoutResID: Int) {
        layoutInflater.inflate(layoutResID, root, true)
    }

    override fun setContentView(view: View) {
        root.addView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        root.addView(view, params)
    }
}
