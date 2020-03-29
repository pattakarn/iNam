package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.StockLog
import com.sungkunn.inam.new_design.model.StockLogDao

class StockLogViewModel : ViewModel() {

    val TAG = "STOCKLOG_VIEW_MODEL"
    var firebaseRepository = StockLogRepository()

    var stockLog : MutableLiveData<ArrayList<StockLogDao>> = MutableLiveData()
    var stockLogItem : MutableLiveData<StockLogDao>? = MutableLiveData()

    fun addStockLog(item: StockLog){
        firebaseRepository.addStockLog(item).addOnFailureListener {
            Log.e(TAG,"Failed to save StockLog!")
        }
    }

    fun saveStockLog(item: StockLogDao){
        firebaseRepository.saveStockLog(item).addOnFailureListener {
            Log.e(TAG,"Failed to save StockLog!")
        }
    }

    fun getStockLogAll(): LiveData<ArrayList<StockLogDao>> {
        firebaseRepository.getStockLogAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stockLog.value = null
                return@EventListener
            }

            var stockLogList : ArrayList<StockLogDao> = ArrayList()
            for (doc in value!!) {
                var stockLogItem = doc.toObject(StockLog::class.java)

                stockLogList.add(StockLogDao(doc.id, stockLogItem))
            }
            stockLog.value = stockLogList
        })

        return stockLog
    }

    fun getStockLogByItem(itemId: String): LiveData<ArrayList<StockLogDao>> {
        firebaseRepository.getStockLogByItem(itemId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stockLog.value = null
                return@EventListener
            }

            var itemList : ArrayList<StockLogDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(StockLog::class.java)

                itemList.add(StockLogDao(doc.id, item))
            }
            stockLog.value = itemList
        })

        return stockLog
    }

    fun getStock(stockId: String): LiveData<StockLogDao> {
        firebaseRepository.getStockLog(stockId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stockLog.value = null
                return@addSnapshotListener
            }
            stockLogItem!!.value = StockLogDao(value!!.id, value!!.toObject(StockLog::class.java)!!)

        }

        return stockLogItem!!
    }

    // delete an place from firebase
    fun deleteStockLog(item: StockLogDao){
        firebaseRepository.deleteStockLog(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete StockLog")
        }
    }

}