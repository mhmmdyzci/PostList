package com.example.postlist.util

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.hideKeyboard() {
    context.getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(windowToken, 0)
    clearFocus()
}
