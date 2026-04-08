package cst.unitbvfmi2026.ui.screens

import android.R.attr.imeOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cst.unitbvfmi2026.util.isValidEmail
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: ()->Unit = {},
    onLoginClick: ()->Unit = {},
    isLoading: Boolean = false,
    errorMsg: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Welcome",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Log In Page",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { newValue ->
                email = newValue//primeste val scrisa in field
            },
            label = {
                Text("Email")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null
                )//content description ajuta la teste unitare
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, //tastatura speciala cu @ usor accesibil (langa space)
                imeAction = ImeAction.Next//face butonul de "Enter" sa faca "Next"
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        OutlinedTextField(
            value = password,
            onValueChange = { newValue ->
                password = newValue//primeste val scrisa in field
            },
            label = {
                Text("Password")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Password,
                    contentDescription = null
                )//content description ajuta la teste unitare
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, //tastatura speciala cu @ usor accesibil (langa space)
                imeAction = ImeAction.Done//face butonul de "Enter" sa faca "Next"

            ),

            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                IconButton(
                    { passwordVisibility = !passwordVisibility }

                ) {
                    Icon(
                        if (passwordVisibility) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },

                        if (passwordVisibility) "Show password" else "Hide Password",


                        )
                }
            }
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        Button(
            {
                onLoginClick()
            },
            modifier=Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            when (isLoading) {
                true -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
                false -> Text("Login")
            }
        }
        errorMsg?.let{error->
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(error)
        }

        Spacer(
            modifier = Modifier.height(16.dp)

        )
        TextButton(
            onRegisterClick
        ) {
            Text("Don't have an account? Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    LogInScreen()
}