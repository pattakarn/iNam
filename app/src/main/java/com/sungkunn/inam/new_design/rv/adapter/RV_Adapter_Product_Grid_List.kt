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
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderShowPlace
import java.util.*


class RV_Adapter_Product_Grid_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<ProductDao>? = null
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<ProductDao>()
    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor(items: ArrayList<ProductDao>, fragmentManager: FragmentManager) : this() {
        this.items = items
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_show_grid, parent, false)
        return ViewHolderShowPlace(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderShowPlace
        configureViewHolderManageMarket(vh, position)
    }

    private fun configureViewHolderManageMarket(vh1: ViewHolderShowPlace, position: Int) {
//        vh1.iv.setImageResource(items!!.get(position).image)
        vh1.tv_name.text = items!!.get(position).data.name
        vh1.chip_type.text = items!!.get(position).data.type!!.capitalize()
        Glide.with(inflater.context)
            .load(items!!.get(position).data.image_url)
            .placeholder(R.drawable.inam_logo)
            .into(vh1.iv!!)
//        when(items!!.get(position).type){
//            "travel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipTravel))
//            "hostel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipHostel))
//            "cafe" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipCafe))
//            "shop" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipShop))
//        }

//        Log.d("RV", arrList.toString())

        vh1.iv.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PlaceItemActivity::class.java)
//                val bundle = Bundle()
//                bundle.putString("item", items.get(position))
//                inflater.context.startActivity(intent)
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.nav_host_fragment, ShowPlaceFragment.newInstance(items!!.get(position)))
//                    .addToBackStack(null)
//                    .commit()
                val intent = Intent(inflater.context, ShowProductActivity::class.java).apply {
                    putExtra("item", items!!.get(position))
                }
//                val intent = Intent(inflater.context, PlaceShowActivity::class.java)
                inflater.context.startActivity(intent)

            }
        })

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
                if (wp.data.name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.data.type!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items!!.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }


}