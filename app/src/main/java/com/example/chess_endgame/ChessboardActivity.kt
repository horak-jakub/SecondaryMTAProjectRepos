package com.example.chess_endgame

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chess.console.Game
import com.example.chess_endgame.Background.Coordinates
import com.example.chess_endgame.databinding.ActivityChessboardBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

val squareDpSize = 50

class ChessboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChessboardBinding

    lateinit var game : Game
    lateinit var blackKing : ImageView
    val pieces = mutableListOf <ImageView>()
    val piecesSymbols = mutableListOf<Char>()
    val squareSize = (squareDpSize * Resources.getSystem().displayMetrics.density)



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        game = Game(readSettingsJson())

        binding.newGamButton.setOnClickListener() {
            startActivity(Intent(this, BoardSettingActivity::class.java))
        }
        binding.mateLayout.x = -1000f

        val layout = findViewById<ConstraintLayout>(R.id.piecesLayout)
        blackKing = ImageView(this)
        blackKing.setImageResource(R.drawable.black_king)
        blackKing.x = squareSize * (game.board.blackKing.coordinates.file)
        blackKing.y = squareSize * (7 - game.board.blackKing.coordinates.row)
        layout.addView(blackKing)

        for (i in 0..game.board.pieces.size - 1) {
            pieces.add(ImageView(this))
            pieces[i].setImageResource(game.board.pieces[i].getRepresentaniton())
            piecesSymbols.add(game.board.pieces[i].getSymbol())
            layout.addView(pieces[i])
        }

        setWhitePositions()


        binding.piecesLayout.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {

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

    fun processClick(x : Float, y : Float) {
        val file = (x / squareDpSize).toInt()
        val row = Coordinates.count - (y / squareDpSize).toInt() - 1

        if (game.move(file, row)) {
            blackKing.x = file.toFloat() * squareSize
            blackKing.y = (Coordinates.count - 1 - row.toFloat()) * squareSize

            var b = game.computeMove()
            setWhitePositions()
            if (!b) {
                binding.mateLayout.x = 0f
            }
        }
    }


    fun setWhitePositions() {

        val layout = findViewById<ConstraintLayout>(R.id.piecesLayout)

        var temp = piecesSymbols.toMutableList()

        for (piece in game.board.pieces) {
            for (i in 0..pieces.size - 1) {
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


    fun readSettingsJson () : String {
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