package com.example.login_flow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

data class User(val name: String, val email: String, val dateOfBirth: String)

class MainActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    if (idToken != null) {
                        authManager.firebaseAuthWithGoogle(idToken,
                            onSuccess = { user ->
                                Log.d("Auth", "Login success: ${user.email}")
                                authManager.onUserSignedIn?.invoke(user)
                            },
                            onError = {
                                Log.e("Auth", "Login error", it)
                            }
                        )
                    }
                } catch (e: ApiException) {
                    Log.e("Auth", "Google sign-in failed", e)
                }
            }

        authManager = AuthManager(this, googleSignInLauncher)

        setContent {
            UTHSmartTasksApp(authManager)
        }
    }
}

class AuthManager(
    private val context: Context,
    private val launcher: ActivityResultLauncher<Intent>
) {
    private val auth = FirebaseAuth.getInstance()
    var onUserSignedIn: ((FirebaseUser) -> Unit)? = null

    fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(context, gso)
        val signInIntent = client.signInIntent
        launcher.launch(signInIntent)
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        onSuccess: (FirebaseUser) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) onSuccess(user)
                } else {
                    onError(task.exception ?: Exception("Login failed"))
                }
            }
    }
}

@Composable
fun UTHSmartTasksApp(authManager: AuthManager) {
    var user by remember { mutableStateOf<User?>(null) }

    authManager.onUserSignedIn = { firebaseUser ->
        user = User(
            name = firebaseUser.displayName ?: "Unknown",
            email = firebaseUser.email ?: "Unknown",
            dateOfBirth = "04/04/2005"
        )
    }

    if (user == null) {
        LoginScreen(onSignIn = { authManager.signInWithGoogle() })
    } else {
        ProfileScreen(user!!, onSignOut = { user = null})
    }
}

@Composable
fun LoginScreen(onSignIn: () -> Unit) {
    val lightBlue = Color(0xFF42A5F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo trong ô vuông bo 4 góc
            Surface(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.size(100.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "UTH Logo",
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("SmartTasks", style = MaterialTheme.typography.headlineMedium, color = lightBlue)
            Text("A simple and efficient to-do app", style = MaterialTheme.typography.bodySmall, color = lightBlue)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome", style = MaterialTheme.typography.titleMedium)
            Text("Ready to explore? Log in to get started.", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onSignIn,
                colors = ButtonDefaults.buttonColors(containerColor = lightBlue)
            ) {
                Text("SIGN IN WITH GOOGLE")
            }
        }

        Text("© UTHSmartTasks", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ProfileScreen(user: User, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Avatar hình ảnh profile.png
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = user.name,
            onValueChange = {},
            label = { Text("Name") },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = user.email,
            onValueChange = {},
            label = { Text("Email") },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = user.dateOfBirth,
            onValueChange = {},
            label = { Text("Date of Birth") },
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF64B5F6)
            )
        ) {
            Text("Back", color = Color.White)
        }
    }
}
