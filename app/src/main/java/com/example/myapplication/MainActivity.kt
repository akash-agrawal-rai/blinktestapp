package com.example.myapplication

//import org.openapitools.client.apis.*
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*
//import java.time.OffsetDateTime
//import java.time.format.DateTimeFormatter
import ai.radius.blink.*
import ai.radius.blink.infrastructure.*
import ai.radius.blink.model.*
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.datetime.Instant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        System.getProperties().setProperty(baseUrlKey, "http://192.168.100.240:8000")
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Function to fetch items
        val apiClient = ApiClient("http://192.168.100.240:8000")
        val webService = apiClient.createService(DefaultApi::class.java)

        val call = webService.scanGet()
        call.enqueue(object: Callback<ScanResponseData> {
            override fun onResponse(call: Call<ScanResponseData>, response: Response<ScanResponseData>) {
                Log.d("asd", "Reached onResponse")
                if (response.isSuccessful) {
                    val result = response.body()

                    println(result)

                    val adapter = result?.scanData?.let { ItemsAdapter(it) }
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ScanResponseData>, t: Throwable) {
                t.printStackTrace()
            }
        })
//        val result : ScanResponseData = fetchItems()
//        println(result)
//
//        val adapter = result?.scanData?.let { ItemsAdapter(it) }
//        recyclerView.adapter = adapter
    }

    private fun fetchItems(): ScanResponseData {
        // Simulate fetching items
//        return listOf(
//            Item("Item 1", "Description 1"),
//            Item("Item 2", "Description 2"),
//            Item("Item 3", "Description 3"),
//            Item("Item 4", "Description 4")
//        )
        return ScanResponseData(
            id = UUID.randomUUID(),
//            createdAt = OffsetDateTime.parse("2025-03-19T12:45:48.697541", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            createdAt = Instant.parse("2025-03-19T12:45:48.697541+00:00"),
            deviceId = "store3000-t1-ca1",
            status = ScanResponseData.Status.ACTIVE,
            scanData = listOf(
                ScannedItem(
                    lineItemId = 1,
                    data = "123345678901",
                    dataEncoding = null,
                    extras = ScannedItemExtras(sku = "123456")
                ),
                ScannedItem(
                    lineItemId = 2,
                    data = "123345678902",
                    dataEncoding = null,
                    extras = ScannedItemExtras(sku = "123451")
                )
            )
        )
    }
}