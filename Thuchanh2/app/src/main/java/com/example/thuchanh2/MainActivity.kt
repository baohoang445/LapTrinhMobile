package com.example.thuchanh2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.editTextEmail)
        val checkButton = findViewById<Button>(R.id.buttonCheck)
        val resultText = findViewById<TextView>(R.id.textResult)

        checkButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            when {
                email.isEmpty() -> {
                    resultText.text = getString(R.string.msg_empty)
                }
                !email.contains("@") -> {
                    resultText.text = getString(R.string.msg_invalid)
                }
                else -> {
                    resultText.text = getString(R.string.msg_valid)
                }
            }
        }
    }
}
