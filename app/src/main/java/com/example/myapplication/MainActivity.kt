package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import rai.blink.api.DefaultApi


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("main", "onCreate: Init")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json()
            }
        }

        val apiInstance = DefaultApi("http://192.168.29.19:8000", client)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                apiInstance.smartsense().collect { data ->
                    Log.d("coro", "Received: ${data.body()}")
                    val adapter = ItemsAdapter(data.body().scanData)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}