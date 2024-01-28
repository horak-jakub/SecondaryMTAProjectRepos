package com.example.chess_endgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess_endgame.databinding.ActivityBoardSettingBinding
import com.example.chess_endgame.databinding.ActivityChessboardBinding

class ChessboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChessboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}