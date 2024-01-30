package com.example.chess_endgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess.console.Game
import com.example.chess_endgame.Score.ScoreAdapter
import com.example.chess_endgame.databinding.ActivityBoardSettingBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


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

    }

}