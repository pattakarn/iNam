package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.Comment
import com.sungkunn.inam.new_design.model.CommentDao

class CommentRepository {

    val TAG = "COMMENT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "comments"

    fun addComment(item: Comment): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun saveComment(item: CommentDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getCommentAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getCommentByItem(itemId: String): Query {
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("item_id", itemId)
        return queryReference
    }

    fun getComment(commentId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(commentId)
        return documentReference
    }

    fun deleteComment(item: CommentDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}