package com.example.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Outline
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val txtResult: TextView = findViewById(R.id.txt_result)
        val lastResult: TextView = findViewById(R.id.txt_last_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)
        val slider: Slider = findViewById(R.id.slide)

        txtResult.translationZ = 30f


        // banco de dados de preferencia
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        if (result != null) {
            lastResult.text = "Última aposta: \n$result"
        }

        slider.addOnChangeListener { slider, value, fromUser ->

            btnGenerate.setOnClickListener {
                numberGenerator(slider, txtResult)

            }
        }
    }

    private fun numberGenerator(slider: Slider, txtResult: TextView) {

        val sliderResult = slider.value.toInt()

        val numbers = mutableSetOf<Int>()
        val random = java.util.Random()

        while (true) {
            val number = random.nextInt(60) // 0..59
            numbers.add(number + 1)

            if (numbers.size == sliderResult) {
                break
            }
        }
        val order = numbers.toSortedSet().joinToString(" | ")
        txtResult.text = order

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply() // salva de forma assincrona


    }
}
