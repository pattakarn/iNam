package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Stock
import com.sungkunn.inam.new_design.model.StockDao

class StockViewModel : ViewModel() {

    val TAG = "STOCK_VIEW_MODEL"
    var firebaseRepository = StockRepository()

    var stock : MutableLiveData<ArrayList<StockDao>> = MutableLiveData()
    var stockItem : MutableLiveData<StockDao>? = MutableLiveData()

    fun addStock(item: Stock){
        firebaseRepository.addStock(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Stock!")
        }
    }

    fun saveStock(item: StockDao){
        firebaseRepository.saveStock(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Product!")
        }
    }

    fun getStockAll(): LiveData<ArrayList<StockDao>> {
        firebaseRepository.getStockAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stock.value = null
                return@EventListener
            }

            var itemList : ArrayList<StockDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Stock::class.java)

                itemList.add(StockDao(doc.id, item))
            }
            stock.value = itemList
        })

        return stock
    }

    fun getStockByItem(itemId: String): LiveData<ArrayList<StockDao>> {
        firebaseRepository.getStockByItem(itemId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stock.value = null
                return@EventListener
            }

            var itemList : ArrayList<StockDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Stock::class.java)

                itemList.add(StockDao(doc.id, item))
            }
            stock.value = itemList
        })

        return stock
    }

    fun getStock(stockId: String): LiveData<StockDao> {
        firebaseRepository.getStock(stockId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                stock.value = null
                return@addSnapshotListener
            }
            stockItem!!.value = StockDao(value!!.id, value!!.toObject(Stock::class.java)!!)

        }

        return stockItem!!
    }

    // delete an place from firebase
    fun deleteStock(item: StockDao){
        firebaseRepository.deleteStock(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}