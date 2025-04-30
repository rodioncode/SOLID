package com.example.myapplication.solid

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class MainActivity : AppCompatActivity() {
    val quotes = ArrayList<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        val textQuote = findViewById<TextView>(R.id.text_quote)
        val editQuote = findViewById<EditText>(R.id.edit_quote)
        val buttonAdd = findViewById<Button>(R.id.button_add)

        if (quotes.size > 0) {
            textQuote.text = quotes[0]
        }

        buttonAdd.setOnClickListener {
            val newQuote = editQuote.text.toString()
            if (newQuote.isNotEmpty()) {
                quotes.add(newQuote)
                // Показ новой цитаты
                textQuote.text = newQuote
                editQuote.setText("")
            }
        }
    }

    fun removeQuote(quote: String) {
        quotes.remove(quote)
    }

    fun findQuote(query: String): String? {
        for (q in quotes) {
            if (q.contains(query)) return q
        }
        return null
    }
}