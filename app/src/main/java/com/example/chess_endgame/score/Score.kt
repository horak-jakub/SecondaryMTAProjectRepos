package com.example.chess_endgame.score

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "score",
    indices = arrayOf(
        Index(value = arrayOf ("score"), unique = false),
        Index(value = arrayOf("name", "score")))
)
data class Score (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name="dateAdded") val date : Date,
    val name : String,
    val score : Int
) {

//@Ignore val ignore : String
}