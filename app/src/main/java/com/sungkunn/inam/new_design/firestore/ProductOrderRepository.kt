package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.ProductOrder
import com.sungkunn.inam.new_design.model.ProductOrderDao

class ProductOrderRepository {

    val TAG = "PRODUCTORDER_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "product-order"

    fun addProductOrder(item: ProductOrder): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveProductOrder(item: ProductOrderDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getProductOrderAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getProductOrderByOrder(orderId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("order_id", orderId)
        return queryReference
    }

    fun getProductOrderByStatus(userId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("status", status)
        return queryReference
    }

    fun getProductOrderByProduct(userId: String, productId: String, status: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("user_id", userId).whereEqualTo("product_id", productId).whereEqualTo("status", status)
        return queryReference
    }

    fun getProductOrder(productOrderId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(productOrderId)
        return documentReference
    }

    fun deleteProductOrder(item: ProductOrderDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}