package com.example.chess_endgame.Score

import androidx.room.TypeConverter
import java.util.Date

class ScoreConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}