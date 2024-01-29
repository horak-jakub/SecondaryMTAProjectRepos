package com.example.chess_endgame

import android.icu.number.IntegerWidth
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chess.console.Game
import com.example.chess_endgame.Background.Chessboard
import com.example.chess_endgame.Background.Computer
import com.example.chess_endgame.databinding.ActivityBoardSettingBinding
import com.example.chess_endgame.databinding.ActivityChessboardBinding

class ChessboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChessboardBinding

    val game : Game = Game()
    val blackKing = ImageView(this)
    val pieces = (mutableListOf <ImageView>())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createImages()

        binding.button01.setOnClickListener() {
            executeMove(0, 1)
        }
        binding.button0Neg1.setOnClickListener() {
            executeMove(0, -1)
        }
        binding.button10.setOnClickListener() {
            executeMove(1, 0)
        }
        binding.button11.setOnClickListener() {
            executeMove(1, 1)
        }
        binding.buttonNeg10.setOnClickListener() {
            executeMove(-1,0)
        }
        binding.buttonNeg1Neg1.setOnClickListener() {
            executeMove(-1, -1)
        }
        binding.buttonNeg11.setOnClickListener() {
            executeMove(-1, 1)
        }
    }

    fun executeMove(file : Int, row : Int) {
        if (game.move(file, row)) {
            blackKing.setImageResource(R.drawable.chess_kdt45)
        }
    }

    fun createImages() {

    }
}