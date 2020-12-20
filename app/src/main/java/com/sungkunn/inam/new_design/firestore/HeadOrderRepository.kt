package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.HeadOrder
import com.sungkunn.inam.new_design.model.HeadOrderDao

class HeadOrderRepository {

    val TAG = "HEADORDER_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "head-orders"

    fun getNewId(): String {
        var id = firestoreDB.collection(collectionPath).document().id
        return id
    }

    fun addHeadOrder(item: HeadOrder): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveHeadOrder(item: HeadOrderDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getHeadOrderAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getHeadOrderByUser(userId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId)
        return queryReference
    }

    fun getHeadOrderByStatus(userId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("status", status)
        return queryReference
    }

    fun getHeadOrder(orderId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(orderId)
        return documentReference
    }

    fun deleteHeadOrder(item: HeadOrderDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}