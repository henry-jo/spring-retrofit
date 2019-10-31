package com.henry.springretrofit.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Configuration
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * retrofit common config
 */

@Configuration
class RetrofitConfig {
    protected fun buildRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(getConverter())
            .client(client)
            .baseUrl(baseUrl)
            .build()
    }

    // json converter configure
    fun getConverter(): Converter.Factory {
        return JacksonConverterFactory.create(BeanConfig.getObjectMapper())
    }

    fun getLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return logging
    }

    final fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor()).build()
    }
}