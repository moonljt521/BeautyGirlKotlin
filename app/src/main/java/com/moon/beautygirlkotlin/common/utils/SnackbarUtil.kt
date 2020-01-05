package com.moon.beautygirlkotlin.common.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackbarUtil {

    companion object {
        fun showMessage(view: View, text: String) {

            Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
        }
    }


}