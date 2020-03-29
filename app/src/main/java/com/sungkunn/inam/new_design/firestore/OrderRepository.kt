package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.Order
import com.sungkunn.inam.new_design.model.OrderDao

class OrderRepository {

    val TAG = "ORDER_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "orders"

    fun addOrder(item: Order): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveOrder(item: OrderDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getOrderAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getOrderByUser(userId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId)
        return queryReference
    }

    fun getOrderByStatus(userId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("status", status)
        return queryReference
    }

    fun getOrder(orderId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(orderId)
        return documentReference
    }

    fun deleteOrder(item: OrderDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}