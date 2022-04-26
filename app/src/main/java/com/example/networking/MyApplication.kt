package com.example.networking

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MyApplication : Application() {

    companion object {
        val TAG: String = MyApplication::class.java.simpleName.toString()
        var INSTANCE: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }
}
