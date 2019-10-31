package com.henry.springretrofit.config

import com.henry.springretrofit.api.NaverSearchApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NaverRetrofitConfig : RetrofitConfig() {

    @Value("\${naverUrl}")
    private lateinit var naverUrl: String

    @Bean
    fun naverSearchApi(): NaverSearchApi {
        val retrofit = buildRetrofit(naverUrl, client())
        return retrofit.create(NaverSearchApi::class.java)
    }
}