package com.sungkunn.inam.new_design.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sungkunn.inam.new_design.model.Community
import com.sungkunn.inam.new_design.model.CommunityDao
import java.util.*

class CommunityViewModel : ViewModel() {

    val TAG = "COMMUNITY_VIEW_MODEL"
    var firebaseRepository = CommunityRepository()

    var community : MutableLiveData<ArrayList<CommunityDao>> = MutableLiveData()
    var communityItem : MutableLiveData<CommunityDao> = MutableLiveData()

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

            var communityList : ArrayList<CommunityDao> = ArrayList()
            for (doc in value!!) {
                var communityItem = doc.toObject(Community::class.java)

                communityList.add(CommunityDao(doc.id, communityItem))
            }
            community.value = communityList
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

    // delete an community from firebase
    fun deleteCommunity(item: CommunityDao){
        firebaseRepository.deleteCommunity(item).addOnFailureListener {
            Log.e(TAG,"Failed to delete Address")
        }
    }

}