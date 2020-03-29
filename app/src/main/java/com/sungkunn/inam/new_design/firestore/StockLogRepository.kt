package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.StockLog
import com.sungkunn.inam.new_design.model.StockLogDao

class StockLogRepository {

    val TAG = "STOCKLOG_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "stock-log"

    fun addStockLog(item: StockLog): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveStockLog(item: StockLogDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getStockLogAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getStockLogByItem(itemId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("item_id", itemId)
        return queryReference
    }

    fun getStockLog(stockLogId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(stockLogId)
        return documentReference
    }

    fun deleteStockLog(item: StockLogDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}