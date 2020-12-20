package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.OrderDao
import com.sungkunn.inam.new_design.model.Order

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

    fun getOrderByHeadOrder(headorderId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("headorder_id", headorderId)
        return queryReference
    }

    fun getOrderByUserAndStatus(userId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("status", status)
        return queryReference
    }

    fun getOrderByProductAndStatus(productId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("product_id", productId).whereEqualTo("status", status)
        return queryReference
    }

    fun getOrderByPlaceAndStatus(placeId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("place_id", placeId).whereEqualTo("status", status)
        return queryReference
    }

    fun getOrderByProduct(userId: String, productId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("product_id", productId).whereEqualTo("status", status)
        return queryReference
    }

    fun getOrder(subOrderId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(subOrderId)
        return documentReference
    }

    fun deleteOrder(item: OrderDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}