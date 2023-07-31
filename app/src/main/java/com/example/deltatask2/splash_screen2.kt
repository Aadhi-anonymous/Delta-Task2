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

class splash_screen2 : AppCompatActivity() {

    lateinit var anchor : ImageView
    lateinit var tip : TextView
    lateinit var imageview : ImageView
    lateinit var imageview3: ImageView
    lateinit var imageview4 : ImageView
    lateinit var name1 : TextView
    lateinit var name3 : TextView
    lateinit var name4 : TextView
    lateinit var des1 : TextView
    lateinit var des3 : TextView
    lateinit var des4 : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        anchor = findViewById(R.id.anchor24)
        tip = findViewById(R.id.splash)

        imageview = findViewById(R.id.imageView)
        imageview3 = findViewById(R.id.imageView3)
        imageview4 = findViewById(R.id.imageView4)

        name1 = findViewById(R.id.name1)
        name3 = findViewById(R.id.name3)
        name4 = findViewById(R.id.name4)

        des1 = findViewById(R.id.des1)
        des3 = findViewById(R.id.des3)
        des4 = findViewById(R.id.des4)


        getmydata()
        anchor.alpha = 0f
        anchor.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun getmydata() {
        val retrofit= ServiceBuilder.buildService(apiinterface::class.java)
        val obj=p("player")
        retrofit.getplayer(obj).enqueue(
            object:Callback<players>{
                override fun onResponse(call: Call<players>, response: Response<players>) {
                    val pn1:TextView=findViewById(R.id.name1)
                    val pd1:TextView=findViewById(R.id.name3)

                    val p1:ImageView=findViewById(R.id.imageView)

                    val pn2:TextView=findViewById(R.id.name4)
                    val pd2:TextView=findViewById(R.id.des1)

                    val p2:ImageView=findViewById(R.id.imageView3)

                    val pn3:TextView=findViewById(R.id.des3)
                    val pd3:TextView=findViewById(R.id.des4)

                    val p3:ImageView=findViewById(R.id.imageView4)

                    val player = response.body()?.characters

                    val play1= player?.get(0)
                    pn1.text= play1!!.name
                    pd1.text= play1!!.description


                    Picasso.get().load("${play1.imageUrl}").into(p1)

                    val play2= player?.get(1)
                    pn2.text= play2!!.name
                    pd2.text= play2!!.description


                    Picasso.get().load("${play2.imageUrl}").into(p2)

                    val play3= player?.get(2)
                    pn3.text= play3!!.name
                    pd3.text= play3!!.description


                    Picasso.get().load("${play3.imageUrl}").into(p3)
                    Log.d("TAG","response success for player ${play1.imageUrl}")
                }

                override fun onFailure(call: Call<players>, t: Throwable) {
                    Log.d("TAG","response not found for player")

                }
            }
        )





        }

    }
