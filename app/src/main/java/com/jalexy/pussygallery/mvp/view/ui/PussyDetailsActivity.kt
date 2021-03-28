package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jalexy.pussygallery.R

class PussyDetailsActivity : AppCompatActivity() {

    companion object {
        const val PUSSY_ID = "PUSSY_ID"
    }

    //todo создать презентер
    // загружать Image по id и отображать основную инфу


    private var pussyId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pussy_details)

        title = "Информация о киске"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        pussyId = if (savedInstanceState == null) {
            intent.getStringExtra(PUSSY_ID)
        } else {
            savedInstanceState.getString(PUSSY_ID)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(PUSSY_ID, pussyId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}