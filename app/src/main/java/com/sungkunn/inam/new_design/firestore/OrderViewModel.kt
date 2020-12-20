package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.*
import kotlin.collections.ArrayList

class OrderViewModel : ViewModel() {

    val TAG = "ORDER_VIEW_MODEL"
    var firebaseRepository = OrderRepository()
    var ProductFR = ProductRepository()
    var PhotoFR = PhotoRepository()


    var order : MutableLiveData<ArrayList<OrderDao>> = MutableLiveData()
    var orderItem : MutableLiveData<OrderDao> = MutableLiveData()
    var productItem : MutableLiveData<ProductDao> = MutableLiveData()
    var photo : MutableLiveData<ArrayList<PhotoDao>> = MutableLiveData()
    var orderPackItem : MutableLiveData<OrderPackDao> = MutableLiveData()
    var orderPack : MutableLiveData<ArrayList<OrderPackDao>> = MutableLiveData()
    var product : MutableLiveData<ArrayList<ProductDao>> = MutableLiveData()
    var productPack : MutableLiveData<ArrayList<ProductPackDao>> = MutableLiveData()
    var productPackItem : MutableLiveData<ProductPackDao> = MutableLiveData()

    // add community to firebase
    fun addOrder(item: Order){
        firebaseRepository.addOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Address!")
        }
    }


    // save community to firebase
    fun saveOrder(item: OrderDao){
        firebaseRepository.saveOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Address!")
        }
    }

    // get realtime updates from firebase regarding community
    fun getOrderAll(): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var itemList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Order::class.java)

                itemList.add(OrderDao(doc.id, item))
            }
            order.value = itemList
        })

        return order
    }

    fun getPrderByOrder(orderId: String): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderByHeadOrder(orderId)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    order.value = null
                    return@EventListener
                }

                var itemList: ArrayList<OrderDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Order::class.java)

                    itemList.add(OrderDao(doc.id, item))
                }
                order.value = itemList
            })

        return order
    }

    fun getOrderByStatus(userId: String, status: String): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderByUserAndStatus(userId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var itemList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Order::class.java)

                itemList.add(OrderDao(doc.id, item))
            }
            order.value = itemList
        })

        return order
    }

    fun getOrderByProduct(userId: String, productId: String, status: String): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderByProduct(userId, productId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var itemList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Order::class.java)

                itemList.add(OrderDao(doc.id, item))
            }
            order.value = itemList
        })

        return order
    }

    fun getOrder(subOrderId: String): LiveData<OrderDao> {
        firebaseRepository.getOrder(subOrderId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@addSnapshotListener
            }
            orderItem.value = OrderDao(value!!.id, value!!.toObject(Order::class.java)!!)
        }

        return orderItem!!
    }

    fun getOrderPack(subOrderId: String): LiveData<OrderPackDao> {
        firebaseRepository.getOrder(subOrderId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@addSnapshotListener
            }
            orderItem.value = OrderDao(value!!.id, value!!.toObject(Order::class.java)!!)

            ProductFR.getProduct(orderItem.value!!.data.product_id!!).addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    productItem.value = null
                    return@addSnapshotListener
                }
                productItem!!.value = ProductDao(value!!.id, value!!.toObject(Product::class.java)!!)

                PhotoFR.getPhotoByItem(productItem!!.value!!.id).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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
                        photo.value = ArrayList()
                        photo.value!!.addAll(
                            itemList.sortedWith(compareBy({ it.data.status })).reversed()
                        )
                    } else {
                        photo.value = itemList
                    }
                    productPackItem.value = ProductPackDao(productItem!!.value!!.id, productItem!!.value!!.data, photo.value!!)
                    orderPackItem.value = OrderPackDao(orderItem.value!!.id, orderItem.value!!.data, productPackItem.value!!)
                })


            }
        }

        return orderPackItem!!
    }

    fun getOrderPackByUserAndStatus(userId: String, status: String): LiveData<ArrayList<OrderPackDao>> {
        firebaseRepository.getOrderByUserAndStatus(userId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var itemList : ArrayList<OrderDao> = ArrayList()
            var idList : ArrayList<String> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Order::class.java)

                itemList.add(OrderDao(doc.id, item))
                idList.add(doc.id)
            }
            order.value = itemList

            ProductFR.getProductByList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    product.value = null
                    return@EventListener
                }

                getArrayOrderPack(value!!)
