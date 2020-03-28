package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Comment
import com.sungkunn.inam.new_design.model.CommentDao
import java.util.*

class CommentViewModel : ViewModel() {

    val TAG = "COMMENT_VIEW_MODEL"
    var firebaseRepository = CommentRepository()

    var comment : MutableLiveData<ArrayList<CommentDao>> = MutableLiveData()
    var commentItem : MutableLiveData<CommentDao> = MutableLiveData()

    fun addComment(item: Comment){
        firebaseRepository.addComment(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Comment!")
        }
    }

    fun saveComment(item: CommentDao){
        firebaseRepository.saveComment(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Comment!")
        }
    }

    fun getCommentAll(): LiveData<ArrayList<CommentDao>> {
        firebaseRepository.getCommentAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                comment.value = null
                return@EventListener
            }

            var commentList : ArrayList<CommentDao> = ArrayList()
            for (doc in value!!) {
                var commentItem = doc.toObject(Comment::class.java)

                commentList.add(CommentDao(doc.id, commentItem))
            }
            comment.value = commentList
        })

        return comment
    }

    fun getCommentByItem(itemId: String): LiveData<ArrayList<CommentDao>> {
        firebaseRepository.getCommentByItem(itemId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                comment.value = null
                return@EventListener
            }

            var commentList : ArrayList<CommentDao> = ArrayList()
            for (doc in value!!) {
                var commentItem = doc.toObject(Comment::class.java)

                commentList.add(CommentDao(doc.id, commentItem))
            }
            comment.value = commentList
        })

        return comment
    }

    fun getComment(commentId: String): LiveData<CommentDao> {
        firebaseRepository.getComment(commentId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                comment.value = null
                return@addSnapshotListener
            }
            commentItem.value = CommentDao(value!!.id, value!!.toObject(Comment::class.java)!!)

        }
        return commentItem!!
    }

    // delete an place from firebase
    fun deleteComment(item: CommentDao){
        firebaseRepository.deleteComment(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Comment")
        }
    }

}