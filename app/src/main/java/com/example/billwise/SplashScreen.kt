package com.example.billwise


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashTimer = object : Thread() {
            override fun run() {
                try {
                    sleep(2000) // 2 seconds delay
                    val intent = Intent(baseContext, Registration::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        splashTimer.start()
    }
        
}


