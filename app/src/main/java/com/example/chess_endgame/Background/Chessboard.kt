package com.example.chess_endgame.Background


private const val emptySquare : Char = '.'


class Chessboard {

    var board = Array<Char>(64) { emptySquare }
    val whiteControl = Array<Boolean>(64) { false }
    var pieces = mutableListOf<Piece>()
    var blackKing = King(false, Coordinates(0, 7))
    var whiteOnTurn : Boolean = false

    constructor() {
    }

    constructor(parentBoard : Chessboard, originalPiece : Piece, movedPiece : Piece) {
        whiteOnTurn = !parentBoard.whiteOnTurn
        board = parentBoard.board.copyOf()
        pieces = parentBoard.pieces.toMutableList()
        blackKing = parentBoard.blackKing
        if (movedPiece is King && !movedPiece.white) {
            blackKing = movedPiece
        }
        else {
            pieces.remove(originalPiece)
            pieces.add((movedPiece))
        }

        board[originalPiece.coordinates.index] = emptySquare
        board[movedPiece.coordinates.index] = movedPiece.getSymbol()

        generateWhiteControl()
    }


    fun placePiece(piece : Piece) {
        board[piece.coordinates.index] = piece.getSymbol()
        if (piece is King && !piece.white) {
            blackKing = piece
        }
        else {
            pieces.add(piece)
        }
        for (i in 0..63) { whiteControl[i] = false }
        generateWhiteControl()
    }

    fun movePiece(oldPiece : Piece, newCoord : Coordinates) : Chessboard {
        if (!newCoord.valid()) throw Exception("Invalid coordinates")
        if (!oldPiece.canYouMove(newCoord.file - oldPiece.coordinates.file, newCoord.row - oldPiece.coordinates.row) || whiteControl[newCoord.index]) {
            return this
        }
        return Chessboard(this, oldPiece, oldPiece.getNewPiece(newCoord))
    }

    fun movePiece(oldCoord : Coordinates, newCoord : Coordinates) : Chessboard {
        if (!newCoord.valid()) throw Exception("Invalid coordinates")
        var oldPiece : Piece = King(true, 0, 0)
        var b : Boolean = false

        if (blackKing.coordinates.equals(oldCoord)) {
            oldPiece = blackKing
            b = true
        }
        for (p in pieces) {
            if (p.coordinates.equals(oldCoord)) {
                oldPiece = p
                b = true
                break
            }
        }
        if (!b || !oldPiece.canYouMove(newCoord.file - oldCoord.file, newCoord.row - oldCoord.row) || whiteControl[newCoord.index]) {
            return this
        }
        var newPiece = oldPiece.getNewPiece(newCoord)
        return Chessboard(this, oldPiece, oldPiece.getNewPiece(newCoord))
    }

    override fun toString(): String {

        var s : String = ""

        for (i in Coordinates.count - 1 downTo 0) {
            for (j in 0..<Coordinates.count) {
                if (board[i * Coordinates.count + j] == '.') {
                    s += board[i * Coordinates.count + j] + "   "
                }
                else {
                    s += board[i * Coordinates.count + j] + "  "
                }

            }
            s += "\n"
        }
        return s
    }


    fun generateMoves() : MutableList<Chessboard> {

        var list = mutableListOf<Chessboard>()

        val tempPieces = pieces.toMutableList()
        if (whiteOnTurn) {
            for (p in tempPieces) {
                if (p is King) {
                    list.addAll(generatePieceMoves(p))
                    tempPieces.remove(p)
                    break
                }
            }
            for (p in tempPieces) {
                list.addAll(generatePieceMoves(p))
            }

        }
        else list.addAll(generatePieceMoves(blackKing))

        return list
    }

    fun generateWhiteControl() {

        for (p in pieces) {
            p.getMovementList().forEach() {
                var file = p.coordinates.file + it.first
                var row = p.coordinates.row + it.second

                if (p.movementIsRepeatable()) {
                    while (file >= 0 && file < Coordinates.count && row < Coordinates.count && row >= 0) {
                        whiteControl[row * Coordinates.count + file] = true
                        if (board[row * Coordinates.count + file] != emptySquare && !blackKing.coordinates.equals(
                                Coordinates(file, row)
                            )) break

                        file += it.first
                        row += it.second
                    }
                }
                else if (file < Coordinates.count && row < Coordinates.count && file >= 0 && row >= 0) {
                    whiteControl[row * Coordinates.count + file] = true
                }
            }
        }
    }

    fun generatePieceMoves(p : Piece) : MutableList<Chessboard> {

        var list = mutableListOf<Chessboard>()
        var kingList = mutableListOf<Chessboard>()

        for (it in p.getMovementList()) {
            var file = p.coordinates.file + it.first
            var row = p.coordinates.row + it.second

            if (p.movementIsRepeatable()) {
                while (file >= 0 && file < Coordinates.count && row < Coordinates.count && row >= 0) {
                    if (board[row * Coordinates.count + file] != emptySquare && p.isSameColor(board[row * Coordinates.count + file])) break
                    list.add(Chessboard(this, p, p.getNewPiece(Coordinates(file, row))))

                    file += it.first
                    row += it.second
                }
            }
            else if (file < Coordinates.count && row < Coordinates.count && file >= 0 && row >= 0 && (board[row * Coordinates.count + file] == emptySquare || !p.isSameColor(board[row * Coordinates.count + file]))) {
                if (p is King) {
                    if (p.white && Coordinates(file, row).getDistance(blackKing.coordinates) > 1) kingList.add(
                        Chessboard(this, p, p.getNewPiece(Coordinates(file, row)))
                    )
                    else if (!p.white && !whiteControl[row * Coordinates.count + file]) kingList.add(
                        Chessboard(this, p, p.getNewPiece(Coordinates(file, row)))
                    )
                }
                else {
                    list.add(Chessboard(this, p, p.getNewPiece(Coordinates(file, row))))
                }
            }
        }

        if (p is King) {
            if (p.white) {
                while(kingList.size > 0) {
                    var m = -maximum
                    var ch : Chessboard = kingList[0]
                    for(it in kingList) {
                        val e = Computer.evaluatePosition(it)
                        if (e >= m) {
                            ch = it
                            m = e
                        }
                    }
                    list.add(ch)
                    kingList.remove(ch)
                }
            }
            else {
                while(kingList.size > 0) {
                    var m = maximum
                    var ch : Chessboard = kingList[0]
                    for(it in kingList) {
                        val e = Computer.evaluatePosition(it)
                        if (e <= m) {
                            ch = it
                            m = e
                        }
                    }
                    list.add(ch)
                    kingList.remove(ch)
                }
            }
        }

        return list
    }


}

