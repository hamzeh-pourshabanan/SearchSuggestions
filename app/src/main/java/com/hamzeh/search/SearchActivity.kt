package com.hamzeh.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

class SearchActivity: AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val searchQueryState = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText = findViewById<EditText>(R.id.et_search_destination)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchQueryState.value = s.toString()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        coroutineScope.launch {
            searchQueryState
                .debounce(500) // Debounce for 500 milliseconds
                .distinctUntilChanged() // Only emit distinct values
                .collect { query ->
                    requestSearchSuggestions(query)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun requestSearchSuggestions(query: String) {
        // Make network request
    }
}