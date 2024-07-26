package com.example.prova02_gilmarjr.db

import android.util.Log
import com.example.prova02_gilmarjr.models.Bike
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class BikeDAO {

    private val db = Firebase.firestore

    suspend fun insertBike(bike: Bike) {
        try {
            db.collection("bikes")
                .document(bike.code)
                .set(bike)
                .await()
            Log.i("Database Info", "Nova bike adicionada com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao adicionar nova bike", e)
        }
    }

    suspend fun getAllBikes(): List<Bike> {
        return try {
            val result = db.collection("bikes")
                .get()
                .await()
            resultFilter(result)
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao mostrar.", e)
            arrayListOf()
        }
    }

    suspend fun getBike(code: String): List<Bike> {
        return try {
            val result = db.collection("bikes")
                .whereEqualTo("code", code)
                .get()
                .await()
            resultFilter(result)
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao mostrar.", e)
            arrayListOf()
        }
    }

    suspend fun updateBike(bike: Bike) {
        try {
            db.collection("bikes").document(bike.code).update(
                mapOf(
                    "model" to bike.model,
                    "chassi_material" to bike.chassi_material,
                    "rim" to bike.rim,
                    "price" to bike.price,
                    "number_gears" to bike.number_gears,
                    "customer_cpf" to bike.customer_cpf
                )
            ).await()
            Log.d("Database Info", "Bike atualizada com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao atualizar bike", e)
        }
    }

    suspend fun deleteBike(code: String) {
        try {
            db.collection("bikes").document(code)
                .delete()
                .await()
            Log.d("Database Info", "Bike removida com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao remover bike!", e)
        }
    }

    private fun resultFilter(result: QuerySnapshot): List<Bike> {
        return result.toObjects(Bike::class.java)
    }

}