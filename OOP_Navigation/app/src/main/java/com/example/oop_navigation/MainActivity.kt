package com.example.oop_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontWeight
import com.example.oop_navigation.ui.theme.OOP_NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingScreen()
        }
    }
}

@Composable
fun OnboardingScreen() {
    var currentPage by remember { mutableStateOf(0) }

    val pages = listOf(
        Page(
            title = "UTH SmartTasks",
            imageRes = R.drawable.image1,
            description = ""
        ),
        Page(
            title = "Easy Time Management",
            imageRes = R.drawable.image2,
            description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first "
        ),
        Page(
            title = "Increase Work Effectiveness",
            imageRes = R.drawable.image3,
            description = "Time management and the determination of more important tasks will give your job statistics better and always improve"
        ),
        Page(
            title = "Reminder Notification",
            imageRes = R.drawable.image4,
            description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row chỉ chứa Skip ở bên phải
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Skip",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { currentPage = 0 }
                    .align(Alignment.CenterVertically)
            )
        }

        // Phần nội dung chính
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = pages[currentPage].imageRes),
                    contentDescription = pages[currentPage].title,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = pages[currentPage].title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (pages[currentPage].title == "UTH SmartTasks") Color(0xFF0D47A1) else MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pages[currentPage].description,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }

        // Footer: Previous + 3 dots + Next
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentPage > 0) {
                IconButton(onClick = { currentPage-- }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous"
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp)) // giữ khoảng trắng
            }

            // 3 dots chỉ áp dụng cho trang 2–4
            Row {
                if (currentPage in 1..3) {
                    (1..3).forEach { index ->
                        Box(
                            modifier = Modifier
                                .size(4.dp) // đường kính 4dp = tâm 2dp
                                .padding(end = 4.dp)
                                .background(
                                    color = if (currentPage == index)
                                        Color(0xFF0D47A1) // màu xanh đậm
                                    else
                                        Color.Gray.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            // Nút Next / Get Started (màu nền xanh đậm)
            Button(
                onClick = {
                    if (currentPage < pages.lastIndex) {
                        currentPage++
                    } else {
                        currentPage = 0
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
            ) {
                Text(
                    if (currentPage == pages.lastIndex) "Get Started" else "Next",
                    color = Color.White
                )
            }
        }
    }
}

data class Page(val title: String, val imageRes: Int, val description: String)