package com.example.chess.console

import androidx.lifecycle.ViewModel
import com.example.chess_endgame.Background.*
import com.example.chess_endgame.Score.Score
import com.example.chess_endgame.Score.ScoreDao
import com.example.chess_endgame.Score.ScoreDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Date


fun main() {
    //Game().run()
}

class Game(jsonSettings : String, scoreDao: ScoreDao) : ViewModel() {

    companion object {
        private var gameId : String = ""

        fun createQChessboard() {
            gameId = "Q_game"
        }

        fun createRChessboard() {
            gameId = "R_game"
        }

        fun createBBChessboard() {
            gameId = "BB_game"
        }

        fun createQQChessboard() {
            gameId = "QQ_game"
        }

        fun createRRChessboard() {
            gameId = "RR_game"
        }

        fun createNNBChessboard() {
            gameId = "NNB_game"
        }

        fun createQBChessboard() {
            gameId = "QB_game"
        }
    }

    private val comp : Computer
    private var b = true
    private var settings : Settings
    var board : Chessboard
    val scoreDao : ScoreDao
    var moveCount = 0

    init {
        board = Chessboard()
        comp = Computer()
        settings = Settings(jsonSettings, gameId)
        generatePieces(settings.pieces)
        this.scoreDao = scoreDao
    }


    fun run() {
        //board.placePiece(Bishop(true, Coordinates('d', '7')))
        //board.placePiece(Bishop(true, Coordinates('e', '1')))

        var command = ""
        do {
            if (b) {
                board = comp.minMax(board).first
                println("----------------------------------------------")
                println(board.toString())
            }
            command = readCommand()
            b = runCommand(command)
            if (command == "konec") {
                break
            }

        } while (true)
    }

    fun readCommand(): String {
        print("Zadej příkaz: ")
        return readLine().toString()
    }

    fun runCommand(command : String) : Boolean {
        //board = board.movePiece(Coordinates(command.get(0), command.get(1)), Coordinates(command.get(2), command.get(3)))
        val b = board.movePiece(board.blackKing, Coordinates(command.get(0), command.get(1)))
        if (board != b) {
            board = b
            println(board)
            return true
        }
        return false
    }

    fun move(file : Int, row : Int) : Boolean {
        val b = board.movePiece(board.blackKing, Coordinates(file, row))
        if (board != b) {
            board = b
            return true
        }
        return false
    }

    fun computeMove() : Boolean {
        var p = comp.minMax(board)
        moveCount++
        board = p.first
        println(p.second)


        if (board.generateMoves().size == 0) {
            GlobalScope.launch {
                scoreDao.addScore(Score(0, Date(), board.toString(), moveCount))
            }
            return false
        }
        return true
    }

    fun generatePieces(pieces: JSONArray?) {
        var piece : Piece
        pieces?.let {
            for (i in 0 until pieces.length()) {
                val piece = pieces.getJSONObject(i)
                val coord = Coordinates(piece.getString("file").toCharArray()[0], piece.getString("row").toCharArray()[0])

                when (piece.getString("symbol")) {
                    "k" -> {
                        board.placePiece(King(false, coord))
                    }
                    "K" -> {
                        board.placePiece(King(true, coord))
                    }
                    "Q" -> {
                        board.placePiece(Queen(true, coord))
                    }
                    "R" -> {
                        board.placePiece(Rook(true, coord))
                    }
                    "B" -> {
                        board.placePiece(Bishop(true, coord))
                    }
                    "N" -> {
                        board.placePiece(Knight(true, coord))
                    }
                }
            }
        }
    }

}

