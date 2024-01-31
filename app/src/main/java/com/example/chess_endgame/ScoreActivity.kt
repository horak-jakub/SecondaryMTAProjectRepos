package com.example.chess_endgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chess.console.Game
import com.example.chess_endgame.Score.Score
import com.example.chess_endgame.Score.ScoreAdapter
import com.example.chess_endgame.Score.ScoreDatabase
import com.example.chess_endgame.databinding.ActivityScoreBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.Date


class ScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScoreBinding
    private val scoreDao by lazy {
        ScoreDatabase.getDatabase(this).scoreDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.scoreRecyclerView.layoutManager = LinearLayoutManager(this)
        val scoreAdapter = ScoreAdapter()
        binding.scoreRecyclerView.adapter = scoreAdapter
        lifecycle.coroutineScope.launch {
            scoreDao.getAllScores().collect() {
                scoreAdapter.submitList(it)
            }
        }

        binding.newGameButton.setOnClickListener() {
            startActivity(Intent(this, BoardSettingActivity::class.java))
        }
    }
}


