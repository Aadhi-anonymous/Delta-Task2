package com.example.deltatask2

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.color.utilities.Score.score


class MainActivity : AppCompatActivity() {

    lateinit var mycanvas : canvas1
    private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var mydialog1: Dialog




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        val dialog={
            val dialogBinding1 = layoutInflater.inflate(R.layout.activity_main, null)
            val mydialog1 = Dialog(this@MainActivity)
            mydialog1.setContentView(dialogBinding1)
            mydialog1.setCancelable(true)

            val playAgain1=dialogBinding1.findViewById<Button>(R.id.playAgain)

            playAgain1.setOnClickListener {
                mydialog1.dismiss()
                mycanvas.resetGame()

            }



            sf=getSharedPreferences("MY", MODE_PRIVATE)
            var highscore=sf.getString("highscore","").toString()
            var score = mycanvas.score.toString()
            if(highscore!=""){
                if(mycanvas.score>highscore.toFloat()){
                    highscore=mycanvas.score.toString()
                }}
            else{
                highscore=mycanvas.score.toString()
            }
            val hs=dialogBinding1.findViewById<TextView>(R.id.score_text_view2)
            hs.text="SCORE:$score"

            val hs1=dialogBinding1.findViewById<TextView>(R.id.score_text_view3)
            hs1.text = "HIGHSCORE:$highscore"





            val editor=sf.edit()
            editor.putString("highscore",highscore)
           editor.apply()

           mydialog1.show()

        }
        mycanvas = canvas1(this,dialog)

        setContentView(mycanvas)



    }
}