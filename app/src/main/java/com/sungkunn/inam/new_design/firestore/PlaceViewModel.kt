package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Place
import com.sungkunn.inam.new_design.model.PlaceDao
import java.util.*

class PlaceViewModel : ViewModel() {

    val TAG = "PLACE_VIEW_MODEL"
    var firebaseRepository = PlaceRepository()

    var place : MutableLiveData<ArrayList<PlaceDao>> = MutableLiveData()
    var placeItem : MutableLiveData<PlaceDao> = MutableLiveData()

    // add place to firebase
    fun addPlace(item: Place){
        firebaseRepository.addPlace(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }


    // save place to firebase
    fun savePlace(item: PlaceDao){
        firebaseRepository.savePlace(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Place!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getPlaceAll(): LiveData<ArrayList<PlaceDao>> {
        firebaseRepository.getPlaceAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                place.value = null
                return@EventListener
            }

            var itemList : ArrayList<PlaceDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Place::class.java)

                itemList.add(PlaceDao(doc.id, item))
            }
            place.value = itemList
        })

        return place
    }

    fun getPlaceByCommunity(communityId: String): LiveData<ArrayList<PlaceDao>> {
        firebaseRepository.getPlaceByCommunity(communityId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                place.value = null
                return@EventListener
            }

            var itemList : ArrayList<PlaceDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Place::class.java)

                itemList.add(PlaceDao(doc.id, item))
            }
            place.value = itemList
        })

        return place
    }

    fun getPlace(placeId: String): LiveData<PlaceDao> {
        firebaseRepository.getPlace(placeId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                place.value = null
                return@addSnapshotListener
            }
            placeItem.value = PlaceDao(value!!.id, value!!.toObject(Place::class.java)!!)

        }
        return placeItem!!
    }

    // delete an place from firebase
    fun deletePlace(item: PlaceDao){
        firebaseRepository.deletePlace(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Place")
        }
    }

}