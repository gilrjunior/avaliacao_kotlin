package com.example.prova02_gilmarjr.views.customer

import androidx.compose.foundation.layout.Arrangement
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
fun EditCustomerView(navController: NavController, customerViewModel: CustomerViewModel){

    var customer = customerViewModel.selectedCustomer.value
    var cpf by remember { mutableStateOf(customer?.let { TextFieldValue(it.cpf) })}
    var name by remember { mutableStateOf(customer?.let { TextFieldValue(it.name) })}
    var email by remember { mutableStateOf(customer?.let { TextFieldValue(it.email) })}
    var instagram by remember { mutableStateOf(customer?.let { TextFieldValue(it.instagram) })}

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

            cpf?.let {
                input(value = it, onValueChange = { cpf = it }, "Digite seu cpf", "Cpf", false)

            }
            name?.let {
                input(value = it, onValueChange = { name = it }, "Digite seu nome", "Nome")

            }

            email?.let {
                input(value = it, onValueChange = { email = it }, "Digite seu email", "Email")

            }

            instagram?.let {
                input(value = it, onValueChange = { instagram = it }, "Digite seu instagram", "Instagram")
            }

        }

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ), onClick = {

                if(cpf != null && name!=null && email != null && instagram != null){
                    val customer = Customer(cpf!!.text, name!!.text, email!!.text, instagram!!.text)
                    customerViewModel.update(customer)
                }

                navController.navigate("Customers")
            }) {
            Text(text = "Salvar")
        }

    }



}