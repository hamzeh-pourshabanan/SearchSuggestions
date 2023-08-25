package com.hamzeh.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
class SearchActivity: AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 500L
    private var lastUpdateTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText = findViewById<EditText>(R.id.et_search_destination)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastUpdateTime > delayMillis) {
                    lastUpdateTime = currentTime
                    requestSearchSuggestions(s.toString())
                } else {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        requestSearchSuggestions(s.toString())
                    }, delayMillis)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun requestSearchSuggestions(query: String) {
        // Make network request
    }
}