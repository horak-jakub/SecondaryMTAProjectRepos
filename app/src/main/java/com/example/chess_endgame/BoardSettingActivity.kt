package com.example.chess_endgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess_endgame.databinding.ActivityBoardSettingBinding


class BoardSettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}