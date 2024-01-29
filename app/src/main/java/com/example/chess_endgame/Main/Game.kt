package com.example.chess.console

import com.example.chess_endgame.Background.*


fun main() {
    Game().run()
}

class Game {

    companion object {
        var board = Chessboard()

        fun createQChessboard() {
            board.placePiece(Queen(true, Coordinates('h', '1')))
            board.placePiece(King(true, Coordinates('a', '1')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createRChessboard() {
            board.placePiece(Rook(true, Coordinates('h', '1')))
            board.placePiece(King(true, Coordinates('a', '8')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createBBChessboard() {
            board.placePiece(Bishop(true, Coordinates('h', '1')))
            board.placePiece(Bishop(true, Coordinates('a', '3')))
            board.placePiece(King(true, Coordinates('g', '2')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createQQChessboard() {
            board.placePiece(Queen(true, Coordinates('h', '1')))
            board.placePiece(Queen(true, Coordinates('h', '2')))
            board.placePiece(King(true, Coordinates('g', '3')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createRRChessboard() {
            board.placePiece(Rook(true, Coordinates('a', '1')))
            board.placePiece(Rook(true, Coordinates('b', '1')))
            board.placePiece(King(true, Coordinates('h', '8')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createNNBChessboard() {
            board.placePiece(Knight(true, Coordinates('a', '8')))
            board.placePiece(Knight(true, Coordinates('h', '1')))
            board.placePiece(Bishop(true, Coordinates('a', '6')))
            board.placePiece(King(true, Coordinates('e', '1')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }

        fun createQBChessboard() {
            board.placePiece(Bishop(true, Coordinates('h', '1')))
            board.placePiece(Queen(true, Coordinates('b', '7')))
            board.placePiece(King(true, Coordinates('g', '2')))
            board.placePiece(King(false, Coordinates('e', '5')))
        }
    }

    private val comp = Computer()
    private var b = true


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
        val b = board.movePiece(board.blackKing, Coordinates(board.blackKing.coordinates.file + file, board.blackKing.coordinates.row + row))
        if (board != b) {
            board = b
            return true
        }
        return false
    }

    fun computeMove() {
        var p = comp.minMax(board)
        board = p.first
        println(p.second)
    }
}