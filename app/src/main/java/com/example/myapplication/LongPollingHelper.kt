package com.example.myapplication

import ai.radius.blink.model.ScanResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call

object LongPollingHelper {
    fun longPollingFlow(call: Call<ScanResponseData>): Flow<ScanResponseData> = flow {
        while (true) {
            val scanData: ScanResponseData? = call.clone().execute().body()
            if (scanData != null) {
                emit(scanData)
            }
        }
    }.flowOn(Dispatchers.IO)
}