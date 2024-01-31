package com.example.chess_endgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chess_endgame.Score.ScoreAdapter
import com.example.chess_endgame.Score.ScoreDatabase
import com.example.chess_endgame.databinding.ActivityScoreBinding
import kotlinx.coroutines.launch


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
            scoreDao.getAllScores().collect {
                scoreAdapter.submitList(it)
            }
        }

        binding.newGameButton.setOnClickListener {
            finish()
            startActivity(Intent(this, BoardSettingActivity::class.java))
        }
    }
}


