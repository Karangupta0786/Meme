package com.example.memeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

//    https://meme-api.com/gimme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
        findViewById<Button>(R.id.btn_next).setOnClickListener {
            findViewById<ProgressBar>(R.id.progress_circular).isVisible = true
            getData()
        }

    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://meme-api.com/")
            .build()
            .create(ApiInterface::class.java)

        val retrofit = retrofitBuilder.getMeme()
        retrofit.enqueue(object : Callback<MemeData?> {
            override fun onResponse(call: Call<MemeData?>, response: Response<MemeData?>) {
                if (response.isSuccessful){
                    findViewById<ProgressBar>(R.id.progress_circular).isVisible = false
                    Toast.makeText(applicationContext, "Mission Succeed", Toast.LENGTH_SHORT).show()
                    findViewById<TextView>(R.id.text).text = response.body()?.title.toString()
                    Glide.with(this@MainActivity).load(response.body()?.url.toString()).into(findViewById(R.id.image))
                }
                else{
                    Toast.makeText(applicationContext, "Mission Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MemeData?>, t: Throwable) {
                Toast.makeText(applicationContext, "Mission Failed", Toast.LENGTH_SHORT).show()
            }
        })

//        RerofitInstance.apiInterface.getMeme().enqueue(object : Callback<MemeData?> {
//            override fun onResponse(call: Call<MemeData?>, response: Response<MemeData?>) {
//                findViewById<ProgressBar>(R.id.progress_circular).isVisible = false
//                findViewById<TextView>(R.id.text).text = response.body()?.title
//            }
//
//            override fun onFailure(call: Call<MemeData?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })

    }
}