package com.sungkunn.inam.new_design.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sungkunn.inam.new_design.model.Place
import com.sungkunn.inam.new_design.model.PlaceDao

class PlaceRepository {

    val TAG = "PLACE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()

    fun addPlace(item: Place): Task<DocumentReference> {
        var documentReference = firestoreDB.collection("places")
        return documentReference.add(item)
    }

    fun savePlace(item: PlaceDao): Task<Void> {
        //var
        var documentReference = firestoreDB.collection("places").document(item.id)
        return documentReference.set(item.data)
    }

    fun getPlaceAll(): CollectionReference {
        var collectionReference = firestoreDB.collection("places")
        return collectionReference
    }

    fun getPlaceByCommunity(communityId: String): Query {
        var queryReference = firestoreDB.collection("places").whereEqualTo("community_id", communityId)
        return queryReference
    }

    fun getPlace(placeId: String): DocumentReference {
        var documentReference = firestoreDB.collection("places").document(placeId)
        return documentReference
    }

    fun deletePlace(item: PlaceDao): Task<Void> {
        var documentReference =  firestoreDB.collection("places").document(item.id)
        return documentReference.delete()
    }
}