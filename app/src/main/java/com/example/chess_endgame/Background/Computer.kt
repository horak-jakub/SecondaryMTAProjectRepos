package com.example.chess_endgame.Background

const val maximum = 10000
val depth = 7
val kingPosEval = listOf<Int>(100, 95, 85, 70, 95, 80, 60, 40, 85, 60, 10, 0, 70, 40, 0, -20)

class Computer {

    fun minMax(board : Chessboard) : Pair<Chessboard, Int> {
        var finalBoard : Chessboard = Chessboard()
        var max = -maximum
        var j : Long = 0

        for (it in board.generateMoves()) {
            var pair = minMax(it, 1, max)
            j += pair.second
            if (pair.first > max) {
                finalBoard = it
                max = pair.first
            }
        }

        println("\n\n\nITERATIONS: $j")

        return Pair(finalBoard, max)
    }

    //Pair<level, iterations>
    fun minMax(board : Chessboard, i : Int, knownBest : Int) : Pair<Int, Long> {

        //iterations counter
        var j : Long = 0

        if (i < depth) {
            var max = -maximum
            if (board.whiteOnTurn) {
                val moves = board.generateMoves()
                for (m in moves) {
                    var pair = minMax(m,i + 1, max)
                    j += pair.second

                    if (pair.first > max) {
                        max = pair.first

                        //alpha beta pruning
                        if (max >= knownBest) break
                    }
                }
                return Pair(max, j)
            }
            else {
                var min = maximum
                val moves = board.generateMoves()
                if (moves.count() == 0) {
                    //Check mate
                    if (board.whiteControl[board.blackKing.coordinates.index]) {
                        return Pair(maximum - i, 1)
                    }
                    //Stalemate
                    else {
                        return Pair(-maximum, 1)
                    }
                }
                for (m in moves) {
                    for (p in m.pieces) {
                        if (p.coordinates.equals(m.blackKing.coordinates)) {
                            return Pair(-maximum, 1)
                        }
                    }
                    var pair = minMax(m, i + 1, min)
                    j += pair.second

                    if (pair.first < min) {
                        min = pair.first

                        //alpha beta pruning
                        if (min <= knownBest) break
                    }
                }
                return Pair(min, j)
            }
        }
        return Pair(evaluatePosition(board) - i, 1)
    }


    fun evaluatePosition(board : Chessboard) : Int {

        var whiteKingCoord = Coordinates(0,0)
        board.pieces.forEach() {
            if (it is King) whiteKingCoord = it.coordinates
        }
        var a = (whiteKingCoord.getDistance(board.blackKing.coordinates) - 1) * 2
        a = -a * a
        a += getKingPosEvaluation(board.blackKing)

        return a
    }

    fun getKingPosEvaluation(king : King) : Int {
        var file = king.coordinates.file
        var row = king.coordinates.row
        if (file >= Coordinates.count / 2) file = Coordinates.count - 1 - file
        if (row >= Coordinates.count / 2) row = Coordinates.count - 1 - row
        return kingPosEval[row * Coordinates.count / 2 + file]
    }
}