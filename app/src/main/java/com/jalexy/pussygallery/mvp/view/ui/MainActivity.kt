package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.view.ui.adapters.SectionsPagerAdapter
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PussyApplication.appComponent.inject(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}