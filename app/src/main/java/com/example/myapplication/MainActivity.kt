package com.example

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.myapplication.MyWebViewClient
import com.example.myapplication.ResponseInterface
import com.example.myapplication.databinding.ActivityMainBinding
import okhttp3.*

class MainActivity : AppCompatActivity() ,ResponseInterface{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
        callback()
    }

    private fun setup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.apply {
                web.webViewClient =MyWebViewClient(this@MainActivity,this@MainActivity)
                web.settings.javaScriptEnabled = true
            }
        }
    }

    private fun callback() {
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
            {
                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.web.loadUrl(query)
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun changeVisibility(success:Boolean) {
        binding.apply {
            if(success){
                searchFailLottie.visibility=View.GONE
                searchLottie.visibility= View.GONE
                web.visibility=View.VISIBLE
            }else
            {
                web.visibility=View.INVISIBLE
                searchLottie.visibility= View.GONE
                searchFailLottie.visibility= View.VISIBLE
            }
        }
    }
}