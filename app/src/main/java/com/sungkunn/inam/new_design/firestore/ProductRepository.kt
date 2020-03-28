package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.sungkunn.inam.new_design.model.Product
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.ProductOrderDao

class ProductRepository {

    val TAG = "PRODUCT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()

    fun addProduct(item: Product): Task<DocumentReference> {
        var documentReference = firestoreDB.collection("products-new")
        return documentReference.add(item)
    }

    fun saveProduct(item: ProductDao): Task<Void> {
        var documentReference = firestoreDB.collection("products-new").document(item.id)
        return documentReference.set(item.data)
    }

    fun getProductAll(): CollectionReference {
        var collectionReference = firestoreDB.collection("products-new")
        return collectionReference
    }

    fun getProductByPlace(placeId: String): Query {
        var queryReference = firestoreDB.collection("products-new").whereEqualTo("place_id", placeId)
        return queryReference
    }

    fun getProductByOrder(orderList: ArrayList<ProductOrderDao>): Query {
        var queryReference = firestoreDB.collection("products-new")
        for (temp in orderList)
            queryReference.whereEqualTo(FieldPath.documentId(), temp.data.product_id)
        return queryReference
    }

    fun getProduct(productId: String): DocumentReference {
        var documentReference = firestoreDB.collection("products-new").document(productId)
        return documentReference
    }

    fun deleteProduct(item: ProductDao): Task<Void> {
        var documentReference =  firestoreDB.collection("products-new").document(item.id)
        return documentReference.delete()
    }
}