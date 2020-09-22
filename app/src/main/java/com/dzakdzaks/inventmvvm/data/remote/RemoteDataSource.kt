package com.dzakdzaks.inventmvvm.data.remote

import javax.inject.Inject

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:08 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.data.remote
 * ==================================//==================================
 * ==================================//==================================
 */
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getMasterProducts() = getResult { apiService.getMasterProducts() }

    suspend fun getTxProducts() = getResult { apiService.getTxProducts() }

}