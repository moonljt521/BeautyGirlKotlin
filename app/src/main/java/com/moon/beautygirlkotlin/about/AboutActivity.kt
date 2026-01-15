package com.moon.beautygirlkotlin.about

import android.view.MenuItem
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseActivity
import com.moon.beautygirlkotlin.databinding.ActivityAboutBinding

/**
 * author: moon
 * created on: 18/4/28 下午3:10
 * description: 关于
 */
class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun initViews() {
        setSupportActionBar(binding.aboutToolbar)

        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.setTitle(getString(R.string.about_title))

        supportFragmentManager.beginTransaction().add(R.id.fragmentAbout, AboutFragment()).commit()
    }

    override fun loadData() {
    }

    override fun getLayoutId(): Int {
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}