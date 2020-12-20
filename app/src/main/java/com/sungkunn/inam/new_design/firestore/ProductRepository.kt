package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.sungkunn.inam.new_design.model.Product
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.ProductOrderDao

class ProductRepository {

    val TAG = "PRODUCT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "products-new"

    fun addProduct(item: Product): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveProduct(item: ProductDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getProductAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getProductByPlace(placeId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("place_id", placeId)
        return queryReference
    }

    fun getProductByOrder(orderList: ArrayList<ProductOrderDao>): Query {
        var queryReference = firestoreDB.collection(collectionPath)
        for (temp in orderList)
            queryReference.whereEqualTo(FieldPath.documentId(), temp.data.product_id)
        return queryReference
    }

    fun getProduct(productId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(productId)
        return documentReference
    }

    fun getProductByList(productIdList: ArrayList<String>): Query {
        var queryReference = firestoreDB.collection(collectionPath)
        for (temp in productIdList)
            queryReference.whereEqualTo(FieldPath.documentId(), temp)
        return queryReference
    }

    fun deleteProduct(item: ProductDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}