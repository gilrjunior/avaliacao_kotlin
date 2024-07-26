package com.example.prova02_gilmarjr.views.bike

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.prova02_gilmarjr.models.Bike
import com.example.prova02_gilmarjr.view_models.BikeViewModel
import kotlinx.coroutines.launch

@Composable
fun BikeView(navController: NavController, bikeViewModel: BikeViewModel){

    var search by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            bikeViewModel.get_all()
        }
    }

    var bikes = bikeViewModel.bikes.observeAsState().value

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Bikes",
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
                        it -> search = it; bikeViewModel.search_bike(search.text)
                }, "Buscar", "Buscar")
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn(Modifier.height(240.dp)) {

                if (bikes != null) {
                    items(bikes, key = { it.code }) { bike ->

                        BikeCard(bike = bike, bikeViewModel = bikeViewModel, navController = navController)
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
                    navController.navigate("AddBike")
                }) {
                Text(text = "Novo")
            }


        }

    }



}

@Composable
fun BikeCard(bike: Bike, bikeViewModel: BikeViewModel, navController: NavController){

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialog(
                onDismiss = {
                    openAlertDialog.value = false
                },
                onDismissRequest = {
                    openAlertDialog.value = false
                    bikeViewModel.delete(bike.code)
                    navController.navigate("Bikes")
                },
                onConfirmation = {
                    openAlertDialog.value = false
                    navController.navigate("EditBike")
                },
                dialogTitle = bike.model,
                dialogText = "Code: ${bike.code} \n" +
                        "Chassi Material: ${bike.chassi_material} \n" +
                        "Rim: ${bike.rim} \n" +
                        "Price: ${bike.price} \n" +
                        "Number of Gears: ${bike.number_gears} \n" +
                        "CPF do Cliente: ${bike.customer_cpf} \n",
            )
        }
    }

    Card(
        Modifier
            .height(50.dp)
            .width(300.dp)
            .clickable(onClick = {
                openAlertDialog.value = true
                bikeViewModel.set_selected_bike(bike)
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
                    text = bike.model,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )

                Icon(
                    Icons.Filled.Info,
                    contentDescription = "Info"
                )

            }
        }
    }

}