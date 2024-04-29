package com.example.billwise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeUsageResult : AppCompatActivity() {

    private lateinit var resultRecyclerView: RecyclerView
    private lateinit var showLodingData :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_usage_result)

        resultRecyclerView =findViewById(R.id.item_recycle)
        resultRecyclerView.layoutManager = LinearLayoutManager(this)
        resultRecyclerView.setHasFixedSize(true)
        showLodingData = findViewById(R.id.tvLoadingData)


    }
}