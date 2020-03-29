package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.Stock
import com.sungkunn.inam.new_design.model.StockDao

class StockRepository {

    val TAG = "STOCK_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "stock"

    fun addStock(item: Stock): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveStock(item: StockDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getStockAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getStockByItem(itemId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("item_id", itemId)
        return queryReference
    }

    fun getStock(stockId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(stockId)
        return documentReference
    }

    fun deleteStock(item: StockDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}