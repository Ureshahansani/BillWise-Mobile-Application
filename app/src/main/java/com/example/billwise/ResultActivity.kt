package com.example.billwise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult = findViewById<TextView>(R.id.tv_result)
        val answer = intent.getDoubleExtra("answer", 0.0)
        tvResult.text = answer.toString()
    }

    fun buttonLogin(v: View){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()//destroy the current activity
    }
}
