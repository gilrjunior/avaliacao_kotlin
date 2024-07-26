package com.example.prova02_gilmarjr.views.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prova02_gilmarjr.components.AlertDialog
import com.example.prova02_gilmarjr.components.input
import com.example.prova02_gilmarjr.models.Customer
import com.example.prova02_gilmarjr.view_models.CustomerViewModel
import kotlinx.coroutines.launch

@Composable
fun CustomerView(navController: NavController, customerViewModel: CustomerViewModel){

    var search by remember { mutableStateOf(TextFieldValue("")) }
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
    ){
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Clientes",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(50.dp))
            input(value = search,
                onValueChange = {
                    it -> search = it; customerViewModel.search_customer(search.text)
                                }, "Buscar", "Buscar")
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn(Modifier.height(240.dp)) {

                if (customers != null) {
                    items(customers, key = { it.cpf }) { customer ->

                        CustomerCard(customer = customer, customerViewModel = customerViewModel, navController = navController)
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                ), onClick = {
                    navController.navigate("AddCustomer")
                }) {
                Text(text = "Novo")
            }


        }

    }



}

@Composable
fun CustomerCard(customer: Customer, customerViewModel: CustomerViewModel, navController: NavController){

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialog(
                onDismiss = {
                    openAlertDialog.value = false
                },
                onDismissRequest = {
                    openAlertDialog.value = false
                    customerViewModel.delete(customer.cpf)
                    navController.navigate("Customers")
                                   },
                onConfirmation = {
                    openAlertDialog.value = false
                    navController.navigate("EditCustomer")
                },
                dialogTitle = customer.name,
                dialogText = "CPF: ${customer.cpf} \n" +
                        "Email: ${customer.email} \n" +
                        "Instagram: ${customer.instagram} \n",
            )
        }
    }

    Card(
        Modifier
            .height(50.dp)
            .width(300.dp)
            .clickable(onClick = {
                openAlertDialog.value = true
                customerViewModel.set_selected_customer(customer)
            })
        ,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )

    ) {
        Column(Modifier.padding(10.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = customer.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )

                Icon(
                    Icons.Filled.Info,
                    contentDescription = "Delete"
                )

            }
        }
    }

}