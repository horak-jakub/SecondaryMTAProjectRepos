package com.example.chess_endgame.background

const val maximum = 10000
var depth = 0
const val baseDepth = 5
val kingPosEval = listOf(100, 95, 85, 70, 95, 80, 60, 40, 85, 60, 10, 0, 70, 40, 0, -20)

class Computer {

    private val repeatedMoves = mutableListOf<Array<Char>>()

    fun minMax(board : Chessboard) : Pair<Chessboard, Int> {
        depth = baseDepth
        if (evaluatePosition(board) > 60) depth += 2
        board.pieces.forEach {
            if (it is Queen) {
                depth--
            }
        }
        //if (evaluatePosition(board) > 80) depth += 1
        var finalBoard = Chessboard()

        var max = -maximum
        var j : Long = 0

        for (it in board.generateMoves()) {
            val pair = minMax(it, 1, max)
            j += pair.second
            if (pair.first > max) {
                var b = true
                for (r in repeatedMoves) {
                    if (r.contentEquals(it.board)) b = false
                }
                if (b) {
                    finalBoard = it
                    max = pair.first
                }
            }
        }

        repeatedMoves.add(finalBoard.board)
        println("\n\n\nITERATIONS: $j")

        return Pair(finalBoard, max)
    }

    //Pair<level, iterations>
    private fun minMax(board : Chessboard, i : Int, knownBest : Int) : Pair<Int, Long> {

        //iterations counter
        var j : Long = 0

        if (i < depth) {
            var max = -maximum
            if (board.whiteOnTurn) {
                val moves = board.generateMoves()
                for (m in moves) {
                    val pair = minMax(m,i + 1, max)
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
                if (moves.isEmpty()) {
                    //Check mate
                    return if (board.whiteControl[board.blackKing.coordinates.index]) {
                        Pair(maximum - i, 1)
                    }
                    //Stalemate
                    else {
                        Pair(-maximum, 1)
                    }
                }
                for (m in moves) {
                    for (p in m.pieces) {
                        if (p.coordinates.equals(m.blackKing.coordinates)) {
                            return Pair(-maximum, 1)
                        }
                    }
                    val pair = minMax(m, i + 1, min)
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

    companion object {
        fun evaluatePosition(board : Chessboard) : Int {

            var whiteKingCoord = Coordinates(0,0)
            board.pieces.forEach {
                if (it is King) whiteKingCoord = it.coordinates
            }
            var a = (whiteKingCoord.getDistance(board.blackKing.coordinates) - 1) * 2
            a *= -a
            a += getKingPosEvaluation(board.blackKing)

            return a
        }

        private fun getKingPosEvaluation(king : King) : Int {
            var file = king.coordinates.file
            var row = king.coordinates.row
            if (file >= Coordinates.count / 2) file = Coordinates.count - 1 - file
            if (row >= Coordinates.count / 2) row = Coordinates.count - 1 - row
            return kingPosEval[row * Coordinates.count / 2 + file]
        }
    }

}