package com.dzakdzaks.inventmvvm.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzakdzaks.inventmvvm.data.entity.MasterProduct
import com.dzakdzaks.inventmvvm.data.entity.TxProduct
import com.dzakdzaks.inventmvvm.data.repository.ProductRepository
import com.dzakdzaks.inventmvvm.util.Resource


/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:18 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.ui
 * ==================================//==================================
 * ==================================//==================================
 */

class MainViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var searchKey = MutableLiveData<String>()


    init {
        getMsProducts()
    }

    fun getMsProducts(): LiveData<Resource<List<MasterProduct>>> =
        productRepository.getMasterProduct()

    fun getTxProducts(): LiveData<Resource<List<TxProduct>>> = productRepository.getTxProducts()

    fun setSearchKey(newKey: String) {
        searchKey.value = newKey
    }

    fun getAllProducts(
        searchKey: String = "",
        isAsc: Boolean = true,
        orderBy: String = TxProduct.TX_NAME
    ) =
        productRepository.getAllProducts(searchKey, isAsc, orderBy)
}