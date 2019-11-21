package com.moon.beautygirlkotlin

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.databinding.FragmentAboutBinding

/**
 * author: jiangtao.liang
 * date:   On 2019-11-21 18:45
 */

class AboutFragment : BaseFragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun initData() {
    }

    override fun initViews(view: View?) {
        binding.aboutTitleV.setText(getVersion())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getVersion(): String {

        try {
            val pi = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            return pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return getString(R.string.about_version)
        }
    }
}