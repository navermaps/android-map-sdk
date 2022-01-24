/*
 * Copyright 2018-2022 NAVER Corp.
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

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.naver.maps.map.NaverMapSdk
import java.util.Locale

class MainActivity : AppCompatActivity() {
    class OpenSourceNoticeFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.sdk_info_title)
                .setMessage(getString(
                    R.string.sdk_info_body_format,
                    com.naver.maps.map.BuildConfig.VERSION_NAME,
                    requireContext().readTextAsset("navermap-sdk/LICENSE")
                ))
                .setView(ScrollView(requireContext()).apply {
                    val padding = (resources.displayMetrics.density * 12).toInt()
                    setPadding(padding, padding, padding, 0)
                    addView(TextView(requireContext()).apply {
                        text = requireContext().readTextAsset("navermap-sdk/NOTICE")
                    })
                })
                .setPositiveButton(R.string.ok, null)
                .create()
    }

    class InfoFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.sdk_info_title)
                .setMessage(getString(
                    R.string.sdk_info_body_format,
                    com.naver.maps.map.BuildConfig.VERSION_NAME,
                    requireContext().readTextAsset("navermap-sdk/LICENSE")
                ))
                .setPositiveButton(R.string.ok, null)
                .create()
    }

    class ListFragment : Fragment() {
        private data class Demo(val className: String, val category: String, val title: String, val description: String)

        private abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            abstract fun setItem(position: Int, item: Any)
        }

        private class TitleViewHolder(itemView: View) : ViewHolder(itemView) {
            private val divider: View = itemView.findViewById(R.id.divider)
            private val title: TextView = itemView.findViewById(R.id.title)

            override fun setItem(position: Int, item: Any) {
                divider.visibility = if (position == 0) View.GONE else View.VISIBLE
                title.text = item as String
            }
        }

        private class DemoViewHolder(itemView: View) : ViewHolder(itemView) {
            private val title: TextView = itemView.findViewById(R.id.title)
            private val description: TextView = itemView.findViewById(R.id.description)

            override fun setItem(position: Int, item: Any) {
                val demo = item as Demo
                title.text = demo.title
                description.text = demo.description
                itemView.setOnClickListener {
                    val context = itemView.context
                    context.startActivity(Intent().apply {
                        component = ComponentName(context.packageName, demo.className)
                    })
                }
            }
        }

        private class DemoListAdapter(private val items: List<Any>) : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                if (viewType == 0) {
                    TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false))
                } else {
                    DemoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_demo, parent, false))
                }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(position, items[position])

            override fun getItemCount() = items.size

            override fun getItemViewType(position: Int) = if (items[position] is String) 0 else 1
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val packageName = requireContext().packageName
            val result = mutableListOf<Any>()

            var currentCategory = ""

            getDemos(requireContext(), arguments?.getString(ARGUMENT_LANGUAGE) ?: LANGUAGES[0]).forEach {
                if (it.category != currentCategory) {
                    currentCategory = it.category
                    result.add(getString(resources.getIdentifier("category_" + it.category, "string", packageName)))
                }
                result.add(it)
            }

            view.findViewById<RecyclerView>(R.id.recycler_view).adapter = DemoListAdapter(result)
        }

        companion object {
            const val ARGUMENT_LANGUAGE = "language"

            fun newInstance(language: String) = ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_LANGUAGE, language)
                }
            }

            private fun getDemos(context: Context, language: String) =
                try {
                    val packageName = "${context.packageName}.${language.lowercase()}"
                    context.packageManager
                        .getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
                        .activities
                        .filterNot {
                            !it.name.startsWith(packageName) || it.name == MainActivity::class.java.name
                        }.map {
                            val subPackage = it.name.substring(packageName.length + 1)
                            Demo(
                                it.name,
                                subPackage.substring(0, subPackage.indexOf('.')),
                                context.getString(it.labelRes),
                                context.getString(it.descriptionRes)
                            )
                        }
                } catch (e: PackageManager.NameNotFoundException) {
                    emptyList()
                }
        }
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount() = LANGUAGES.size

        override fun getPageTitle(position: Int) = LANGUAGES[position]

        override fun getItem(position: Int) = ListFragment.newInstance(LANGUAGES[position])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.view_pager).apply {
            adapter = PagerAdapter(supportFragmentManager)
        }

        findViewById<TabLayout>(R.id.tab_layout).setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_flush_cache -> {
                NaverMapSdk.getInstance(this).flushCache {
                    Toast.makeText(this, R.string.cache_flushed, Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_open_source_notice -> {
                OpenSourceNoticeFragment().show(supportFragmentManager, null)
                true
            }
            R.id.action_info -> {
                InfoFragment().show(supportFragmentManager, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        private val LANGUAGES = arrayOf("Java", "Kotlin")

        private fun Context.readTextAsset(assetName: String) =
            assets.open(assetName).use { it.bufferedReader().readText() }.trim()
    }
}
