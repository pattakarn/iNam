package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.*
import java.util.*

class ProductViewModel : ViewModel() {

    val TAG = "PRODUCT_VIEW_MODEL"
    var firebaseRepository = ProductRepository()
    var PhotoFR = PhotoRepository()

    var product : MutableLiveData<ArrayList<ProductDao>> = MutableLiveData()
    var productItem : MutableLiveData<ProductDao>? = MutableLiveData()
    var photo : MutableLiveData<ArrayList<PhotoDao>> = MutableLiveData()
    var productPackItem : MutableLiveData<ProductPackDao> = MutableLiveData()
    var productPack : MutableLiveData<ArrayList<ProductPackDao>> = MutableLiveData()

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

            var itemList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
            }
            product.value = itemList
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

            var itemList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
            }
            product.value = itemList
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

            var itemList : ArrayList<ProductDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
            }
            product.value = itemList
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

    fun getProductPackAll(): LiveData<ArrayList<ProductPackDao>> {
        firebaseRepository.getProductAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductDao> = ArrayList()
            var idList : ArrayList<String> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
                idList.add(doc.id)
            }
            product.value = itemList

            PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    photo.value = null
                    return@EventListener
                }

                var itemList : ArrayList<PhotoDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Photo::class.java)

                    itemList.add(PhotoDao(doc.id, item))
                }
                photo.value = itemList

                var packList : ArrayList<ProductPackDao> = ArrayList()
                for (i in product.value!!){
                    var photoList: ArrayList<PhotoDao> = ArrayList()
                    photoList.addAll(
                        photo.value!!
                            .filter { it.data.item_id.equals(i.id) }
                            .sortedWith(compareBy({ it.data.status })).reversed()
                    )
                    packList.add(ProductPackDao(i.id, i.data, photoList))
                }

                productPack.value = packList
            })
        })

        return productPack
    }

    fun getProductPackByPlace(placeId: String): LiveData<ArrayList<ProductPackDao>> {
        firebaseRepository.getProductByPlace(placeId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductDao> = ArrayList()
            var idList : ArrayList<String> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
                idList.add(doc.id)
            }
            product.value = itemList

            PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    photo.value = null
                    return@EventListener
                }

                var itemList : ArrayList<PhotoDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Photo::class.java)

                    itemList.add(PhotoDao(doc.id, item))
                }
                photo.value = itemList

                var packList : ArrayList<ProductPackDao> = ArrayList()
                for (i in product.value!!){
                    var photoList: ArrayList<PhotoDao> = ArrayList()
                    photoList.addAll(
                        photo.value!!
                            .filter { it.data.item_id.equals(i.id) }
                            .sortedWith(compareBy({ it.data.status })).reversed()
                    )
                    packList.add(ProductPackDao(i.id, i.data, photoList))
                }

                productPack.value = packList
            })
        })

        return productPack
    }

    fun getProductPackByOrder(orderList: ArrayList<ProductOrderDao>): LiveData<ArrayList<ProductPackDao>> {
        firebaseRepository.getProductByOrder(orderList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@EventListener
            }

            var itemList : ArrayList<ProductDao> = ArrayList()
            var idList : ArrayList<String> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Product::class.java)

                itemList.add(ProductDao(doc.id, item))
                idList.add(doc.id)
            }
            product.value = itemList

            PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    photo.value = null
                    return@EventListener
                }

                var itemList : ArrayList<PhotoDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Photo::class.java)

                    itemList.add(PhotoDao(doc.id, item))
                }
                photo.value = itemList

                var packList : ArrayList<ProductPackDao> = ArrayList()
                for (i in product.value!!){
                    var photoList: ArrayList<PhotoDao> = ArrayList()
                    photoList.addAll(
                        photo.value!!
                            .filter { it.data.item_id.equals(i.id) }
                            .sortedWith(compareBy({ it.data.status })).reversed()
                    )
                    packList.add(ProductPackDao(i.id, i.data, photoList))
                }

                productPack.value = packList
            })
        })

        return productPack
    }

    fun getProductPack(productId: String): LiveData<ProductPackDao> {
        firebaseRepository.getProduct(productId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                product.value = null
                return@addSnapshotListener
            }
            productItem!!.value = ProductDao(value!!.id, value!!.toObject(Product::class.java)!!)

            PhotoFR.getPhotoByItem(productId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    photo.value = null
                    return@EventListener
                }

                var itemList : ArrayList<PhotoDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Photo::class.java)

                    itemList.add(PhotoDao(doc.id, item))
                }

                if (itemList.size > 0) {
                    photo.value!!.addAll(
                        itemList.sortedWith(compareBy({ it.data.status })).reversed()
                    )
                } else {
                    photo.value = itemList
                }
                productPackItem.value = ProductPackDao(productItem!!.value!!.id, productItem!!.value!!.data, photo.value!!)
            })

        }

        return productPackItem!!
    }

    // delete an place from firebase
    fun deleteProduct(item: ProductDao){
        firebaseRepository.deleteProduct(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}