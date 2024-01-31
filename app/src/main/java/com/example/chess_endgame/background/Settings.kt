package com.example.chess_endgame.background

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class Settings (var settingsJson : String = "", var gameIdentifier : String) {

    var pieces : JSONArray?

    init {
        pieces = parseJsonArray(settingsJson, gameIdentifier)
    }

    fun parseJsonObject(settingsJson:String, item:String) : JSONObject? {
        return try {
            val jObj = JSONObject(settingsJson)
            jObj.getJSONObject(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser object", "unexpected JSON exception", ex)
            null
        }
    }

    private fun parseJsonArray(settingsJson:String, item:String) : JSONArray? {
        return try {
            val jObj = JSONObject(settingsJson)
            jObj.getJSONArray(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser array", "unexpected JSON exception", ex)
            null
        }
    }


}