package com.sungkunn.inam.new_design.rv.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.StockLogDao
import com.sungkunn.inam.new_design.model.UserDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderStockLog
import java.util.*


class RV_Adapter_Stock_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<StockLogDao>? = null
    var userList: ArrayList<UserDao> = ArrayList()
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<StockLogDao>()
    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor(items: ArrayList<StockLogDao>, userList: ArrayList<UserDao>,fragmentManager: FragmentManager) : this() {
        this.items = items
        this.userList = userList
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_show_stocklog, parent, false)
        return ViewHolderStockLog(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderStockLog
        configureViewHolder(vh, position)
    }

    private fun configureViewHolder(vh1: ViewHolderStockLog, position: Int) {
//        vh1.iv.setImageResource(items!!.get(position).image)
        if (items!!.get(position).data.action == "reduce"){
            vh1.tv_quantity.setTextColor(Color.RED)
        } else {
            vh1.tv_quantity.setTextColor(Color.GREEN)
        }
        vh1.tv_quantity.text = items!!.get(position).data.quantity
        vh1.tv_detail.text = items!!.get(position).data.action
//        vh1.tv_user.text = items!!.get(position).data.user_id
        vh1.tv_date.text = items!!.get(position).data.created_datetime

        var userItem = userList.filter { it.id.equals(items!!.get(position).data.user_id) }
        if (userItem.size != 0) {
            vh1.tv_user.text = userItem!!.get(0).data.firstname + " " + userItem!!.get(0).data.lastname
        }


//        when(items!!.get(position).type){
//            "travel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipTravel))
//            "hostel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipHostel))
//            "cafe" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipCafe))
//            "shop" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipShop))
//        }

//        Log.d("RV", arrList.toString())



    }

//    fun filter(charText: String?){
//        var charText = charText
//        charText = charText!!.toLowerCase(Locale.getDefault())
//
//        items!!.clear()
//        if (charText.length == 0) {
//            items!!.addAll(arrList!!)
//        } else {
//            for (wp in arrList!!) {
////                Log.d("Name", wp.name)
//                if (wp.data.community_name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.data.type!!.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    items!!.add(wp)
//                }
//            }
//        }
//        notifyDataSetChanged()
//    }


}