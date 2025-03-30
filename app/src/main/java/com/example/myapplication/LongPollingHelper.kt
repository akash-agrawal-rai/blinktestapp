package com.example.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import rai.blink.model.ScanResponseData
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rai.blink.api.DefaultApi
import rai.blink.infrastructure.HttpResponse

fun smartsense(): Flow<HttpResponse<ScanResponseData>> = flow {
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
    }

    val apiInstance = DefaultApi("http://192.168.29.19:8000", client)

    while (true) {
        emit(apiInstance.scanGet())
        delay(10)
    }
}.flowOn(Dispatchers.IO)
