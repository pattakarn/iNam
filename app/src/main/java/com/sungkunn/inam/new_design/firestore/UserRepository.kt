package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.sungkunn.inam.new_design.model.CommentDao
import com.sungkunn.inam.new_design.model.StockLogDao
import com.sungkunn.inam.new_design.model.User
import com.sungkunn.inam.new_design.model.UserDao

class UserRepository {

    val TAG = "USER_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "users"

    fun addUser(item: User): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveUser(item: UserDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getUserAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getUserByStockLog(userList: ArrayList<StockLogDao>): Query {
        var queryReference = firestoreDB.collection(collectionPath)
        for (temp in userList)
            queryReference.whereEqualTo(FieldPath.documentId(), temp.data.user_id)
        return queryReference
    }

    fun getUserByComment(userList: ArrayList<CommentDao>): Query {
        var queryReference = firestoreDB.collection(collectionPath)
        for (temp in userList)
            queryReference.whereEqualTo(FieldPath.documentId(), temp.data.user_id)
        return queryReference
    }

    fun getUser(userId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(userId)
        return documentReference
    }

    fun deleteUser(item: UserDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}