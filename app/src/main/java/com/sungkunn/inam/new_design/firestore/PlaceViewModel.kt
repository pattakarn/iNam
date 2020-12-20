package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.*
import java.util.*
import kotlin.collections.ArrayList

class PlaceViewModel : ViewModel() {

    val TAG = "PLACE_VIEW_MODEL"
    var firebaseRepository = PlaceRepository()
    var PhotoFR = PhotoRepository()

    var place: MutableLiveData<ArrayList<PlaceDao>> = MutableLiveData()
    var placeItem: MutableLiveData<PlaceDao> = MutableLiveData()
    var photo: MutableLiveData<ArrayList<PhotoDao>> = MutableLiveData()
    var placePackItem: MutableLiveData<PlacePackDao> = MutableLiveData()
    var placePack: MutableLiveData<ArrayList<PlacePackDao>> = MutableLiveData()

    // add place to firebase
    fun addPlace(item: Place) {
        firebaseRepository.addPlace(item).addOnFailureListener {
            Log.e(TAG, "Failed to save Place!")
        }
    }


    // save place to firebase
    fun savePlace(item: PlaceDao) {
        firebaseRepository.savePlace(item).addOnFailureListener {
            Log.e(TAG, "Failed to save Place!")
        }
    }

    // get realtime updates from firebase regarding place
    fun getPlaceAll(): LiveData<ArrayList<PlaceDao>> {
        firebaseRepository.getPlaceAll()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    place.value = null
                    return@EventListener
                }

                var itemList: ArrayList<PlaceDao> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Place::class.java)

                    itemList.add(PlaceDao(doc.id, item))
                }
                place.value = itemList
            })

        return place
    }

    fun getPlaceByCommunity(communityId: String): LiveData<ArrayList<PlaceDao>> {
        firebaseRepository.getPlaceByCommunity(communityId)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    place.value = null
                    return@EventListener
                }

                var itemList: ArrayList<PlaceDao> = ArrayList()
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

    fun getPlacePackAll(): LiveData<ArrayList<PlacePackDao>> {
        firebaseRepository.getPlaceAll()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    place.value = null
                    return@EventListener
                }

                var itemList: ArrayList<PlaceDao> = ArrayList()
                var idList: ArrayList<String> = ArrayList()
                for (doc in value!!) {
                    var item = doc.toObject(Place::class.java)

                    itemList.add(PlaceDao(doc.id, item))
                    idList.add(doc.id)
                }
                place.value = itemList

                PhotoFR.getPhotoByItemList(idList)
                    .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            photo.value = null
                            return@EventListener
                        }

                        var itemList: ArrayList<PhotoDao> = ArrayList()
                        for (doc in value!!) {
                            var item = doc.toObject(Photo::class.java)

                            itemList.add(PhotoDao(doc.id, item))
                        }
                        photo.value = itemList

                        var packList: ArrayList<PlacePackDao> = ArrayList()
                        for (i in place.value!!) {
                            var photoList: ArrayList<PhotoDao> = ArrayList()
                            photoList.addAll(
                                photo.value!!
                                    .filter { it.data.item_id.equals(i.id) }
                                    .sortedWith(compareBy({ it.data.status })).reversed()
                            )
                            packList.add(PlacePackDao(i.id, i.data, photoList))
                        }

                        placePack.value = packList
                    })
            })

        return placePack
    }

    fun getPlacePack(placeId: String): LiveData<PlacePackDao> {
        firebaseRepository.getPlace(placeId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                place.value = null
                return@addSnapshotListener
            }
            placeItem.value = PlaceDao(value!!.id, value!!.toObject(Place::class.java)!!)

            PhotoFR.getPhotoByItem(placeId)
                .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        photo.value = null
                        return@EventListener
                    }

                    var itemList: ArrayList<PhotoDao> = ArrayList()
                    for (doc in value!!) {
                        var item = doc.toObject(Photo::class.java)

                        itemList.add(PhotoDao(doc.id, item))
                    }

                    if (itemList.size > 0) {
                        photo.value = ArrayList()
                        photo.value!!.addAll(
                            itemList.sortedWith(compareBy({ it.data.status })).reversed()
                        )
                    } else {
                        photo.value = itemList
                    }
                    placePackItem.value =
                        PlacePackDao(placeItem.value!!.id, placeItem.value!!.data, photo.value!!)
                })
        }
        return placePackItem!!
    }

    // delete an place from firebase
    fun deletePlace(item: PlaceDao) {
        firebaseRepository.deletePlace(item).addOnFailureListener {
            Log.e(TAG, "Failed to delete Place")
        }
    }

}