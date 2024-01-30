package com.example.chess.console

import com.example.chess_endgame.Background.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


fun main() {
    //Game().run()
}

class Game(jsonSettings : String) {

    companion object {
        private var gameId : String = ""

        fun createQChessboard() {
            //board.placePiece(Queen(true, Coordinates('a', '1')))
            //board.placePiece(King(true, Coordinates('h', '8')))
            //board.placePiece(King(false, Coordinates('e', '4')))
            gameId = "Q_game"
        }

        fun createRChessboard() {
            //board.placePiece(Rook(true, Coordinates('h', '1')))
            //board.placePiece(King(true, Coordinates('a', '8')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "R_game"
        }

        fun createBBChessboard() {
            //board.placePiece(Bishop(true, Coordinates('a', '3')))
            //board.placePiece(Bishop(true, Coordinates('h', '1')))
            //board.placePiece(King(true, Coordinates('g', '2')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "BB_game"
        }

        fun createQQChessboard() {
            //board.placePiece(Queen(true, Coordinates('h', '1')))
            //board.placePiece(Queen(true, Coordinates('h', '2')))
            //board.placePiece(King(true, Coordinates('g', '3')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "QQ_game"
        }

        fun createRRChessboard() {
            //board.placePiece(Rook(true, Coordinates('a', '1')))
            //board.placePiece(Rook(true, Coordinates('b', '1')))
            //board.placePiece(King(true, Coordinates('h', '8')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "RR_game"
        }

        fun createNNBChessboard() {
            //board.placePiece(Knight(true, Coordinates('a', '8')))
            //board.placePiece(Knight(true, Coordinates('h', '1')))
            //board.placePiece(Bishop(true, Coordinates('a', '6')))
            //board.placePiece(King(true, Coordinates('e', '1')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "NNB_game"
        }

        fun createQBChessboard() {
            //board.placePiece(Bishop(true, Coordinates('h', '1')))
            //board.placePiece(Queen(true, Coordinates('b', '7')))
            //board.placePiece(King(true, Coordinates('g', '2')))
            //board.placePiece(King(false, Coordinates('e', '5')))
            gameId = "QB_game"
        }
    }

    private val comp : Computer
    private var b = true
    private var settings : Settings
    var board : Chessboard

    init {
        board = Chessboard()
        comp = Computer()
        settings = Settings(jsonSettings, gameId)
        generatePieces(settings.pieces)
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
        board = p.first
        println(p.second)
        return board.generateMoves().size != 0
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