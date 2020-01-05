package com.example.tabapplication.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.tabapplication.R
import kotlinx.android.synthetic.main.word_quiz.*

const val RESULT_CODE = 1

class WordAddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addword)

        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener{
            val addVocabulary: EditText = findViewById(R.id.addVocabulary)
            val addMeaning: EditText = findViewById(R.id.addMeaning)

            val vocabulary: String = addVocabulary.text.toString()
            val meaning: String = addMeaning.text.toString()

            val intent: Intent = intent
            intent.putExtra("vocabulary", vocabulary)
            intent.putExtra("meaning", meaning)
            setResult(RESULT_CODE, intent)
            finish()
        }

    }
}