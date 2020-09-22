package com.dzakdzaks.inventmvvm.data.entity

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("status_code")
    val statusCode: String? = null,

    @field:SerializedName("status_message_ind")
    val statusMessageInd: String? = null,

    @field:SerializedName("status_message_eng")
    val statusMessageEng: String? = null,

    @field:SerializedName("value")
    val value: T? = null
)