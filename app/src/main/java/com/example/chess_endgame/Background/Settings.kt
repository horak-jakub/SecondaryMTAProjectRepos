package com.example.chess_endgame.Background

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
        try {
            val jObj = JSONObject(settingsJson)
            return jObj.getJSONObject(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser object", "unexpected JSON exception", ex)
            return null
        }
    }

    fun parseJsonArray(settingsJson:String, item:String) : JSONArray? {
        try {
            val jObj = JSONObject(settingsJson)
            return jObj.getJSONArray(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser array", "unexpected JSON exception", ex)
            return null
        }
    }


}