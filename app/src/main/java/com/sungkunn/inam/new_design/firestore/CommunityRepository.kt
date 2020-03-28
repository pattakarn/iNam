package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.new_design.model.Community
import com.sungkunn.inam.new_design.model.CommunityDao

class CommunityRepository {

    val TAG = "COMMUNITY_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()

    fun addCommunity(item: Community): Task<DocumentReference> {
        var documentReference = firestoreDB.collection("communities")
        return documentReference.add(item)
    }

    fun saveCommunity(item: CommunityDao): Task<Void> {
        var documentReference = firestoreDB.collection("communities").document(item.id)
        return documentReference.set(item.data)
    }

    fun getCommunityAll(): CollectionReference {
        var collectionReference = firestoreDB.collection("communities")
        return collectionReference
    }

    fun getCommunity(communityId: String): DocumentReference {
        var documentReference = firestoreDB.collection("communities").document(communityId)
        return documentReference
    }

    fun deleteCommunity(item: CommunityDao): Task<Void> {
        var documentReference =  firestoreDB.collection("communities").document(item.id)
        return documentReference.delete()
    }
}