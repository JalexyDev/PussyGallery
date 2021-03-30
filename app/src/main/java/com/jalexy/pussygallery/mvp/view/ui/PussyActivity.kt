package com.jalexy.pussygallery.mvp.view.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jalexy.pussygallery.GlideApp
import com.jalexy.pussygallery.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pussy_details.*
import java.text.SimpleDateFormat
import java.util.*


class PussyActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_URL = "IMAGE_URL"

        private const val PERMISSION_REQUEST_CODE = 123
    }

    private var imageUrl: String? = ""
    private var disposable: Disposable? = null
    private var loadingImage = false
    private var allowLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pussy_details)

        title = "Киска"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        imageUrl = if (savedInstanceState == null) {
            intent.getStringExtra(IMAGE_URL)
        } else {
            savedInstanceState.getString(IMAGE_URL)
        }

        if (hasPermissions()) {
            allowLoading = true
        } else {
            requestPerms()
        }
    }

    override fun onStart() {
        super.onStart()
        GlideApp.with(applicationContext)
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(pussy_image)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(IMAGE_URL, imageUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pussy_activity_menu, menu)
        menu.findItem(R.id.save_image).isVisible = allowLoading
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.save_image -> {
                saveImageOnPhone()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        val lastDisposable = disposable

        if (lastDisposable != null && lastDisposable.isDisposed.not()) {
            disposable?.dispose()
        }

        super.onDestroy()
    }

    private fun saveImageOnPhone() {
        if (!loadingImage) {

            Toast.makeText(applicationContext, "Загружаем киску", Toast.LENGTH_SHORT).show()

            val observableOnSubscribe = ObservableOnSubscribe<Void> {
                loadingImage = true
                loadWithDownloadManager()
            }

            disposable = Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun loadWithDownloadManager() {
        val dateString = SimpleDateFormat.getDateTimeInstance().format(Date())
        val imageFileName = "pussy_$dateString.jpg"

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(imageUrl)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, imageFileName)
        downloadManager.enqueue(request)
    }

    private fun hasPermissions(): Boolean {
        var res = 0
        //string array of permissions,
        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        for (perms in permissions) {
            res = checkCallingOrSelfPermission(perms)
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun requestPerms() {
        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> for (res in grantResults) {
                // if user granted all permissions.
                allowLoading = res == PackageManager.PERMISSION_GRANTED
            }
            else ->                 // if user not granted permissions.
                allowLoading = false
        }

        if (allowLoading) {
            invalidateOptionsMenu()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}