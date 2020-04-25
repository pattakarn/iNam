package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.Comment
import com.sungkunn.inam.new_design.model.CommentDao
import com.sungkunn.inam.new_design.model.Photo
import com.sungkunn.inam.new_design.model.PhotoDao

class PhotoRepository {

    val TAG = "PHOTO_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var collectionPath = "photos"

    fun getNewId(): String {
        var id = firestoreDB.collection(collectionPath).document().id
        return id
    }

    fun addPhoto(item: Photo): Task<DocumentReference> {
        var documentReference = firestoreDB.collection(collectionPath)
        return documentReference.add(item)
    }

    fun savePhoto(item: PhotoDao): Task<Void> {
        var documentReference = firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.set(item.data)
    }

    fun getPhotoAll(): CollectionReference {
        var collectionReference = firestoreDB.collection(collectionPath)
        return collectionReference
    }

    fun getPhotoByItem(itemId: String): Query {
//        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("item_id", itemId).orderBy("status", Query.Direction.DESCENDING)
        var queryReference = firestoreDB.collection(collectionPath).whereEqualTo("item_id", itemId)
        return queryReference
    }

    fun getPhoto(photoId: String): DocumentReference {
        var documentReference = firestoreDB.collection(collectionPath).document(photoId)
        return documentReference
    }

    fun deletePhoto(item: PhotoDao): Task<Void> {
        var documentReference =  firestoreDB.collection(collectionPath).document(item.id)
        return documentReference.delete()
    }
}