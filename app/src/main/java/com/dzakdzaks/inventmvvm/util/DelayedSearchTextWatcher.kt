package com.dzakdzaks.inventmvvm.util

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Tuesday, 22 September 2020 at 8:22 AM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.util
 * ==================================//==================================
 * ==================================//==================================
 */
class DelayedSearchTextWatcher(private val onTextWatcher: OnTextWatcher) : TextWatcher {
    var job: Job? = null

    override fun afterTextChanged(s: Editable?) {
        job = MainScope().launch {
            delay(500L)
            s?.let {
                onTextWatcher.onAfterTextChanged(it.toString())
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        job?.cancel()
    }

    interface OnTextWatcher {
        fun onAfterTextChanged(s: String)
    }

}