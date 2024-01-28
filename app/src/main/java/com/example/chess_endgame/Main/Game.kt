package com.example.chess.console

import com.example.chess_endgame.Background.*


fun main() {
    Game().run()
}

class Game {

    var board = Chessboard()
    val comp = Computer()
    var b = true

    fun run() {
        //board.placePiece(Bishop(true, Coordinates('d', '7')))
        //board.placePiece(Bishop(true, Coordinates('e', '1')))
        board.placePiece(Knight(true, Coordinates('a', '7')))
        board.placePiece(Bishop(true, Coordinates('h', '2')))
        board.placePiece(Bishop(true, Coordinates('h', '1')))
        board.placePiece(King(true, Coordinates('e', '1')))
        board.placePiece(King(false, Coordinates('d', '5')))
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
}