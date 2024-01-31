package com.example.chess_endgame.Score

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {    @Insert(onConflict = ABORT)
    fun addScore (score: Score) : Long

    @Insert
    fun addScores (vararg scores: Score)

    @Delete
    fun deleteScore (score: Score) : Int

    @Query("SELECT * FROM score ORDER BY score")
    fun getAllScores(): Flow<List<Score>>

    @Query ("SELECT * FROM score where name = :name")
    fun getPlayerScore (name : String) : Flow<List<Score>>

    @Query ("DELETE FROM score where name = :name")
    fun deletePlayerScore (name : String)
}
