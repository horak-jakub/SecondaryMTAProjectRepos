package com.example.chess_endgame.Main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chess.console.Game
import com.example.chess_endgame.Score.ScoreDao

class GameFactory (
    private val jsonString : String,
    private val scoreDao: ScoreDao
): ViewModelProvider.Factory {

    lateinit var game : Game

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Game::class.java)) {
            game = Game(jsonString, scoreDao)
            return game as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}