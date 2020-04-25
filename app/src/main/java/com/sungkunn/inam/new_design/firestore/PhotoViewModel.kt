package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Comment
import com.sungkunn.inam.new_design.model.CommentDao
import com.sungkunn.inam.new_design.model.Photo
import com.sungkunn.inam.new_design.model.PhotoDao
import java.util.*

class PhotoViewModel : ViewModel() {

    val TAG = "PHOTO_VIEW_MODEL"
    var firebaseRepository = PhotoRepository()

    var photo : MutableLiveData<ArrayList<PhotoDao>> = MutableLiveData()
    var photoItem : MutableLiveData<PhotoDao> = MutableLiveData()

    fun getNewId(): String{
        return firebaseRepository.getNewId()
    }

    fun addPhoto(item: Photo){
        firebaseRepository.addPhoto(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Comment!")
        }
    }

    fun savePhoto(item: PhotoDao){
        firebaseRepository.savePhoto(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Comment!")
        }
    }

    fun getPhotoAll(): LiveData<ArrayList<PhotoDao>> {
        firebaseRepository.getPhotoAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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
        })

        return photo
    }

    fun getPhotoByItem(itemId: String): LiveData<ArrayList<PhotoDao>> {
        firebaseRepository.getPhotoByItem(itemId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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
        })

        return photo
    }

    fun getPhoto(photoId: String): LiveData<PhotoDao> {
        firebaseRepository.getPhoto(photoId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                photo.value = null
                return@addSnapshotListener
            }
            photoItem.value = PhotoDao(value!!.id, value!!.toObject(Photo::class.java)!!)

        }
        return photoItem!!
    }

    // delete an place from firebase
    fun deletePhoto(item: PhotoDao){
        firebaseRepository.deletePhoto(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Comment")
        }
    }

}