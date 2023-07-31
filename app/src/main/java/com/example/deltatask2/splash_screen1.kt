package com.example.deltatask2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class splash_screen1 : AppCompatActivity() {

    lateinit var anchor : ImageView
    lateinit var imageview2 : ImageView
    lateinit var imageview5: ImageView
    lateinit var imageview6 : ImageView
    lateinit var tip : TextView
    lateinit var name2 : TextView
    lateinit var name5 : TextView
    lateinit var name6 : TextView
    lateinit var des2 : TextView
    lateinit var des5 : TextView
    lateinit var des6 : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        anchor = findViewById(R.id.anchor24)
        tip = findViewById(R.id.splash)

        imageview2 = findViewById(R.id.imageView2)
        imageview5 = findViewById(R.id.imageView5)
        imageview6 = findViewById(R.id.imageView6)

        name2 = findViewById(R.id.name2)
        name5 = findViewById(R.id.name5)
        name6 = findViewById(R.id.name6)

        des2 = findViewById(R.id.des2)
        des5 = findViewById(R.id.des5)
        des6 = findViewById(R.id.des6)



        getmydata()
        anchor.alpha = 0f
        anchor.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
    fun getmydata(){
        val retrofit= ServiceBuilder.buildService(apiinterface::class.java)
    val obj1=p("chaser")
    retrofit.getplayer(obj1).enqueue(
    object:Callback<players>{
        override fun onResponse(call: Call<players>, response: Response<players>) {
            val cn1:TextView=findViewById(R.id.name2)
            val cd1:TextView=findViewById(R.id.name5)

            val c1:ImageView=findViewById(R.id.imageView2)


            val cn2:TextView=findViewById(R.id.name6)
            val cd2:TextView=findViewById(R.id.des2)

            val c2:ImageView=findViewById(R.id.imageView5)

            val cn3:TextView=findViewById(R.id.des5)
            val cd3:TextView=findViewById(R.id.des6)

            val c3:ImageView=findViewById(R.id.imageView6)

            val chaser = response.body()?.characters

            val chas1= chaser?.get(0)
            cn1.text= chas1!!.name
            cd1.text= chas1!!.description

            Picasso.get().load("${chas1.imageUrl}").into(c1)

            val chas2= chaser?.get(1)
            cn2.text= chas2!!.name
            cd2.text= chas2!!.description

            Picasso.get().load("${chas2.imageUrl}").into(c2)

            val chas3= chaser?.get(2)
            cn3.text= chas3!!.name
            cd3.text= chas3!!.description

            Picasso.get().load("${chas3.imageUrl}").into(c3)

            Log.d("TAG","response success for player ${chas1.name}")
        }

        override fun onFailure(call: Call<players>, t: Throwable) {
            Log.d("TAG","response not found for chaser")

        }
    }
    )}}