package com.henry.springretrofit.service

import com.henry.springretrofit.api.ApiCaller
import com.henry.springretrofit.api.NaverSearchApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NaverSearchService {

    @Autowired
    private lateinit var naverSearchApi: NaverSearchApi

    fun blogSearch(query: String) = ApiCaller.wrapper { naverSearchApi.blogSearch(query) }
}