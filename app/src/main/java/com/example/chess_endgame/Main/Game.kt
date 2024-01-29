package com.example.chess.console

import com.example.chess_endgame.Background.*


fun main() {
    Game().run()
}

class Game {

    //companion object {
        var board = Chessboard()
    //}

    private val comp = Computer()
    private var b = true

    constructor() {
        board.placePiece(Queen(true, Coordinates('h', '1')))
        board.placePiece(King(true, Coordinates('e', '1')))
        board.placePiece(King(false, Coordinates('e', '5')))
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
        val b = board.movePiece(board.blackKing, Coordinates(board.blackKing.coordinates.file + file, board.blackKing.coordinates.row + row))
        if (board != b) {
            board = b
            return true
        }
        return false
    }

    fun computeMove() {
        board = comp.minMax(board).first
    }
}