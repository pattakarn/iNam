package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Product
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.ProductOrderDao

class ProductViewModel : ViewModel() {

    val TAG = "PRODUCT_VIEW_MODEL"
    var firebaseRepository = ProductRepository()

    var product : MutableLiveData<ArrayList<ProductDao>> = MutableLiveData()
    var productItem : MutableLiveData<ProductDao>? = MutableLiveData()

    // add product to firebase
    fun addProduct(item: Product){
        firebaseRepository.addProduct(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Product!")
        }
    }


    // save product to firebase
    fun saveProduct(item: ProductDao){
        firebaseRepository.saveProduct(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Product!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getProductAll(): LiveData<ArrayList<ProductDao>> {
        firebaseRepository.getProductAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var productList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var productItem = doc.toObject(Product::class.java)

                productList.add(ProductDao(doc.id, productItem))
            }
            product.value = productList
        })

        return product
    }

    fun getProductByPlace(placeId: String): LiveData<ArrayList<ProductDao>> {
        firebaseRepository.getProductByPlace(placeId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var productList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var productItem = doc.toObject(Product::class.java)

                productList.add(ProductDao(doc.id, productItem))
            }
            product.value = productList
        })

        return product
    }

    fun getProductByOrder(orderList: ArrayList<ProductOrderDao>): LiveData<ArrayList<ProductDao>> {
        firebaseRepository.getProductByOrder(orderList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var productList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var productItem = doc.toObject(Product::class.java)

                productList.add(ProductDao(doc.id, productItem))
            }
            product.value = productList
        })

        return product
    }

    fun getProduct(productId: String): LiveData<ProductDao> {
        firebaseRepository.getProduct(productId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@addSnapshotListener
            }
            productItem!!.value = ProductDao(value!!.id, value!!.toObject(Product::class.java)!!)

        }

        return productItem!!
    }

    // delete an place from firebase
    fun deleteProduct(item: ProductDao){
        firebaseRepository.deleteProduct(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}