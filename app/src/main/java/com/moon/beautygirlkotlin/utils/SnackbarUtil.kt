package com.moon.beautygirlkotlin.utils

import android.support.design.widget.Snackbar
import android.view.View

class SnackbarUtil {

    companion object {
        fun showMessage(view: View, text: String) {

            Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
        }
    }


}