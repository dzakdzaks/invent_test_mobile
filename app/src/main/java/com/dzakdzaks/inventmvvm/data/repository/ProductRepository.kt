package com.dzakdzaks.inventmvvm.data.repository

import com.dzakdzaks.inventmvvm.data.local.ProductDao
import com.dzakdzaks.inventmvvm.data.remote.RemoteDataSource
import com.dzakdzaks.inventmvvm.ui.FilterProduct
import com.dzakdzaks.inventmvvm.util.performGetOperation
import javax.inject.Inject

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:09 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.data.repository
 * ==================================//==================================
 * ==================================//==================================
 */
class ProductRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: ProductDao
) {

    fun getMasterProduct() = performGetOperation(
        databaseQuery = { local.getMasterProducts() },
        networkCall = { remote.getMasterProducts() },
        saveCallResult = { local.insertMasterProduct(it.value!!) }
    )

    fun getTxProducts() = performGetOperation(
        databaseQuery = { local.getTxProducts() },
        networkCall = { remote.getTxProducts() },
        saveCallResult = { local.insertTxProduct(it.value!!) }
    )

    fun getAllProducts(filterProduct: FilterProduct) =
        local.getAllProducts(filterProduct.key, filterProduct.isAsc)

}