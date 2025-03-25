package com.example.myapplication

import ai.radius.blink.DefaultApi
import ai.radius.blink.infrastructure.ApiClient
import ai.radius.blink.model.ScanResponseData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScanItemsViewModel: ViewModel() {
    private val _data = MutableStateFlow<ScanResponseData>(
        value = ScanResponseData()
    )
    val scan = _data.asStateFlow()

    private val apiClient = ApiClient("http://192.168.29.19:8000")
    private val webService = apiClient.createService(DefaultApi::class.java)

    private val call = webService.scanGet()

    init {
        startLongPolling()
    }

    private fun startLongPolling() {
        viewModelScope.launch {
            LongPollingHelper.longPollingFlow(call).collectLatest { scan ->
                _data.value = scan
            }
        }
    }
}