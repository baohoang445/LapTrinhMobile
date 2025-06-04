package com.example.quanly_thuvien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {

    data class Student(val name: String, val borrowedBooks: MutableList<String>)

    private val allBooks = listOf("Sách 01", "Sách 02")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var studentName by remember { mutableStateOf("Nguyen Van A") }
            var currentStudent by remember {
                mutableStateOf(
                    Student("Nguyen Van A", mutableListOf("Sách 01", "Sách 02"))
                )
            }

            fun loadStudent(name: String): Student {
                return when (name.trim()) {
                    "Nguyen Van A" -> Student("Nguyen Van A", mutableListOf("Sách 01", "Sách 02"))
                    "Nguyen Thi B" -> Student("Nguyen Thi B", mutableListOf("Sách 01"))
                    "Nguyen Van C" -> Student("Nguyen Van C", mutableListOf())
                    else -> Student(name.trim(), mutableListOf())
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hệ thống\nQuản lý Thư viện",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Sinh viên") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { currentStudent = loadStudent(studentName) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.End)
                ) {
                    Text("Thay đổi")
                }

                Text("Danh sách sách", fontWeight = FontWeight.Medium, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))

                if (currentStudent.borrowedBooks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Bạn chưa mượn quyền sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        allBooks.forEach { book ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Checkbox(
                                    checked = currentStudent.borrowedBooks.contains(book),
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            if (!currentStudent.borrowedBooks.contains(book)) {
                                                currentStudent.borrowedBooks.add(book)
                                            }
                                        } else {
                                            currentStudent.borrowedBooks.remove(book)
                                        }
                                    }
                                )
                                Text(book)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (currentStudent.borrowedBooks.size < allBooks.size) {
                            val next = allBooks.firstOrNull { it !in currentStudent.borrowedBooks }
                            if (next != null) currentStudent.borrowedBooks.add(next)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Thêm")
                }

                Spacer(modifier = Modifier.weight(1f))

                // Thanh điều hướng
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("Quản lý", color = Color.Blue, fontWeight = FontWeight.Bold)
                    Text("DS Sách", color = Color.Gray)
                    Text("Sinh viên", color = Color.Gray)
                }
            }
        }
    }
}
