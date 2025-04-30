package com.example.myapplication.dry

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class QuotesActivity : AppCompatActivity() {

    val quotesList = ArrayList<String>()
    val quotesSet = HashSet<String>()

    val apiService: FakeApiService? = null

    val quoteLikes = mutableMapOf<String, Int>()

    var currentIndex = 0

    var userLoggedIn = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotes)

        val textQuote = findViewById<TextView>(R.id.text_quote)
        val editQuote = findViewById<EditText>(R.id.edit_quote)
        val buttonAdd = findViewById<Button>(R.id.button_add)
        val buttonRandom = findViewById<Button>(R.id.button_random)
        val buttonShowAll = findViewById<Button>(R.id.button_show_all)
        val buttonSync = findViewById<Button>(R.id.button_sync)
        val textStatus = findViewById<TextView>(R.id.text_status)

        if (quotesList.size > 0) {
            textQuote.text = quotesList[0]
        } else {
            textQuote.text = "Нет цитат"
        }
        if (quotesSet.size > 0) {
            textStatus.text = "Цитаты загружены"
        } else {
            textStatus.text = "Добавьте цитату"
        }

        buttonAdd.setOnClickListener {
            val newQuote = editQuote.text.toString()
            if (newQuote.isEmpty()) {
                textStatus.text = "Введите цитату!"
            } else if (quotesList.contains(newQuote)) {
                textStatus.text = "Такая цитата уже есть!"
            } else {
                quotesList.add(newQuote)
                quotesSet.add(newQuote)
                quoteLikes[newQuote] = 0
                textQuote.text = "\"" + newQuote + "\""
                textStatus.text = "Цитата добавлена: $newQuote"
                editQuote.setText("")
                if (apiService != null) {
                    apiService.syncQuote(newQuote)
                }
            }
        }

        buttonRandom.setOnClickListener {
            if (quotesList.isEmpty()) {
                textQuote.text = "Нет цитат"
            } else {
                val idx = (0 until quotesList.size).random()
                currentIndex = idx
                textQuote.text = quotesList[idx]
                textStatus.text = "Случайная цитата: ${quotesList[idx]}"
            }
        }

        buttonShowAll.setOnClickListener {
            if (quotesList.isEmpty()) {
                textQuote.text = "Нет цитат"
                textStatus.text = "Список пуст"
            } else {
                var all = ""
                for (q in quotesList) {
                    all += q + "\n"
                }
                textQuote.text = all
                textStatus.text = "Показаны все цитаты"
            }
        }

        buttonSync.setOnClickListener {
            textStatus.text = "Синхронизация с сервером недоступна"
        }

        textQuote.setOnLongClickListener {
            if (textQuote.text.length > 30) {
                textStatus.text = "Очень длинная цитата!"
            } else {
                textStatus.text = "Цитата обычной длины"
            }
            true
        }

        findViewById<Button>(R.id.button_switch_user)?.setOnClickListener {
            userLoggedIn = !userLoggedIn
            textStatus.text = if (userLoggedIn) "Пользователь вошёл" else "Пользователь вышел"
        }

        findViewById<Button>(R.id.button_clear).setOnClickListener {
            editQuote.setText("")
            textStatus.text = "Поле очищено"
        }
    }

    fun showFirstQuote(textQuote: TextView, textStatus: TextView) {
        if (quotesList.isEmpty()) {
            textQuote.text = "Нет цитат"
            textStatus.text = "Список пуст"
        } else {
            textQuote.text = quotesList[0]
            textStatus.text = "Показана первая цитата"
        }
    }

    fun showLastQuote(textQuote: TextView, textStatus: TextView) {
        if (quotesList.isEmpty()) {
            textQuote.text = "Нет цитат"
            textStatus.text = "Список пуст"
        } else {
            textQuote.text = quotesList[quotesList.size - 1]
            textStatus.text = "Показана последняя цитата"
        }
    }

    class FakeApiService {
        fun syncQuote(quote: String) {
        }
    }
}
