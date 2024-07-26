package com.example.prova02_gilmarjr

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova02_gilmarjr.ui.theme.Prova02GilmarJrTheme
import com.example.prova02_gilmarjr.view_models.BikeViewModel
import com.example.prova02_gilmarjr.view_models.CustomerViewModel
import com.example.prova02_gilmarjr.views.HomeView
import com.example.prova02_gilmarjr.views.bike.AddBikeView
import com.example.prova02_gilmarjr.views.bike.BikeView
import com.example.prova02_gilmarjr.views.bike.EditBikeView
import com.example.prova02_gilmarjr.views.customer.AddCustomerView
import com.example.prova02_gilmarjr.views.customer.CustomerView
import com.example.prova02_gilmarjr.views.customer.EditCustomerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prova02GilmarJrTheme {
                View(this)
            }
        }
    }
}

@Composable
fun View(context: Context) {

    val navController = rememberNavController()

    var showFab = remember { mutableStateOf(true) }

    val customerViewModel = CustomerViewModel()
    val bikeViewModel = BikeViewModel()

    Scaffold(
        bottomBar = {
            if (showFab.value){
                NavBar(navController = navController, customerViewModel, bikeViewModel,context)
            }

        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(it)
        ) {
            NavHost(navController, startDestination = "Home") {
                composable("Home") {
                    showFab.value = true
                    HomeView(navController)
                }
                composable("Customers") {
                    showFab.value = true
                    CustomerView(navController = navController, customerViewModel)
                }
                composable("AddCustomer") {
                    showFab.value = false
                    AddCustomerView(navController = navController, customerViewModel)
                }
                composable("EditCustomer") {
                    showFab.value = false
                    EditCustomerView(navController = navController, customerViewModel = customerViewModel)
                }
                composable("Bikes") {
                    showFab.value = true
                    BikeView(navController = navController, bikeViewModel = bikeViewModel)
                }
                composable("AddBike") {
                    showFab.value = false
                    AddBikeView(navController = navController, bikeViewModel = bikeViewModel, customerViewModel = customerViewModel)

                }
                composable("EditBike") {
                    showFab.value = false
                    EditBikeView(navController = navController, bikeViewModel = bikeViewModel)
                }


            }
        }

    }

}

@Composable
fun NavBar(navController: NavHostController, customerViewModel: CustomerViewModel, bikeViewModel: BikeViewModel, context: Context){

    Box(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier
                .background(Color.Black)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            AppBarItem(
                description = "Home",
                icon = Icons.Filled.Home,
                painter = null,
                color = Color.White
            ) {
                navController.navigate("Home")
            }
            AppBarItem(
                description = "Clientes",
                icon = Icons.Filled.Person,
                painter = null,
                color = Color.White
            ) {
                navController.navigate("Customers")
            }
            AppBarItem(
                description = "Bikes",
                icon = null,
                painter = painterResource(id = R.drawable.bike),
                color = Color.White
            ) {
                navController.navigate("Bikes")
            }

            AppBarItem(
                description = "TXT",
                icon = null,
                painter = painterResource(id = R.drawable.txt),
                color = Color.White
            ) {

                val FilePath = "MeuArquivo"
                var ExternalFile: File?=null

                ExternalFile = File(context.getExternalFilesDir(FilePath), "Backup")
                var customers = customerViewModel.customers.value
                var bikes = bikeViewModel.bikes.value

                try{
                    val fileOutPutStream = FileOutputStream(ExternalFile)

                    customers?.forEach {

                        fileOutPutStream.write(("---------------------------------------------------------\n").toByteArray())
                        fileOutPutStream.write(it.toString().toByteArray())
                        var cpf = it.cpf

                        bikes?.forEach {
                            if(it.customer_cpf == cpf)
                            fileOutPutStream.write(it.toString().toByteArray())
                        }

                    }

                    fileOutPutStream.close()
                    Toast.makeText(context,"Backup salvo com sucesso!",Toast.LENGTH_SHORT).show()
                }catch(e: IOException) {
                    Log.i("Erro","Erro: "+e.printStackTrace())
                }



            }

        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppBarItem(
    description: String,
    icon: ImageVector? = null,
    painter: Painter? = null,
    color: Color = Color.Black,
    onClick: () -> Unit
) {
    Box() {
        IconButton(onClick = onClick,
            Modifier
                .fillMaxHeight()
                .width(70.dp)) {
            if (icon !== null) {
                Icon(icon, contentDescription = description, Modifier.scale(2f), tint = color)
            } else if (painter !== null) {
                Icon(
                    painter,
                    contentDescription = description,
                    Modifier.scale(0.9f),
                    tint = color
                )
            }

        }
    }
}
