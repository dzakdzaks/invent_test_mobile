package com.dzakdzaks.inventmvvm.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dzakdzaks.inventmvvm.data.entity.MasterProduct
import com.dzakdzaks.inventmvvm.data.entity.Product
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

    val getMsProducts: LiveData<Resource<List<MasterProduct>>> =
        productRepository.getMasterProduct()

    val getTxProducts: LiveData<Resource<List<TxProduct>>> = productRepository.getTxProducts()

    private val _filterProduct = MutableLiveData<FilterProduct>()

    private val _prods = _filterProduct.switchMap { key ->
        productRepository.getAllProducts(key)
    }

    val prods: LiveData<List<Product>> = _prods

    fun setFilter(key: String = "", isAsc: Boolean = true) {
        val filterProduct = FilterProduct(key, isAsc)
        _filterProduct.value = filterProduct
    }

    val getFilter: LiveData<FilterProduct> = _filterProduct

}