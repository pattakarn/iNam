package com.istyleglobalnetwork.talatnoi.rv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.ShowProductActivity
import com.sungkunn.inam.new_design.model.PhotoDao
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderPhotoHori
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderShowPlace
import java.util.*


class RV_Adapter_Photo_Hori_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<PhotoDao>? = null
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<PhotoDao>()
    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor(items: ArrayList<PhotoDao>, fragmentManager: FragmentManager) : this() {
        this.items = items
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_photo_hori, parent, false)
        return ViewHolderPhotoHori(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderPhotoHori
        configureViewHolderPhotoHori(vh, position)
    }

    private fun configureViewHolderPhotoHori(vh1: ViewHolderPhotoHori, position: Int) {
//        vh1.iv.setImageResource(items!!.get(position).image)
//        vh1.tv_name.text = items!!.get(position).data.name
//        vh1.chip_type.text = items!!.get(position).data.type!!.capitalize()
        Glide.with(inflater.context)
            .load(items!!.get(position).data.image_url)
            .placeholder(R.drawable.inam_logo)
            .into(vh1.iv!!)


    }

    fun filter(charText: String?){
        var charText = charText
        charText = charText!!.toLowerCase(Locale.getDefault())

        items!!.clear()
        if (charText.length == 0) {
            items!!.addAll(arrList!!)
        } else {
            for (wp in arrList!!) {
//                Log.d("Name", wp.name)
//                if (wp.data.name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.data.type!!.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    items!!.add(wp)
//                }
            }
        }
        notifyDataSetChanged()
    }


}