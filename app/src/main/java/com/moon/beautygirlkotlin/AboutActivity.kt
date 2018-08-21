package com.moon.beautygirlkotlin

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.moon.beautygirlkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * author: moon
 * created on: 18/4/28 下午3:10
 * description: 关于
 */
class AboutActivity : BaseActivity() {

    override fun initViews() {
        setSupportActionBar(about_toolbar)

        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.setTitle(getString(R.string.about_title))

        about_title_v.setText(getVersion())
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getVersion(): String {

        try {
            val pi = packageManager.getPackageInfo(packageName, 0)
            return pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return getString(R.string.about_version)
        }

    }
}