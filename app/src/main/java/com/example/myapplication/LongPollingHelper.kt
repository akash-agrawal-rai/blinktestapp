package com.example.myapplication

import rai.blink.model.ScanResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rai.blink.api.DefaultApi

object LongPollingHelper {
    fun longPollingFlow(apiInstance: DefaultApi): Flow<ScanResponseData> = flow {
        while (true) {
            val scanData: ScanResponseData = apiInstance.scanGet().body()
            emit(scanData)
        }
    }.flowOn(Dispatchers.IO)
}