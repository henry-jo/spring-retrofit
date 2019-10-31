package com.henry.springretrofit.api

import mu.KLogging
import org.springframework.web.client.ResourceAccessException
import retrofit2.Call

object ApiCaller : KLogging() {

    inline fun <reified T> wrapper(block: () -> Call<T>): T? {
        try {
            val response = block().execute()

            return if (response.isSuccessful) {
                response.body()
            } else {
                logger.error("Retrofit API CALL 실패 [response=$response]")

                throw RuntimeException()
            }
        } catch (e: ResourceAccessException) {
            // ... ex) logging or retryable
            throw e
        } catch (e: RuntimeException) {
            // ... ex) logging or throw specify exception
            throw e
        }
    }
}