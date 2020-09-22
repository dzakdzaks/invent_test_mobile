package com.dzakdzaks.inventmvvm.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 10:44 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.util
 * ==================================//==================================
 * ==================================//==================================
 */
fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS)
            saveCallResult(responseStatus.data!!)
        else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }