package com.moon.beautygirlkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.moon.beautygirlkotlin.glide.GlideCircleTransform
import com.moon.beautygirlkotlin.mengmeizi.GankFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mCircleImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        initData()
    }

    fun initViews(){
        nav_view.setNavigationItemSelectedListener(this)

        var headView: View = nav_view.inflateHeaderView(R.layout.nav_header_main);

        mCircleImageView = headView.findViewById<View>(R.id.nav_head_avatar) as ImageView

        toolbar.setTitle("萌妹纸")

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    fun initData(){
        Glide.with(this).load( R.drawable.ic_avatar1).transform(GlideCircleTransform(this)).into(mCircleImageView)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, GankFragment.getInstance(0))
                .commit()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> {


            }

        }


        return true
    }

}
