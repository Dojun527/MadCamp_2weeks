package com.example.tabapplication.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.R

const val RESULT_NUM = 1

class PhoneAddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_phonebook)

        val SaveButton: Button = findViewById(R.id.btnSave)
        SaveButton.setOnClickListener{
            val addName: EditText = findViewById(R.id.txtName)
            val addPhonenumber: EditText = findViewById(R.id.txtPhoneNumber)

            val Name: String = addName.text.toString()
            val Phonenumber: String = addPhonenumber.text.toString()

            val intent: Intent = intent
            intent.putExtra("Name",Name )
            intent.putExtra("Phonenumber", Phonenumber)
            setResult(RESULT_NUM, intent)
            finish()
        }

    }
}