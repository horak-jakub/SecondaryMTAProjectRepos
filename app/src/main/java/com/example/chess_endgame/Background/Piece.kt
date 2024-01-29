package com.example.chess_endgame.Background

import com.example.chess_endgame.R
import kotlin.math.abs

abstract class Piece {

    var white : Boolean
    var coordinates : Coordinates

    constructor(white : Boolean, file : Int, row : Int) {
        this.white = white
        this.coordinates = Coordinates(file, row)
    }

    constructor(white : Boolean, coordinates : Coordinates) {
        this.white = white
        this.coordinates = coordinates
    }

    abstract fun getSymbol() : Char
    abstract fun getMovementList() : List<Pair<Int, Int>>
    abstract fun movementIsRepeatable() : Boolean
    abstract fun getNewPiece(coord : Coordinates) : Piece
    abstract fun getRepresentaniton() : Int


    fun canYouMove(file : Int, row : Int) : Boolean {
        var list = getMovementList()
        for(m in list) {
            if (!movementIsRepeatable()) {
                if (m.first == file && m.second == row) return true
            }
            else {
                if ((abs(file) * m.first == file && abs(file) * m.second == row) || (abs(row) * m.first == file && abs(row) * m.second == row)) return true
            }
        }
        return false
    }

    fun isSameColor(other : Char) : Boolean {
        return other.isLowerCase() == getSymbol().isLowerCase()
    }
}

class King : Piece {

    var moved : Boolean = false


    companion object {
        var movementList = listOf( Pair(1, 1), Pair(1, 0), Pair(1, -1), Pair(0, 1), Pair(0, -1), Pair(-1, 1), Pair(-1, 0), Pair(-1, -1) )
        var representation = R.drawable.white_king
    }

    constructor(white: Boolean, file: Int, row: Int) : super(white, file, row) {
    }

    constructor(white: Boolean, coord : Coordinates) : super(white, coord) {
    }

    override fun getSymbol(): Char {
        if (white) {
            return 'K'
        }
        return 'k'
    }

    override fun getMovementList(): List<Pair<Int, Int>> {
        return movementList
    }

    override fun movementIsRepeatable(): Boolean {
        return false
    }

    override fun getNewPiece(coord: Coordinates) : Piece {
        return King(white, coord)
    }

    override fun getRepresentaniton(): Int {
        return representation
    }
}

class Queen : Piece {

    companion object {
        var movementList = listOf( Pair(1, 1), Pair(1, 0), Pair(1, -1), Pair(0, 1), Pair(0, -1), Pair(-1, 1), Pair(-1, 0), Pair(-1, -1) )
        var representation = R.drawable.white_queen
    }

    constructor(white: Boolean, file: Int, row: Int) : super(white, file, row) {
    }

    constructor(white: Boolean, coord : Coordinates) : super(white, coord) {
    }

    override fun getSymbol(): Char {
        if (white) {
            return 'Q'
        }
        return 'q'
    }

    override fun getMovementList(): List<Pair<Int, Int>> {
        return movementList
    }

    override fun movementIsRepeatable(): Boolean {
        return true
    }

    override fun getNewPiece(coord: Coordinates) : Piece {
        return Queen(white, coord)
    }

    override fun getRepresentaniton(): Int {
        return representation
    }
}

class Rook : Piece {

    companion object {
        var movementList = listOf( Pair(1, 0), Pair(0, 1), Pair(0, -1), Pair(-1, 0) )
        var representation = R.drawable.white_rook
    }

    constructor(white: Boolean, file: Int, row: Int) : super(white, file, row) {
    }

    constructor(white: Boolean, coord : Coordinates) : super(white, coord) {
    }

    override fun getSymbol(): Char {
        if (white) {
            return 'R'
        }
        return 'r'
    }

    override fun getMovementList(): List<Pair<Int, Int>> {
        return movementList
    }

    override fun movementIsRepeatable(): Boolean {
        return true
    }

    override fun getNewPiece(coord: Coordinates) : Piece {
        return Rook(white, coord)
    }

    override fun getRepresentaniton(): Int {
        return representation
    }
}

class Bishop : Piece {

    companion object {
        var movementList = listOf( Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1) )
        var representation = R.drawable.white_bishop
    }

    constructor(white: Boolean, coord : Coordinates) : super(white, coord) {
    }

    constructor(white: Boolean, file: Int, row: Int) : super(white, file, row) {
    }

    override fun getSymbol(): Char {
        if (white) {
            return 'B'
        }
        return 'b'
    }

    override fun getMovementList(): List<Pair<Int, Int>> {
        return movementList
    }

    override fun movementIsRepeatable(): Boolean {
        return true
    }
    override fun getNewPiece(coord: Coordinates) : Piece {
        return Bishop(white, coord)
    }

    override fun getRepresentaniton(): Int {
        return representation
    }
}

class Knight : Piece {

    companion object {
        var movementList = listOf( Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1), Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2) )
        var representation = R.drawable.white_knight
    }

    constructor(white: Boolean, file: Int, row: Int) : super(white, file, row) {
    }

    constructor(white: Boolean, coord : Coordinates) : super(white, coord) {
    }

    override fun getSymbol(): Char {
        if (white) {
            return 'N'
        }
        return 'n'
    }

    override fun getMovementList(): List<Pair<Int, Int>> {
        return movementList
    }

    override fun movementIsRepeatable(): Boolean {
        return false
    }

    override fun getNewPiece(coord: Coordinates) : Piece {
        return Knight(white, coord)
    }

    override fun getRepresentaniton(): Int {
        return representation
    }
}

