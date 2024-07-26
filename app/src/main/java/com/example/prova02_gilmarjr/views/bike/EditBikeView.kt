package com.example.prova02_gilmarjr.views.bike

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
import com.example.prova02_gilmarjr.models.Bike
import com.example.prova02_gilmarjr.view_models.BikeViewModel

@Composable
fun EditBikeView(navController: NavController, bikeViewModel: BikeViewModel){

    var bike = bikeViewModel.selectedBike.value
    var code by remember { mutableStateOf(bike?.let { TextFieldValue(it.code) }) }
    var model by remember { mutableStateOf(bike?.let { TextFieldValue(it.model) }) }
    var chassiMaterial by remember { mutableStateOf(bike?.let { TextFieldValue(it.chassi_material) }) }
    var rim by remember { mutableStateOf(bike?.let { TextFieldValue(it.rim.toString()) }) }
    var price by remember { mutableStateOf(bike?.let { TextFieldValue(it.price.toString()) }) }
    var numberGears by remember { mutableStateOf(bike?.let { TextFieldValue(it.number_gears.toString()) }) }
    var customerCpf by remember { mutableStateOf(bike?.let { TextFieldValue(it.customer_cpf) }) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Editar Bicicleta",
            fontSize = 40.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Column(
            Modifier.height(500.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            code?.let {
                input(value = it, onValueChange = { code = it }, "Digite o código", "Código", false)
            }
            model?.let {
                input(value = it, onValueChange = { model = it }, "Digite o modelo", "Modelo")
            }
            chassiMaterial?.let {
                input(value = it, onValueChange = { chassiMaterial = it }, "Digite o material do chassi", "Material do Chassi")
            }
            rim?.let {
                input(value = it, onValueChange = { rim = it }, "Digite o aro", "Aro")
            }
            price?.let {
                input(value = it, onValueChange = { price = it }, "Digite o preço", "Preço")
            }
            numberGears?.let {
                input(value = it, onValueChange = { numberGears = it }, "Digite o número de marchas", "Número de Marchas")
            }
            customerCpf?.let {
                input(value = it, onValueChange = { customerCpf = it }, "Digite o CPF do cliente", "CPF do Cliente", false)
            }

        }

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ), onClick = {

                if (code != null && model != null && chassiMaterial != null && rim != null && price != null && numberGears != null && customerCpf != null) {
                    val bike = Bike(
                        code = code!!.text,
                        model = model!!.text,
                        chassi_material = chassiMaterial!!.text,
                        rim = rim!!.text.toIntOrNull() ?: 0,
                        price = price!!.text.toDoubleOrNull() ?: 0.0,
                        number_gears = numberGears!!.text.toIntOrNull() ?: 0,
                        customer_cpf = customerCpf!!.text
                    )
                    bikeViewModel.update(bike)
                }

                navController.navigate("Bikes")
            }) {
            Text(text = "Salvar")
        }

    }

}
