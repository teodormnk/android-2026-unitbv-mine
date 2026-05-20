package cst.unitbvfmi2026.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cst.unitbvfmi2026.data.entities.AddressEntity
import cst.unitbvfmi2026.util.isValidLocation
import cst.unitbvfmi2026.viewModels.AddressesViewModel

@Composable
fun AddressesScreen(viewModel: AddressesViewModel = viewModel(), onAddressClick: (AddressEntity) -> Unit = {}) {
    val addresses = viewModel.addresses.collectAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) //pune intre fiecare elem cate un spatiu de 16 dp
    ) {
        item {
            AddressesScreenHeader(viewModel)
        }
        items(addresses.value) { address -> //un fel de foreach()
            AddressCell(address, onAddressClick)
        }
    }
}

@Composable
fun AddressCell(address: AddressEntity, onClick: (AddressEntity) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onClick(address) }),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    )
    {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = address.city,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = address.country,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),//f - float, pt a nu crea evidenta pt fiecare culoare
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddressesScreenHeader(viewModel: AddressesViewModel) {
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var cityError by remember { mutableStateOf<String?>(null) }
    var countryError by remember { mutableStateOf<String?>(null) }
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
            value = city,
            onValueChange = { newValue ->
                city = newValue//primeste val scrisa in field
                cityError = null
            },
            label = {
                Text("City")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.LocationCity,
                    contentDescription = null
                )//content description ajuta la teste unitare
            },
            isError = cityError?.let {
                true
            } ?: false,
            supportingText = cityError?.let { { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, //tastatura speciala cu @ usor accesibil (langa space)
                imeAction = ImeAction.Next//face butonul de "Enter" sa faca "Next"
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)

        )
        OutlinedTextField(
            value = country,
            onValueChange = { newValue ->
                country = newValue//primeste val scrisa in field
                countryError = null
            },
            label = {
                Text("Country")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Map,
                    contentDescription = null
                )//content description ajuta la teste unitare
            },
            isError = countryError?.let {
                true
            } ?: false,
            supportingText = countryError?.let { { Text(it) } },
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
                if (!city.isValidLocation()) {
                    cityError = "Invalid city"
                    valid = false
                }
                if (!country.isValidLocation()) {
                    countryError = "Invalid country"
                    valid = false
                }
                if (valid) {
                    cityError = null
                    countryError = null
                    viewModel.insert(city, country)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add address")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddressesScreenPreview() {
    AddressesScreen()
}