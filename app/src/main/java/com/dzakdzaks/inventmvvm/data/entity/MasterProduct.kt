package com.dzakdzaks.inventmvvm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 10:49 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.data
 * ==================================//==================================
 * ==================================//==================================
 */

@Entity
data class MasterProduct(

    @field:SerializedName("image")
    val msImage: String? = "",

    @PrimaryKey
    @field:SerializedName("kode_barang")
    val msCode: String,

    @field:SerializedName("nama_barang")
    val msName: String? = ""
)