//                var itemList : ArrayList<ProductDao> = ArrayList()
//                var idList : ArrayList<String> = ArrayList()
//                for (doc in value!!) {
//                    var item = doc.toObject(Product::class.java)
//
//                    itemList.add(ProductDao(doc.id, item))
//                    idList.add(doc.id)
//                }
//                product.value = itemList
//
//                PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
//                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e)
//                        photo.value = null
//                        return@EventListener
//                    }
//
//                    var itemList : ArrayList<PhotoDao> = ArrayList()
//                    for (doc in value!!) {
//                        var item = doc.toObject(Photo::class.java)
//
//                        itemList.add(PhotoDao(doc.id, item))
//                    }
//                    photo.value = itemList
//
//                    var packList : ArrayList<ProductPackDao> = ArrayList()
//                    for (i in product.value!!){
//                        var photoList: ArrayList<PhotoDao> = ArrayList()
//                        photoList.addAll(
//                            photo.value!!
//                                .filter { it.data.item_id.equals(i.id) }
//                                .sortedWith(compareBy({ it.data.status })).reversed()
//                        )
//                        packList.add(ProductPackDao(i.id, i.data, photoList))
//                    }
//
//                    productPack.value = packList
//
//                    var orderList : ArrayList<OrderPackDao> = ArrayList()
//                    for (i in order.value!!){
//                        var pList: ArrayList<ProductPackDao> = ArrayList()
//                        pList.addAll(
//                            productPack.value!!.filter { it.id.equals(i.data.product_id) }
//                        )
//                        orderList.add(OrderPackDao(i.id, i.data, pList.get(0)))
//                    }
//                    orderPack.value = orderList
//                })
            })
        })

        return orderPack
    }

    fun getOrderPackByProductAndStatus(productId: String, status: String): LiveData<ArrayList<OrderPackDao>> {
        firebaseRepository.getOrderByProductAndStatus(productId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            getArrayOrderPack(value!!)
//            var itemList : ArrayList<OrderDao> = ArrayList()
//            var idList : ArrayList<String> = ArrayList()
//            for (doc in value!!) {
//                var item = doc.toObject(Order::class.java)
//
//                itemList.add(OrderDao(doc.id, item))
//                idList.add(doc.id)
//            }
//            order.value = itemList
//
//            ProductFR.getProductByList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e)
//                    product.value = null
//                    return@EventListener
//                }
//
//                var itemList : ArrayList<ProductDao> = ArrayList()
//                var idList : ArrayList<String> = ArrayList()
//                for (doc in value!!) {
//                    var item = doc.toObject(Product::class.java)
//
//                    itemList.add(ProductDao(doc.id, item))
//                    idList.add(doc.id)
//                }
//                product.value = itemList
//
//                PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
//                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e)
//                        photo.value = null
//                        return@EventListener
//                    }
//
//                    var itemList : ArrayList<PhotoDao> = ArrayList()
//                    for (doc in value!!) {
//                        var item = doc.toObject(Photo::class.java)
//
//                        itemList.add(PhotoDao(doc.id, item))
//                    }
//                    photo.value = itemList
//
//                    var packList : ArrayList<ProductPackDao> = ArrayList()
//                    for (i in product.value!!){
//                        var photoList: ArrayList<PhotoDao> = ArrayList()
//                        photoList.addAll(
//                            photo.value!!
//                                .filter { it.data.item_id.equals(i.id) }
//                                .sortedWith(compareBy({ it.data.status })).reversed()
//                        )
//                        packList.add(ProductPackDao(i.id, i.data, photoList))
//                    }
//
//                    productPack.value = packList
//
//                    var orderList : ArrayList<OrderPackDao> = ArrayList()
//                    for (i in order.value!!){
//                        var pList: ArrayList<ProductPackDao> = ArrayList()
//                        pList.addAll(
//                            productPack.value!!.filter { it.id.equals(i.data.product_id) }
//                        )
//                        orderList.add(OrderPackDao(i.id, i.data, pList.get(0)))
//                    }
//                    orderPack.value = orderList
//                })
//            })
        })

        return orderPack
    }

    private fun getArrayOrderPack(value: QuerySnapshot){
        var itemList : ArrayList<OrderDao> = ArrayList()
        var idList : ArrayList<String> = ArrayList()
        for (doc in value!!) {
            var item = doc.toObject(Order::class.java)

            itemList.add(OrderDao(doc.id, item))
            idList.add(doc.id)
        }
        order.value = itemList

        ProductFR.getProductByList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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

                var orderList : ArrayList<OrderPackDao> = ArrayList()
                for (i in order.value!!){
                    var pList: ArrayList<ProductPackDao> = ArrayList()
                    pList.addAll(
                        productPack.value!!.filter { it.id.equals(i.data.product_id) }
                    )
                    orderList.add(OrderPackDao(i.id, i.data, pList.get(0)))
                }
                orderPack.value = orderList
            })
        })
    }


    // delete an community from firebase
    fun deleteOrder(item: OrderDao){
        firebaseRepository.deleteOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Address")
        }
    }

}