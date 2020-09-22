package com.dzakdzaks.inventmvvm.data.remote

import com.dzakdzaks.inventmvvm.data.entity.BaseResponse
import com.dzakdzaks.inventmvvm.data.entity.MasterProduct
import com.dzakdzaks.inventmvvm.data.entity.TxProduct
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Monday, 21 September 2020 at 11:02 PM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.data.remote
 * ==================================//==================================
 * ==================================//==================================
 */

interface ApiService {

    companion object {
        private fun loggingInterceptorClient(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptorClient())
            .sslSocketFactory(sslSocketFactory(), loadTrustManager()[0])
            .build()

        private fun sslSocketFactory(): SSLSocketFactory {
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, loadTrustManager(), SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.socketFactory
        }

        private fun loadTrustManager(): Array<X509TrustManager> {
            return arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
        }

    }


    @GET("test_core/v1/get_m_product")
    suspend fun getMasterProducts(): Response<BaseResponse<List<MasterProduct>>>

    @GET("test_core/v1/get_product_price")
    suspend fun getTxProducts(): Response<BaseResponse<List<TxProduct>>>
}