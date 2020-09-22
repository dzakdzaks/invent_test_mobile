package com.dzakdzaks.inventmvvm.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dzakdzaks.inventmvvm.data.entity.MasterProduct
import com.dzakdzaks.inventmvvm.data.entity.Product
import com.dzakdzaks.inventmvvm.data.entity.TxProduct

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Saturday, 05 September 2020 at 1:11 PM.
 * Project Name => AndroidArchitecture-Hilt-MVVM-Coroutines-LiveData-Room-Retrofit
 * Package Name => com.dzakdzaks.androidarchitecture.data.local
 * ==================================//==================================
 * ==================================//==================================
 */

@Dao
interface ProductDao {

    /* @Transaction
      @Query(
          "SELECT * FROM MasterProduct WHERE msName LIKE '%' || :key || '%' ORDER BY CASE WHEN :isAsc = 1 THEN msName END ASC, CASE WHEN :isAsc = 0 THEN msName END DESC"
      )
      fun getAllProducts(key: String, isAsc: Boolean = true): LiveData<List<Product>>
   */

    @Transaction
    @Query(
        "SELECT * FROM MasterProduct WHERE msName LIKE '%' || :key || '%' ORDER BY CASE WHEN :isAsc = 1 THEN msName END ASC, CASE WHEN :isAsc = 0 THEN msName END DESC"
    )
    fun getAllProducts(key: String, isAsc: Boolean = true): LiveData<List<Product>>

    @Query("SELECT * FROM MasterProduct")
    fun getMasterProducts(): LiveData<List<MasterProduct>>

    @Query("SELECT * FROM TxProduct")
    fun getTxProducts(): LiveData<List<TxProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMasterProduct(data: List<MasterProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTxProduct(data: List<TxProduct>)
}