package com.example.chess_endgame.Background

import kotlin.math.abs

class Coordinates {

    companion object {
        const val count = 8
    }

    val file : Int
    val row : Int
    var index : Int

    constructor(file : Int, row : Int) {
        if (file >= count || row >= count) throw Exception("Board index out of bound")
        this.file = file
        this.row = row
        index = count * row + file
    }

    constructor(f : Char, r : Char) {
        val file = f - 'a'
        val row = r - '1'
        if (file >= count || row >= count) throw Exception("Board index out of bound")
        this.file = file
        this.row = row
        index = count * row + file
    }


    fun getMovedCoordinates(fileShift : Int, rowShift : Int) : Coordinates {
        return Coordinates(file + fileShift, row + rowShift)
    }

    fun valid() : Boolean {
        return (file in 0..<count) && (row in 0..<count)
    }

    fun equals(other : Coordinates) : Boolean {
        return other.file == file && other.row == row
    }

    fun getDistance(other : Coordinates) : Int {
        return maxOf(abs(file - other.file), abs(row - other.row))
    }
}