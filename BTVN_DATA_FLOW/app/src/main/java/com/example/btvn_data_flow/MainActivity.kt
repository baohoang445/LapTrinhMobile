package com.example.btvn_data_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding


import androidx.compose.ui.tooling.preview.Preview
import com.example.btvn_data_flow.ui.theme.BTVN_DATA_FLOWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTVN_DATA_FLOWTheme {
                val navController = rememberNavController()
                val dataFlow = remember { DataFlow() }

                NavHost(navController = navController, startDestination = "forget_password") {
                    composable("forget_password") {
                        ForgetPasswordScreen(navController, dataFlow)
                    }
                    composable("verify_code") {
                        VerifyCodeScreen(navController, dataFlow)
                    }
                    composable("reset_password") {
                        ResetPasswordScreen(navController, dataFlow)
                    }
                    composable("confirm") {
                        ConfirmScreen(navController, dataFlow)
                    }
                }
            }
        }
    }
}

data class DataFlow(
    var email: String = "",
    var verificationCode: String = "",
    var password: String = ""
)

// Man hinh forget password
@Composable
fun ForgetPasswordScreen(navController: NavController, dataFlow: DataFlow) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp), color = Color.Blue)
        Text("Forgot Password", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(
            "Enter your Email, we will send you a verification code",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BasicTextField(
            value = email,
            onValueChange = { email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text(text = "Your Email", color = Color.Gray)
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                dataFlow.email = email
                navController.navigate("verify_code")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Next", color = Color.White)
        }
    }
}

// Man hinh verify code
@Composable
fun VerifyCodeScreen(navController: NavController, dataFlow: DataFlow) {
    var code by remember { mutableStateOf(List(5) { "" }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { navController.navigate("forget_password") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
            ) {
                Text("<", color = Color.Blue)
            }
        }

        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("SmartTasks", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Verify Code", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(
            "We just sent you on your registered Email",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(5) { index ->
                BasicTextField(
                    value = code[index],
                    onValueChange = {
                        if (it.length <= 1) {
                            code = code.toMutableList().apply { this[index] = it }
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dataFlow.verificationCode = code.joinToString("")
                navController.navigate("reset_password")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Next", color = Color.White)
        }
    }
}

// man hinh Reset Password
@Composable
fun ResetPasswordScreen(navController: NavController, dataFlow: DataFlow) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { navController.navigate("verify_code") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
            ) {
                Text("<", color = Color.White)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("CREATE new password", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(
            "Your new password must be different from previously used password",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (password.isEmpty()) {
                    Text("Password", color = Color.Gray)
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (confirmPassword.isEmpty()) {
                    Text("Confirm Password", color = Color.Gray)
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password == confirmPassword) {
                    dataFlow.password = password
                    navController.navigate("confirm")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Next", color = Color.White)
        }
    }
}

// Confirm
@Composable
fun ConfirmScreen(navController: NavController, dataFlow: DataFlow) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { navController.navigate("reset_password") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
            ) {
                Text("<", color = Color.White)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Confirm", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(
            "We are here help you",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BasicTextField(
            value = dataFlow.email,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            dataFlow.verificationCode.forEach { char ->
                Text(char.toString(), fontSize = 20.sp, modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = "********",
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("forget_password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}
