package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Order
import com.sungkunn.inam.new_design.model.OrderDao
import java.util.*

class OrderViewModel : ViewModel() {

    val TAG = "ORDER_VIEW_MODEL"
    var firebaseRepository = OrderRepository()

    var order : MutableLiveData<ArrayList<OrderDao>> = MutableLiveData()
    var orderItem : MutableLiveData<OrderDao> = MutableLiveData()

    // add place to firebase
    fun addOrder(item: Order){
        firebaseRepository.addOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }


    // save place to firebase
    fun saveOrder(item: OrderDao){
        firebaseRepository.saveOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getOrderAll(): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var orderList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var orderItem = doc.toObject(Order::class.java)

                orderList.add(OrderDao(doc.id, orderItem))
            }
            order.value = orderList
        })

        return order
    }

    fun getOrderByUser(userId: String): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderByUser(userId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var orderList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var orderItem = doc.toObject(Order::class.java)

                orderList.add(OrderDao(doc.id, orderItem))
            }
            order.value = orderList
        })

        return order
    }

    fun getOrderByStatus(userId: String, status: String): LiveData<ArrayList<OrderDao>> {
        firebaseRepository.getOrderByStatus(userId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@EventListener
            }

            var orderList : ArrayList<OrderDao> = ArrayList()
            for (doc in value!!) {
                var orderItem = doc.toObject(Order::class.java)

                orderList.add(OrderDao(doc.id, orderItem))
            }
            order.value = orderList
        })

        return order
    }

    fun getOrder(orderId: String): LiveData<OrderDao> {
        firebaseRepository.getOrder(orderId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                order.value = null
                return@addSnapshotListener
            }
            orderItem.value = OrderDao(value!!.id, value!!.toObject(Order::class.java)!!)

        }
        return orderItem!!
    }

    // delete an place from firebase
    fun deleteOrder(item: OrderDao){
        firebaseRepository.deleteOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}