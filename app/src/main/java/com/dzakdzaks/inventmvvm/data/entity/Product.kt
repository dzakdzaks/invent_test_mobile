package com.dzakdzaks.inventmvvm.data.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 10:56 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.data.entity
 * ==================================//==================================
 * ==================================//==================================
 */
data class Product(
    @Embedded val msProduct: MasterProduct,
    @Relation(
        parentColumn = "msCode",
        entityColumn = "txCode"
    )
    val txProducts: List<TxProduct>
)
