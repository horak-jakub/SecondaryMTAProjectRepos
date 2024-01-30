package com.example.chess_endgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chess_endgame.Score.Score
import com.example.chess_endgame.Score.ScoreAdapter
import com.example.chess_endgame.databinding.ActivityScoreBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class ScoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val scores = parseJsonArray(readscoreJson(), "score")
        val list = mutableListOf<Score>()
        scores?.let {
            for (i in 0 until scores.length()) {
                val s = scores.getJSONObject(i)
                list.add(Score(s.getInt("id"), s.getString("game"), s.getInt("moves")))
            }
        }

        binding.scoreRecyclerView.layoutManager = LinearLayoutManager(this)
        val scoreAdapter = ScoreAdapter()
        binding.scoreRecyclerView.adapter = scoreAdapter
        scoreAdapter.submitList(list)



        binding.newGameButton.setOnClickListener() {
            startActivity(Intent(this, BoardSettingActivity::class.java))
        }
    }


    fun readscoreJson () : String {
        var string: String? = ""
        val stringBuilder = StringBuilder()
        val inputStream: InputStream = resources.openRawResource(applicationContext.resources.getIdentifier("score", "raw", applicationContext.packageName))
        //val inputStream: InputStream = resources.openRawResource(R.raw.settings)
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            try {
                if (reader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")

        }
        inputStream.close()
        return stringBuilder.toString()
    }

    fun parseJsonArray(scoreJson:String, item:String) : JSONArray? {
        try {
            val jObj = JSONObject(scoreJson)
            return jObj.getJSONArray(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser array", "unexpected JSON exception", ex)
            return null
        }
    }
}


