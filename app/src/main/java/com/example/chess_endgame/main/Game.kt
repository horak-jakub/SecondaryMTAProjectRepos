package com.example.chess_endgame.main

import androidx.lifecycle.ViewModel
import com.example.chess_endgame.background.*
import com.example.chess_endgame.score.Score
import com.example.chess_endgame.score.ScoreDao
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
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
    private val scoreDao : ScoreDao
    private var moveCount = 0
    var finished = false

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

        var command: String
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

    private fun readCommand(): String {
        print("Zadej příkaz: ")
        return readlnOrNull().toString()
    }

    private fun runCommand(command : String) : Boolean {
        //board = board.movePiece(Coordinates(command.get(0), command.get(1)), Coordinates(command.get(2), command.get(3)))
        val b = board.movePiece(board.blackKing, Coordinates(command[0], command[1]))
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

    @OptIn(DelicateCoroutinesApi::class)
    fun computeMove() : Boolean {
        val p = comp.minMax(board)
        moveCount++
        board = p.first
        println(p.second)


        if (board.generateMoves().size == 0) {
            GlobalScope.launch {
                finished = true
                scoreDao.addScore(Score(0, Date(), board.toString(), moveCount))
            }
            return false
        }
        return true
    }

    private fun generatePieces(pieces: JSONArray?) {

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

