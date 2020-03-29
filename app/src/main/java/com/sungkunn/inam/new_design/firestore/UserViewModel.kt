package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.CommentDao
import com.sungkunn.inam.new_design.model.StockLogDao
import com.sungkunn.inam.new_design.model.User
import com.sungkunn.inam.new_design.model.UserDao

class UserViewModel : ViewModel() {

    val TAG = "USER_VIEW_MODEL"
    var firebaseRepository = UserRepository()

    var user : MutableLiveData<ArrayList<UserDao>> = MutableLiveData()
    var userItem : MutableLiveData<UserDao>? = MutableLiveData()

    fun addUser(item: User){
        firebaseRepository.addUser(item).addOnFailureListener {
            Log.e(TAG,"Failed to save User!")
        }
    }

    fun saveUser(item: UserDao){
        firebaseRepository.saveUser(item).addOnFailureListener {
            Log.e(TAG,"Failed to save User!")
        }
    }

    fun getUserAll(): LiveData<ArrayList<UserDao>> {
        firebaseRepository.getUserAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                user.value = null
                return@EventListener
            }

            var itemList : ArrayList<UserDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(User::class.java)

                itemList.add(UserDao(doc.id, item))
            }
            user.value = itemList
        })

        return user
    }

    fun getUserByStockLog(stockLogList: ArrayList<StockLogDao>): LiveData<ArrayList<UserDao>> {
        firebaseRepository.getUserByStockLog(stockLogList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                user.value = null
                return@EventListener
            }

            var itemList : ArrayList<UserDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(User::class.java)

                itemList.add(UserDao(doc.id, item))
            }
            user.value = itemList
        })

        return user
    }

    fun getUserByComment(commentList: ArrayList<CommentDao>): LiveData<ArrayList<UserDao>> {
        firebaseRepository.getUserByComment(commentList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                user.value = null
                return@EventListener
            }

            var itemList : ArrayList<UserDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(User::class.java)

                itemList.add(UserDao(doc.id, item))
            }
            user.value = itemList
        })

        return user
    }

    fun getUser(userId: String): LiveData<UserDao> {
        firebaseRepository.getUser(userId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                user.value = null
                return@addSnapshotListener
            }
            userItem!!.value = UserDao(value!!.id, value!!.toObject(User::class.java)!!)

        }

        return userItem!!
    }

    // delete an place from firebase
    fun deleteUser(item: UserDao){
        firebaseRepository.deleteUser(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}