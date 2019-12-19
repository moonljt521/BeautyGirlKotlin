package com.moon.beautygirlkotlin

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.moon.beautygirlkotlin.about.AboutActivity
import com.moon.beautygirlkotlin.base.BaseActivity
import com.moon.beautygirlkotlin.doubanmeizi.DouBanBaseFragment
import com.moon.beautygirlkotlin.gank.GankFragment
import com.moon.beautygirlkotlin.my_favorite.MyFavoriteFragment
import com.moon.beautygirlkotlin.utils.AppManager
import com.moon.beautygirlkotlin.utils.ImageLoader
import com.moon.beautygirlkotlin.utils.ShareUtil
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.wei1.OnlyPicBaseFragment
import com.moon.beautygirlkotlin.youtumeiku.YouTuMeikuFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mCircleImageView: ImageView

    var fragmentList: ArrayList<Fragment> = java.util.ArrayList()

    var currentTabIndex : Int = 0

    var exitTime:Long = 0

    override fun initViews() {
        nav_view.setNavigationItemSelectedListener(this)

        val headView: View = nav_view.inflateHeaderView(R.layout.nav_header_main);

        mCircleImageView = headView.findViewById<View>(R.id.nav_head_avatar) as ImageView

        toolbar.setTitle(getString(R.string.gank_meizi))

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun loadData() {
        fragmentList.add(GankFragment.getInstance(0)) // 萌妹子 gank
        fragmentList.add(DouBanBaseFragment.getInstance(0))  // 豆瓣妹子
        fragmentList.add(OnlyPicBaseFragment.getInstance(0))  // 【'唯一'图库】
        fragmentList.add(YouTuMeikuFragment.getInstance(0))  // 【优图美库】
        fragmentList.add(MyFavoriteFragment.getInstance(0))  // 我的收藏

        ImageLoader.loadCircle(this,R.drawable.ic_avatar1,mCircleImageView)

        // 初始化显示 [gank]妹子模块
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragmentList.get(0))
                .commit()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> {
                navigationFragment(0, getString(R.string.gank_meizi),item)
            }

            R.id.nav_douban -> {
                navigationFragment(1,getString(R.string.douban_meizi),item)
            }

            R.id.weiyi_tuku-> {
                navigationFragment(2,getString(R.string.weiyi_pic),item)
            }

            R.id.nav_tao-> {
                navigationFragment(3,getString(R.string.tao_female),item)
            }

            R.id.nav_mycollect-> {
                navigationFragment(4,getString(R.string.my_collect),item)
            }

            R.id.nav_share -> {
                ShareUtil.shareAppLink(this,getString(R.string.project_link),getString(R.string.app_name))
            }

            R.id.nav_score -> {
                navigationWebView()
            }

            R.id.nav_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }

        return true
    }

    /**
     * 切换主页各个fragment
     */
    fun navigationFragment(index : Int, title : String ,item: MenuItem ){

        val trx = supportFragmentManager.beginTransaction()
        trx.hide(fragmentList.get(currentTabIndex))

        if (!fragmentList.get(index).isAdded){
            trx.add(R.id.content,fragmentList.get(index))
        }

        trx.show(fragmentList.get(index)).commit()

        currentTabIndex = index

        item.isChecked = (true)

        toolbar.setTitle(title)
        drawer_layout.closeDrawers()
    }


    /**
     * 去跳转系统浏览器
     */
    fun navigationWebView(){

        val i = Intent()
        i.setAction("android.intent.action.VIEW")
        i.setData(Uri.parse(getString(R.string.project_link)))
        startActivity(i)

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {

        if (System.currentTimeMillis() - exitTime > 2000){
            SnackbarUtil.showMessage(drawer_layout, getString(R.string.back_message))
            exitTime = System.currentTimeMillis()
        }else{
            AppManager.instance.exitApp(applicationContext)
        }
    }

}
