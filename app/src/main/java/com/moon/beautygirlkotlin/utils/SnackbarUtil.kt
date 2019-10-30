package com.moon.beautygirlkotlin.utils

import com.google.android.material.snackbar.Snackbar
import android.view.View

class SnackbarUtil {

    companion object {
        fun showMessage(view: View, text: String) {

            Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
        }
    }


}