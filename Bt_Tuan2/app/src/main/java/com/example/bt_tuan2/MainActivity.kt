package com.example.bt_tuan2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        btnCheck.setOnClickListener {
            val name = edtName.text.toString().trim()
            val ageText = edtAge.text.toString()

            if (name.isEmpty() || ageText.isEmpty()) {
                txtResult.text = "Vui lòng nhập đầy đủ họ tên và tuổi."
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            if (age == null) {
                txtResult.text = "Tuổi không hợp lệ."
                return@setOnClickListener
            }

            val category = when {
                age <= 2 -> "Em bé"
                age in 3..6 -> "Trẻ em"
                age in 7..65 -> "Người lớn"
                else -> "Người già"
            }

            txtResult.text = "$name ($age tuổi) là: $category"
        }
    }
}