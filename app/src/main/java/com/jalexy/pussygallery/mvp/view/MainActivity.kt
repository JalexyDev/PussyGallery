package com.jalexy.pussygallery.mvp.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.Image
import com.jalexy.pussygallery.mvp.presenter.PussyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PussyApplication).apiComponent.inject(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        getTestResult()
    }

    fun getTestResult() {
        val api = retrofit.create(PussyApi::class.java)
        val call = api.getImages()

        call.enqueue(object: Callback<ArrayList<Image>> {
            override fun onResponse(
                call: Call<ArrayList<Image>>?,
                response: Response<ArrayList<Image>>?
            ) {
                val pussies = response?.body()

                pussies?.let {
                    for (i in it.indices) {
                        Log.d("Id shmaidy #$i ->", it[i].id ?: "PUK")
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Image>>?, t: Throwable?) {
                Toast.makeText(applicationContext, t?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}