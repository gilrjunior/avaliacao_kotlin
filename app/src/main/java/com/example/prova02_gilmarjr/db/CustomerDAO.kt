package com.example.prova02_gilmarjr.db

import android.util.Log
import com.example.prova02_gilmarjr.models.Customer
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class CustomerDAO {

    private val db = Firebase.firestore

    suspend fun insertCustomer(customer: Customer) {
        try {
            db.collection("customers")
                .document(customer.cpf)
                .set(customer)
                .await()
            Log.i("Database Info", "Novo usuário adicionado com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao adicionar novo usuário", e)
        }
    }

    suspend fun getAllCustomers(): List<Customer> {
        return try {
            val result = db.collection("customers")
                .get()
                .await()
            resultFilter(result)
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao mostrar.", e)
            arrayListOf()
        }
    }

    suspend fun getCustomer(cpf: String): List<Customer> {
        return try {
            val result = db.collection("customers")
                .whereEqualTo("cpf", cpf)
                .get()
                .await()
            resultFilter(result)
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao mostrar.", e)
            arrayListOf()
        }
    }

    suspend fun updateCustomer(customer: Customer) {
        try {
            db.collection("customers").document(customer.cpf).update(
                mapOf(
                    "name" to customer.name,
                    "email" to customer.email,
                    "instagram" to customer.instagram
                )
            ).await()
            Log.d("Database Info", "Documento atualizado com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao atualizar documento", e)
        }
    }

    suspend fun deleteCustomer(cpf: String) {
        try {
            db.collection("customers").document(cpf)
                .delete()
                .await()
            Log.d("Database Info", "Documento removido com sucesso!")
        } catch (e: Exception) {
            Log.e("Database Info", "Erro ao remover!", e)
        }
    }

    private fun resultFilter(result: QuerySnapshot): List<Customer> {

        return result.toObjects(Customer::class.java)

    }
}