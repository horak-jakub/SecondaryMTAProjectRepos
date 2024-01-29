package com.example.chess_endgame

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chess.console.Game
import com.example.chess_endgame.databinding.ActivityChessboardBinding

class ChessboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChessboardBinding

    val game : Game = Game()
    lateinit var blackKing : ImageView
    val pieces = mutableListOf <ImageView>()
    val piecesSymbols = mutableListOf<Char>()
    val squareSize = (50 * Resources.getSystem().displayMetrics.density).toFloat()




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layout = findViewById<ConstraintLayout>(R.id.piecesLayout)
        blackKing = ImageView(this)
        blackKing.setImageResource(R.drawable.black_king)
        blackKing.x = squareSize * (game.board.blackKing.coordinates.file)
        blackKing.y = squareSize * (7 - game.board.blackKing.coordinates.row)
        layout.addView(blackKing)

        for (i in 0..this.game.board.pieces.size - 1) {
            pieces.add(ImageView(this))
            pieces[i].setImageResource(game.board.pieces[i].getRepresentaniton())
            piecesSymbols.add(game.board.pieces[i].getSymbol())
            layout.addView(pieces[i])
        }

        setWhitePositions()


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
            blackKing.x += squareSize * file
            blackKing.y -= squareSize * row

            game.computeMove()
            setWhitePositions()
        }
    }

    fun setWhitePositions() {

        val layout = findViewById<ConstraintLayout>(R.id.piecesLayout)

        for (piece in this.game.board.pieces) {
            for (i in 0..pieces.size - 1) {
                if (piece.getSymbol() == piecesSymbols[i]) {
                    pieces[i].x = squareSize * (piece.coordinates.file)
                    pieces[i].y = squareSize * (7 - piece.coordinates.row)
                }
            }
        }
    }
}