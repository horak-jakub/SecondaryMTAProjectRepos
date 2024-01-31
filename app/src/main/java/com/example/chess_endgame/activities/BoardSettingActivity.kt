package com.example.chess_endgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess_endgame.main.Game
import com.example.chess_endgame.databinding.ActivityBoardSettingBinding


class BoardSettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.QGameButton.setOnClickListener {
            Game.createQChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.RGameButton.setOnClickListener {
            Game.createRChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.RRGameButton.setOnClickListener {
            Game.createRRChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.QQGameButton.setOnClickListener {
            Game.createQQChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.BBGameButton.setOnClickListener {
            Game.createBBChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.NNBGameButton.setOnClickListener {
            Game.createNNBChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.QBGameButton.setOnClickListener {
            Game.createQBChessboard()
            startActivity(Intent(this, ChessboardActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

}