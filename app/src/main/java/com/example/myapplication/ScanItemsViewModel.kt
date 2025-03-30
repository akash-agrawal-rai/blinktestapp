package com.example.myapplication

import rai.blink.api.DefaultApi
import rai.blink.model.ScanResponseData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


class ScanItemsViewModel: ViewModel() {
    private val _data = MutableStateFlow(
        value = ScanResponseData("init", createdAt = Clock.System.now(), deviceId = "init", status = ScanResponseData.Status.IDLE, scanData = listOf())
    )
    val scan = _data.asStateFlow()

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
    }

    private val apiInstance = DefaultApi("http://192.168.29.19:8000", client)

    init {
        startLongPolling()
    }

    private fun startLongPolling() {
        viewModelScope.launch {
            LongPollingHelper.longPollingFlow(apiInstance).collectLatest { scan ->
                _data.value = scan
            }
        }
    }
}