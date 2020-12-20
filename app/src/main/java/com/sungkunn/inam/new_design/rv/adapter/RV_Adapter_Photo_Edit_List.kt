package com.sungkunn.inam.new_design.rv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.model.PhotoDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderPhoto
import java.util.*
import kotlin.collections.ArrayList


class RV_Adapter_Photo_Edit_List() :
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
        var itemView = inflater.inflate(R.layout.card_show_photo, parent, false)
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
//        vh1.iv.setImageResource(items!!.get(position).data.image_url)
//        vh1.iv.text = items!!.get(position).data.community_name
//        vh1.tv_detail.text = items!!.get(position).data.community_name
//        vh1.chip_type.text = items!!.get(position).data.type!!.capitalize()
//        Log.d("Adap Community", "==================== " + items!!.get(position).data.image_url)
        Glide.with(inflater.context)
            .load(items!!.get(position).data.image_url)
            .placeholder(R.drawable.inam_logo)
            .into(vh1.iv!!)
        if (items!!.get(position).data.status.equals("main")) {
//            vh1.iv_flag.setImageResource(R.drawable.ic_flag_black)
            vh1.iv_flag.visibility = View.VISIBLE
        }

        vh1.cv.setOnLongClickListener {
            val builder = AlertDialog.Builder(inflater!!.context)
            var listItem = arrayOf("Set profile", "Delete")
            builder.setItems(listItem) { _, which ->
                val selected = listItem[which]
//                    Toast.makeText(inflater!!.context,"===== " + selected + " ====",Toast.LENGTH_SHORT).show()
                when (selected) {
                    "Set profile" -> {
                        var list: ArrayList<PhotoDao> = ArrayList()
                        list.addAll(items!!.filter { it.data.status.equals("main") })

                        for (i in list){
                            i.data.status = ""
                            photoVM!!.savePhoto(i)
                        }

                        items!!.get(position).data.status = "main"
                        photoVM!!.savePhoto(items!!.get(position))
//                    var intent =
//                        Intent(inflater!!.context, NewEditCommunityActivity::class.java)
//                    inflater!!.context.startActivity(intent)
                    }
                    "Delete" -> {
//                        Toast.makeText(inflater!!.context, "Done ${items!!.get(position).id}" , Toast.LENGTH_SHORT).show()
                        val storageRef = FirebaseStorage.getInstance().reference
                        val photoRef = storageRef.child("photo").child(items!!.get(position).id)
                        photoRef.delete().addOnSuccessListener {
                            photoVM!!.deletePhoto(items!!.get(position))
                            Toast.makeText(inflater!!.context, "Done" , Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            // Uh-oh, an error occurred!
                        }

                    }
                }

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            return@setOnLongClickListener true
        }
////        when(items!!.get(position).type){
////            "travel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipTravel))
////            "hostel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipHostel))
////            "cafe" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipCafe))
////            "shop" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipShop))
////        }
//
////        Log.d("RV", arrList.toString())
//
//        vh1.iv.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
////                var intent = Intent(inflater.context, PlaceItemActivity::class.java)
////                val bundle = Bundle()
////                bundle.putString("item", items.get(position))
////                inflater.context.startActivity(intent)
////                fragmentManager!!.beginTransaction()
////                    .replace(R.id.nav_host_fragment, ShowPlaceFragment.newInstance(items!!.get(position)))
////                    .addToBackStack(null)
////                    .commit()
//                val intent = Intent(inflater.context, ShowCommunityActivity::class.java).apply {
//                    putExtra("item", items!!.get(position))
//                }
////                val intent = Intent(inflater.context, PlaceShowActivity::class.java)
//                inflater.context.startActivity(intent)
//
//            }
//        })

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