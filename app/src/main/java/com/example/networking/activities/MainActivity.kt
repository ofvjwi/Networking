package com.example.networking.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.networking.R
import com.example.networking.adapters.RecyclerAdapter
import com.example.networking.models.Poster
import com.example.networking.models.PosterResp
import com.example.networking.network.retrofit.RetrofitHttp
import com.example.networking.network.volley.VolleyHandler
import com.example.networking.network.volley.VolleyHttp
import com.example.networking.utils.Logger
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var posters: ArrayList<Poster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        context = this
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        floatingActionButton = findViewById(R.id.floating)

        apiPosterList()
    }

    fun refreshAdapter(posters: ArrayList<Poster>) {
        val adapter = RecyclerAdapter(this, posters)
        recyclerView.adapter = adapter
    }

    fun dialogPoster(poster: Poster) {
        AlertDialog.Builder(context)
            .setTitle("Delete poster")
            .setMessage("Are you sure you want to delete this poster?")
            .setPositiveButton("Yes") { _, _ -> apiPosterDelete(poster) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun apiPosterDelete(poster: Poster) {
        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.VISIBLE
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id, object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Log.d("TAG", "onSuccess: $response")
                apiPosterList()
            }

            override fun onError(error: String?) {
                Log.e("TAG", "onError: $error")
            }
        })
    }

    private fun apiPosterList() {
        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.VISIBLE
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                findViewById<ProgressBar>(R.id.progress_circular).visibility = View.INVISIBLE
                val posterArray = Gson().fromJson(response, Array<Poster>::class.java)
                posters = ArrayList()
                posters.addAll(posterArray)
                refreshAdapter(posters)
                Log.d("TAG", "onSuccess: ${posters.size}")
            }

            override fun onError(error: String?) {
                findViewById<ProgressBar>(R.id.progress_circular).visibility = View.INVISIBLE
                Log.e("TAG", "onError: $error")
            }
        })
    }

    private fun workWithVolley() {

        val post = Poster(1, 1, "PDP", "Academy")

        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onError(error: String?) {
                Logger.e("VolleyHttp error get: ", error!!)
            }

            override fun onSuccess(response: String?) {
                Logger.d("VolleyHttp success get: ", response!!)
            }
        })

        VolleyHttp.post(
            VolleyHttp.API_CREATE_POST,
            VolleyHttp.paramsCreate(post),
            object : VolleyHandler {
                override fun onError(error: String?) {
                    Logger.e("VolleyHttp error post: ", error!!)
                }

                override fun onSuccess(response: String?) {
                    Logger.d("VolleyHttp success post: ", response!!)
                }
            })

        VolleyHttp.del(VolleyHttp.API_DELETE_POST + post.id, object : VolleyHandler {
            override fun onError(error: String?) {
                Logger.e("VolleyHttp error del: ", error!!)
            }

            override fun onSuccess(response: String?) {
                Logger.d("VolleyHttp success del: ", response!!)
            }
        })
    }

    private fun workWithRetrofit() {

        val post = Poster(1, 1, "PDP", "Academy")

        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<PosterResp>> {
            override fun onFailure(call: Call<ArrayList<PosterResp>>, t: Throwable) {
                Logger.e("Retrofit error: ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<PosterResp>>,
                response: Response<ArrayList<PosterResp>>
            ) {
                Logger.e("Retrofit success: ", response.body().toString())
            }
        })

        RetrofitHttp.posterService.createPost(post).enqueue(object : Callback<PosterResp> {
            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Logger.e("Retrofit error: ", t.message.toString())
            }

            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Logger.e("Retrofit success: ", response.body().toString())
            }
        })

        RetrofitHttp.posterService.updatePost(post.id, post).enqueue(object : Callback<PosterResp> {
            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Logger.e("Retrofit error: ", t.message.toString())
            }

            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Logger.e("Retrofit success: ", response.body().toString())
            }
        })
    }
}

