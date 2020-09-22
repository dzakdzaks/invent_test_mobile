package com.dzakdzaks.inventmvvm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TxProduct(

	@field:SerializedName("cabang")
	val txBranch: String? = "",

	@field:SerializedName("kode_barang")
	val txCode: String? = "",

	@field:SerializedName("nama_barang")
	val txName: String? = "",

	@field:SerializedName("harga_barang")
	val txPrice: Double? = 0.0,

	@PrimaryKey
	@field:SerializedName("id")
	val txID: String
) {
	companion object {
		val TX_NAME = "txName"
		val TX_PRICE = "txPrice"
	}
}
