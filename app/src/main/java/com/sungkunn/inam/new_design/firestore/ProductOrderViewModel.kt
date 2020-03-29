package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.ProductOrder
import com.sungkunn.inam.new_design.model.ProductOrderDao
import java.util.*

class ProductOrderViewModel : ViewModel() {

    val TAG = "PRODUCTORDER_VIEW_MODEL"
    var firebaseRepository = ProductOrderRepository()

    var productOrder : MutableLiveData<ArrayList<ProductOrderDao>> = MutableLiveData()
    var productOrderItem : MutableLiveData<ProductOrderDao> = MutableLiveData()

    // add place to firebase
    fun addProductOrder(item: ProductOrder){
        firebaseRepository.addProductOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }


    // save place to firebase
    fun saveProductOrder(item: ProductOrderDao){
        firebaseRepository.saveProductOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getProductOrderAll(): LiveData<ArrayList<ProductOrderDao>> {
        firebaseRepository.getProductOrderAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                productOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(ProductOrder::class.java)

                itemList.add(ProductOrderDao(doc.id, item))
            }
            productOrder.value = itemList
        })

        return productOrder
    }

    fun getProductOrderByOrder(userId: String): LiveData<ArrayList<ProductOrderDao>> {
        firebaseRepository.getProductOrderByOrder(userId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                productOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(ProductOrder::class.java)

                itemList.add(ProductOrderDao(doc.id, item))
            }
            productOrder.value = itemList
        })

        return productOrder
    }

    fun getProductOrderByStatus(userId: String, status: String): LiveData<ArrayList<ProductOrderDao>> {
        firebaseRepository.getProductOrderByStatus(userId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                productOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(ProductOrder::class.java)

                itemList.add(ProductOrderDao(doc.id, item))
            }
            productOrder.value = itemList
        })

        return productOrder
    }

    fun getProductOrderByProduct(userId: String, productId: String, status: String): LiveData<ArrayList<ProductOrderDao>> {
        firebaseRepository.getProductOrderByProduct(userId, productId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                productOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(ProductOrder::class.java)

                itemList.add(ProductOrderDao(doc.id, item))
            }
            productOrder.value = itemList
        })

        return productOrder
    }

    fun getProductOrder(productOrderId: String): LiveData<ProductOrderDao> {
        firebaseRepository.getProductOrder(productOrderId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                productOrder.value = null
                return@addSnapshotListener
            }
            productOrderItem.value = ProductOrderDao(value!!.id, value!!.toObject(ProductOrder::class.java)!!)

        }
        return productOrderItem!!
    }

    // delete an place from firebase
    fun deleteProductOrder(item: ProductOrderDao){
        firebaseRepository.deleteProductOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}