package com.example.chess_endgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chess_endgame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val img = findViewById<ImageView>(R.id.imageView2)
        //img.setImageResource(R.drawable.chess_kdt45)

        //binding.newGameButton.setOnClickListener {
        //    startActivity(Intent(this, BoardSettingActivity::class.java))
        //}

        binding.newGameButton.setOnClickListener {
            startActivity(Intent(this, ScoreActivity::class.java))
        }
    }


}


