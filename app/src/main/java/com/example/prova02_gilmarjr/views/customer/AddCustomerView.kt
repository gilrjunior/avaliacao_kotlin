package com.example.prova02_gilmarjr.views.customer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova02_gilmarjr.components.input
import com.example.prova02_gilmarjr.models.Customer
import com.example.prova02_gilmarjr.view_models.CustomerViewModel

@Composable
fun AddCustomerView(navController: NavController, customerViewModel: CustomerViewModel){


    var cpf by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var instagram by remember { mutableStateOf(TextFieldValue("")) }

        Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Adicionar Cliente",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Column(
            Modifier.height(300.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            input(value = cpf, onValueChange = { cpf = it }, "Digite seu cpf", "Cpf")
            input(value = name, onValueChange = { name = it }, "Digite seu nome", "Nome")
            input(value = email, onValueChange = { email = it }, "Digite seu email", "Email")
            input(value = instagram, onValueChange = { instagram = it }, "Digite seu instagram", "Instagram")
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ), onClick = {
                val customer = Customer(cpf.text, name.text, email.text, instagram.text)
                customerViewModel.create(customer)
                navController.navigate("Customers")
            }) {
            Text(text = "Adicionar")
        }

    }



}