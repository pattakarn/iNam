package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.HeadOrder
import com.sungkunn.inam.new_design.model.HeadOrderDao
import java.util.*

class HeadOrderViewModel : ViewModel() {

    val TAG = "HEADORDER_VIEW_MODEL"
    var firebaseRepository = HeadOrderRepository()

    var headOrder : MutableLiveData<ArrayList<HeadOrderDao>> = MutableLiveData()
    var headOrderItem : MutableLiveData<HeadOrderDao> = MutableLiveData()

    fun getNewId(): String{
        return firebaseRepository.getNewId()
    }

    fun addHeadOrder(item: HeadOrder){
        firebaseRepository.addHeadOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }


    // save place to firebase
    fun saveHeadOrder(item: HeadOrderDao){
        firebaseRepository.saveHeadOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getHeadOrderAll(): LiveData<ArrayList<HeadOrderDao>> {
        firebaseRepository.getHeadOrderAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                headOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<HeadOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(HeadOrder::class.java)

                itemList.add(HeadOrderDao(doc.id, item))
            }
            headOrder.value = itemList
        })

        return headOrder
    }

    fun getHeadOrderByUser(userId: String): LiveData<ArrayList<HeadOrderDao>> {
        firebaseRepository.getHeadOrderByUser(userId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                headOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<HeadOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(HeadOrder::class.java)

                itemList.add(HeadOrderDao(doc.id, item))
            }
            headOrder.value = itemList
        })

        return headOrder
    }

    fun getHeadOrderByStatus(userId: String, status: String): LiveData<ArrayList<HeadOrderDao>> {
        firebaseRepository.getHeadOrderByStatus(userId, status).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                headOrder.value = null
                return@EventListener
            }

            var itemList : ArrayList<HeadOrderDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(HeadOrder::class.java)

                itemList.add(HeadOrderDao(doc.id, item))
            }
            headOrder.value = itemList
        })

        return headOrder
    }

    fun getHeadOrder(orderId: String): LiveData<HeadOrderDao> {
        firebaseRepository.getHeadOrder(orderId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                headOrder.value = null
                return@addSnapshotListener
            }
            headOrderItem.value = HeadOrderDao(value!!.id, value!!.toObject(HeadOrder::class.java)!!)

        }
        return headOrderItem!!
    }

    // delete an place from firebase
    fun deleteHeadOrder(item: HeadOrderDao){
        firebaseRepository.deleteHeadOrder(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}