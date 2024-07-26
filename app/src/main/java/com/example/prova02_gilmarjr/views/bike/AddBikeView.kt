package com.example.prova02_gilmarjr.views.bike

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova02_gilmarjr.components.input
import com.example.prova02_gilmarjr.models.Bike
import com.example.prova02_gilmarjr.models.Customer
import com.example.prova02_gilmarjr.view_models.BikeViewModel
import com.example.prova02_gilmarjr.view_models.CustomerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBikeView(navController: NavController, bikeViewModel: BikeViewModel, customerViewModel: CustomerViewModel){

    var code by remember { mutableStateOf(TextFieldValue("")) }
    var model by remember { mutableStateOf(TextFieldValue("")) }
    var chassiMaterial by remember { mutableStateOf(TextFieldValue("")) }
    var rim by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var numberGears by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecionar") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            customerViewModel.get_all()
        }
    }

    var customers = customerViewModel.customers.observeAsState().value

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Adicionar Bike",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Column(
            Modifier.height(500.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            input(value = code, onValueChange = { code = it }, "Digite o código", "Código")
            input(value = model, onValueChange = { model = it }, "Digite o modelo", "Modelo")
            input(
                value = chassiMaterial,
                onValueChange = { chassiMaterial = it },
                "Digite o material do chassi",
                "Material do Chassi"
            )
            input(value = rim, onValueChange = { rim = it }, "Digite o aro", "Aro")
            input(value = price, onValueChange = { price = it }, "Digite o preço", "Preço")
            input(
                value = numberGears,
                onValueChange = { numberGears = it },
                "Digite o número de marchas",
                "Número de Marchas"
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.background(color = Color.Black)
            ) {

                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .background(color = Color.Black),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black

                    ),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(color = Color.Black)
                ) {

                    customers?.forEach { customer ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = customer.name,
                                    color = Color.White
                                )
                            },
                            onClick = {
                                selectedText = customer.name
                                customerViewModel.set_selected_customer(customer)
                                expanded = false
                            }
                        )
                    }

                }

            }

        }

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ), onClick = {
                if(selectedText != "Adicionar"){
                    val bike = customerViewModel.selectedCustomer.value?.let {
                        Bike(
                            code.text,
                            model.text,
                            chassiMaterial.text,
                            rim.text.toInt(),
                            price.text.toDouble(),
                            numberGears.text.toInt(),
                            it.cpf
                        )
                    }

                    if (bike != null) {
                        bikeViewModel.create(bike)
                        navController.navigate("Bikes")
                    }
                }
            }) {
            Text(text = "Adicionar")
        }
    }

}
