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

class CommunityViewModel : ViewModel() {

    val TAG = "COMMUNITY_VIEW_MODEL"
    var firebaseRepository = CommunityRepository()
    var PhotoFR = PhotoRepository()

    var community : MutableLiveData<ArrayList<CommunityDao>> = MutableLiveData()
    var communityItem : MutableLiveData<CommunityDao> = MutableLiveData()
    var photo : MutableLiveData<ArrayList<PhotoDao>> = MutableLiveData()
    var communityPackItem : MutableLiveData<CommunityPackDao> = MutableLiveData()
    var communityPack : MutableLiveData<ArrayList<CommunityPackDao>> = MutableLiveData()

    // add community to firebase
    fun addCommunity(item: Community){
        firebaseRepository.addCommunity(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Address!")
        }
    }


    // save community to firebase
    fun saveCommunity(item: CommunityDao){
        firebaseRepository.saveCommunity(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Address!")
        }
    }

    // get realtime updates from firebase regarding community
    fun getCommunityAll(): LiveData<ArrayList<CommunityDao>> {
        firebaseRepository.getCommunityAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                community.value = null
                return@EventListener
            }

            var itemList : ArrayList<CommunityDao> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Community::class.java)

                itemList.add(CommunityDao(doc.id, item))
            }
            community.value = itemList
        })

        return community
    }

    fun getCommunity(communityId: String): LiveData<CommunityDao> {
        firebaseRepository.getCommunity(communityId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                community.value = null
                return@addSnapshotListener
            }
            communityItem.value = CommunityDao(value!!.id, value!!.toObject(Community::class.java)!!)
        }

        return communityItem!!
    }

    fun getCommunityPackAll(): LiveData<ArrayList<CommunityPackDao>> {
        firebaseRepository.getCommunityAll().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                community.value = null
                return@EventListener
            }

            var itemList : ArrayList<CommunityDao> = ArrayList()
            var idList : ArrayList<String> = ArrayList()
            for (doc in value!!) {
                var item = doc.toObject(Community::class.java)

                itemList.add(CommunityDao(doc.id, item))
                idList.add(doc.id)
            }
            community.value = itemList

            PhotoFR.getPhotoByItemList(idList).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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

                var packList : ArrayList<CommunityPackDao> = ArrayList()
                for (i in community.value!!){
                    var photoList: ArrayList<PhotoDao> = ArrayList()
                    photoList.addAll(
                        photo.value!!
                        .filter { it.data.item_id.equals(i.id) }
                        .sortedWith(compareBy({ it.data.status })).reversed()
                    )
                    packList.add(CommunityPackDao(i.id, i.data, photoList))
                }

                communityPack.value = packList
            })
        })

        return communityPack
    }

    fun getCommunityPack(communityId: String): LiveData<CommunityPackDao> {
        firebaseRepository.getCommunity(communityId).addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                community.value = null
                return@addSnapshotListener
            }
            communityItem.value = CommunityDao(value!!.id, value!!.toObject(Community::class.java)!!)

            PhotoFR.getPhotoByItem(communityId).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
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

                if (itemList.size > 0) {
                    photo.value!!.addAll(
                        itemList.sortedWith(compareBy({ it.data.status })).reversed()
                    )
                } else {
                    photo.value = itemList
                }
                communityPackItem.value = CommunityPackDao(communityItem.value!!.id, communityItem.value!!.data, photo.value!!)
            })
        }

        return communityPackItem!!
    }

    // delete an community from firebase
    fun deleteCommunity(item: CommunityDao){
        firebaseRepository.deleteCommunity(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Address")
        }
    }

}