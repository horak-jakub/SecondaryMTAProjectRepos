package com.example.chess_endgame.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chess_endgame.score.ScoreDao

@Suppress("UNCHECKED_CAST")
class GameFactory (
    private val jsonString : String,
    private val scoreDao: ScoreDao
): ViewModelProvider.Factory {

    private lateinit var game : Game

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Game::class.java)) {
            game = Game(jsonString, scoreDao)
            return game as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}