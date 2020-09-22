package com.dzakdzaks.inventmvvm.util

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Tuesday, 22 September 2020 at 8:35 AM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.util
 * ==================================//==================================
 * ==================================//==================================
 */

fun Context.toast(message: String) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

fun RecyclerView.autoFitColumns(columnWidth: Int) {
    val displayMetrics = this.context.resources.displayMetrics
    val noOfColumns = ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
    this.layoutManager = GridLayoutManager(this.context, noOfColumns)
}

fun String.formatCurrency(): String? {
    val value: String?
    val formatter = DecimalFormat("#,###.##")
    val symbols = DecimalFormatSymbols(Locale("id", "ID"))
    formatter.decimalFormatSymbols = symbols
    value = formatter.format(java.lang.Double.valueOf(this))
    return "Rp $value,-"
}