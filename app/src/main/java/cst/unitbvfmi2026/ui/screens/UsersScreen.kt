package cst.unitbvfmi2026.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import cst.unitbvfmi2026.data.entities.UserEntity
import cst.unitbvfmi2026.util.isValidEmail
import cst.unitbvfmi2026.util.isValidPassword
import cst.unitbvfmi2026.viewModels.UsersViewModel

@Composable
fun UsersScreen(viewModel: UsersViewModel = viewModel())
{
    val users = viewModel.users.collectAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) //pune intre fiecare elem cate un spatiu de 16 dp
    ) {
        item {
            UserScreenHeader(viewModel)
        }
        item{
            Button(
                onClick = { viewModel.loadUsers() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Load users")
            }
        }
        items(users.value){ user -> //un fel de foreach()
            UserCell(user)
        }
    }
}

@Composable
fun UserCell(user: UserEntity)
{
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        AsyncImage(
            model = user.avatar,
            contentDescription = "${user.name} avatar",
            modifier = Modifier.size(50.dp).clip(CircleShape),
            contentScale = ContentScale.Crop // daca poza nu incape in size, face scale la poza ca sa umple tot containerul
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = user.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = user.email,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),//f - float, pt a nu crea evidenta pt fiecare culoare
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun UserScreenHeader(viewModel: UsersViewModel)
{
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var nameError by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Users",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { newValue ->
                email = newValue//primeste val scrisa in field
                emailError = null
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
            isError = emailError?.let {
                true
            } ?: false,
            supportingText = emailError?.let { { Text(it) } },
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
            value = name,
            onValueChange = { newValue ->
                name = newValue//primeste val scrisa in field
                nameError = null
            },
            label = {
                Text("Name")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null
                )//content description ajuta la teste unitare
            },
            isError = nameError?.let {
                true
            } ?: false,
            supportingText = nameError?.let { { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, //tastatura speciala cu @ usor accesibil (langa space)
                imeAction = ImeAction.Done//face butonul de "Enter" sa faca "Next"

            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        Button(
            {
                var valid = true
                if (!email.isValidEmail()) {
                    emailError = "Invalid Email"
                    valid = false
                }
                if(!name.isValidPassword()) {
                    nameError = "Invalid Name"
                    valid = false
                }
                if (valid) {
                    emailError = null
                    nameError = null
                    viewModel.insert(email, name)
                }
            },
            modifier = Modifier.fillMaxWidth()
            //enabled = !isLoading
        ) {
//            when (isLoading) {
//                true -> CircularProgressIndicator(
//                    modifier = Modifier.size(24.dp),
//                    strokeWidth = 2.dp
//                )
//
//                false -> Text("Login")
//            }
            Text("Add user")

        }
//        errorMessage?.let{error->
//            Spacer(
//                modifier = Modifier.height(8.dp)
//
//            )
//            Text(error)
//        }

    }
}
@Preview(showBackground = true)
@Composable
fun UsersScreenPreview()
{
    UsersScreen()
}