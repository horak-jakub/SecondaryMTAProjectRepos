package com.example.chess_endgame.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.chess_endgame.main.Game
import com.example.chess_endgame.background.Coordinates
import com.example.chess_endgame.main.GameFactory
import com.example.chess_endgame.R
import com.example.chess_endgame.score.ScoreDatabase
import com.example.chess_endgame.databinding.ActivityChessboardBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

const val squareDpSize = 50

class ChessboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChessboardBinding

    private lateinit var game: Game
    private lateinit var blackKing : ImageView
    private val pieces = mutableListOf <ImageView>()
    private val piecesSymbols = mutableListOf<Char>()
    private val squareSize = (squareDpSize * Resources.getSystem().displayMetrics.density)

    private val scoreDao by lazy { ScoreDatabase.getDatabase(this).scoreDao() }



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = GameFactory(readSettingsJson(), scoreDao)
        game = ViewModelProvider(this, factory)[Game::class.java]

        binding.recordsButton.setOnClickListener {
            finish()
            startActivity(Intent(this, ScoreActivity::class.java))
        }

        binding.newGameButton.setOnClickListener {
            finish()
            startActivity(Intent(this, BoardSettingActivity::class.java))
        }

        if (!game.finished) {
            binding.mateLayout.x = -1000f
        }

        val layout = findViewById<ConstraintLayout>(R.id.piecesLayout)
        blackKing = ImageView(this)
        blackKing.setImageResource(R.drawable.black_king)
        blackKing.x = squareSize * (game.board.blackKing.coordinates.file)
        blackKing.y = squareSize * (7 - game.board.blackKing.coordinates.row)
        layout.addView(blackKing)

        for (i in 0..<game.board.pieces.size) {
            pieces.add(ImageView(this))
            pieces[i].setImageResource(game.board.pieces[i].getRepresentaniton())
            piecesSymbols.add(game.board.pieces[i].getSymbol())
            layout.addView(pieces[i])
        }

        setWhitePositions()


        binding.piecesLayout.setOnTouchListener { v, event ->
            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    processClick(
                        event.x.toInt() / Resources.getSystem().displayMetrics.density,
                        event.y.toInt() / Resources.getSystem().displayMetrics.density)
                }

                else -> { }
            }
            true
        }

    }

    private fun processClick(x : Float, y : Float) {
        val file = (x / squareDpSize).toInt()
        val row = Coordinates.count - (y / squareDpSize).toInt() - 1

        if (game.move(file, row)) {
            blackKing.x = file.toFloat() * squareSize
            blackKing.y = (Coordinates.count - 1 - row.toFloat()) * squareSize

            val b = game.computeMove()
            setWhitePositions()
            if (!b) {
                binding.mateLayout.x = 0f
            }
        }
    }


    private fun setWhitePositions() {

        val temp = piecesSymbols.toMutableList()

        for (piece in game.board.pieces) {
            for (i in 0..<pieces.size) {
                if (piece.getSymbol() == piecesSymbols[i]) {
                    pieces[i].x = squareSize * (piece.coordinates.file)
                    pieces[i].y = squareSize * (7 - piece.coordinates.row)
                    piecesSymbols[i] = '0'
                    break
                }
            }
        }

        piecesSymbols.clear()
        piecesSymbols.addAll(temp)
    }


    private fun readSettingsJson () : String {
        var string: String? = ""
        val stringBuilder = StringBuilder()
        val inputStream: InputStream = resources.openRawResource(applicationContext.resources.getIdentifier("settings", "raw", applicationContext.packageName))
        //val inputStream: InputStream = resources.openRawResource(R.raw.settings)
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            try {
                if (reader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")

        }
        inputStream.close()
        return stringBuilder.toString()
    }

}