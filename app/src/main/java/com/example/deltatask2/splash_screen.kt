package com.example.deltatask2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class splash_screen : AppCompatActivity() {

    lateinit var anchor : ImageView
    lateinit var tip : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        anchor = findViewById(R.id.anchor24)
        tip = findViewById(R.id.splash)
getmydata()
        anchor.alpha = 0f
        anchor.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
    private fun getmydata() {

        tip = findViewById(R.id.splash)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
            .build()
            .create(apiinterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object: Callback<tips>{
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call:Call<tips>, response:Response<tips>){
                tip.text = "randomTip"
                if (response.isSuccessful) {
                    val tips = response.body()?.tips
                    if (tips != null && tips.isNotEmpty()) {
                        val randomTip = tips[Random.nextInt(tips.size)]
                        tip.text = randomTip
                        Log.d("STRING", randomTip)
                    }
                } else {
                    Log.d("Response Error", response.message())
                }
            }
            override fun onFailure(call: Call<tips>, t: Throwable) {

                Log.d("Failure", "ON FAILURE: ${t.message}")
            }
        })
    }

}