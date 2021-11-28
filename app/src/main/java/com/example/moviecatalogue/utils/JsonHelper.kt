package com.example.moviecatalogue.utils

import android.content.Context
import com.example.moviecatalogue.data.source.remote.response.GenresItem
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private val listMovie : List<GenresItem> = getGenres("movie")
    private val listTv : List<GenresItem> = getGenres("tv")

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    private fun getGenres(type: String) : List<GenresItem>{
        val list = ArrayList<GenresItem>()

        try {
            val filename = if (type == "movie") "MovieGenre.json" else "TvGenre.json"
            val responseObject = JSONObject(parsingFileToString(filename).toString())
            val listArray = responseObject.getJSONArray("genres")
            for (i in 0 until listArray.length()) {
                val genre = listArray.getJSONObject(i)

                val id = genre.getInt("id")
                val name = genre.getString("name")

                val genreResponse = GenresItem(id = id, name = name)
                list.add(genreResponse)
            }
        } catch (e : JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun getTvGenreName(id: Int) : String{
        var result = ""
        for (genre in listTv) {
            if (genre.id == id){
                result = genre.name as String
            }
        }
        return result
    }

    fun getMovieGenreName(id: Int) : String{
        var result = ""
        for (genre in listMovie) {
            if (genre.id == id){
                result = genre.name as String
            }
        }
        return result
    }

}