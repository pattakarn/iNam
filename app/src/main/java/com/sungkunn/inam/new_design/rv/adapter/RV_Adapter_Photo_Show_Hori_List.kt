package com.sungkunn.inam.new_design.rv.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.NewEditCommunityActivity
import com.sungkunn.inam.new_design.activity.NewEditPlaceActivity
import com.sungkunn.inam.new_design.activity.NewEditProductActivity
import com.sungkunn.inam.new_design.activity.ShowCommunityActivity
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.model.Photo
import com.sungkunn.inam.new_design.model.PhotoDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderPhoto
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderShowPlace
import java.util.*
import kotlin.collections.ArrayList


class RV_Adapter_Photo_Show_Hori_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<PhotoDao>? = null
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<PhotoDao>()
    var photoVM: PhotoViewModel? = null
    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor(items: ArrayList<PhotoDao>, fragmentManager: FragmentManager, photoVM: PhotoViewModel) : this() {
        this.items = items
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
        this.photoVM = photoVM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_show_photo_hori_2, parent, false)
        return ViewHolderPhoto(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderPhoto
        configureViewHolderManageMarket(vh, position)
    }

    private fun configureViewHolderManageMarket(vh1: ViewHolderPhoto, position: Int) {
        Glide.with(inflater.context)
            .load(items!!.get(position).data.image_url)
            .placeholder(R.drawable.inam_logo)
            .into(vh1.iv!!)
    }

    fun filter(charText: String?) {
        var charText = charText
        charText = charText!!.toLowerCase(Locale.getDefault())

        items!!.clear()
        if (charText.length == 0) {
            items!!.addAll(arrList!!)
        } else {
            for (wp in arrList!!) {
//                Log.d("Name", wp.name)
//                if (wp.data.community_name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.data.type!!.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    items!!.add(wp)
//                }
            }
        }
        notifyDataSetChanged()
    }

}