package com.codepath.randompet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var petList : MutableList<String>
    private lateinit var authorList : MutableList<String>
    private lateinit var rvPets : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvPets = findViewById(R.id.pet_list)
        petList = mutableListOf()
        authorList = mutableListOf()

        fetchDogImages()
    }

    private fun fetchDogImages() {
        val client = AsyncHttpClient()
        val randoNum = (1..10).random()
        client["https://picsum.photos/v2/list?page=${randoNum}&limit=20", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val petImageArray = json.jsonArray
                petList = mutableListOf()
                authorList = mutableListOf()
                for (i in 0 until petImageArray.length()) {
                    petList.add(petImageArray.getJSONObject(i).getString("download_url"))
                    authorList.add(petImageArray.getJSONObject(i).getString("author"))
                }
                val adapter = PetAdapter(petList, authorList)
                rvPets = findViewById(R.id.pet_list)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)

                Log.d("Dog", "response successful: $json")

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)            }
        }]
    }
}