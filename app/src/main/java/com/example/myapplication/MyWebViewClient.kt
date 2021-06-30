package com.example.myapplication

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.webkit.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
class MyWebViewClient(private val activity:Activity, private val afterResponse: ResponseInterface):WebViewClient() {
    private val client=OkHttpClient()
    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        val mRequest=Request.Builder().url(request?.url.toString()).build()
        client.newCall(mRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { jsonString ->
                            if(isJson(jsonString)) {
                                // show api -json-
                                Log.i("SSMM", request?.url.toString())
                            }
                }
            }
        })
        return super.shouldInterceptRequest(view, request)
    }
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        activity.runOnUiThread {
            afterResponse.changeVisibility(false)
        }
        super.onReceivedError(view, request, error)
    }
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        activity.runOnUiThread{
            afterResponse.changeVisibility(true)
        }
        super.onPageStarted(view, url, favicon)
    }
    private fun isJson(jsonString: String): Boolean {
        try {
            JSONObject(jsonString)
        } catch (ex: JSONException) {
            try {
                JSONArray(jsonString)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }
}