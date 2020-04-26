package com.sungkunn.inam.new_design.rv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.*
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.UserDao
import com.sungkunn.inam.new_design.rv.viewholder.ShowManageViewHolder
import java.util.*


class RV_Adapter_Manage_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<Any>? = null
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<Any>()
    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor (items: Any, fragmentManager: FragmentManager) : this() {
        this.items = items as ArrayList<Any>
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_show_manage, parent, false)
        return ShowManageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ShowManageViewHolder
        configureViewHolderManage(vh, position)
    }

    private fun configureViewHolderManage(vh1: ShowManageViewHolder, position: Int) {
//        vh1.iv.setImageResource(items!!.get(position).image)
        when (items!!.get(position)) {
            is CommunityDao -> {
                var temp = items!!.get(position) as CommunityDao
                vh1.tv_name.text = temp.data.community_name
                vh1.chip_type.text = temp.data.type
                vh1.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, NewEditCommunityActivity::class.java).apply {
                                putExtra("item", temp)
                            }
                        inflater.context.startActivity(intent)
                    }
                })
            }
            is PlaceDao -> {
                var temp = items!!.get(position) as PlaceDao
                vh1.tv_name.text = temp.data.name
                vh1.chip_type.text = temp.data.type
                vh1.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, NewEditPlaceActivity::class.java).apply {
                                putExtra("item", temp)
                            }
                        inflater.context.startActivity(intent)
                    }
                })
            }
            is ProductDao -> {
                var temp = items!!.get(position) as ProductDao
                vh1.tv_name.text = temp.data.name
                vh1.chip_type.text = temp.data.type
                vh1.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, NewEditProductActivity::class.java).apply {
                                putExtra("item", temp)
                            }
                        inflater.context.startActivity(intent)
                    }
                })
                vh1.ib_stock.visibility = View.VISIBLE
                vh1.ib_stock.setOnClickListener(object: View.OnClickListener{
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, StockManageActivity::class.java).apply {
                                putExtra("item", temp)
                            }
                        inflater.context.startActivity(intent)
                    }
                })
            }
            is UserDao -> {
                var temp = items!!.get(position) as UserDao
                vh1.tv_name.text = temp.data.email
//                vh1.chip_type.text = temp.data.type
                vh1.chip_type.visibility = View.INVISIBLE
                vh1.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, NewEditUserActivity::class.java).apply {
                                putExtra("item", temp)
                            }
                        inflater.context.startActivity(intent)
                    }
                })
            }
        }


//        vh1.tv_detail.text = items!!.get(position).detail
//        vh1.chip_type.text = items!!.get(position).data.type.capitalize()
//        when(items!!.get(position).type){
//            "travel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipTravel))
//            "hostel" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipHostel))
//            "cafe" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipCafe))
//            "shop" -> vh1.chip_type.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(inflater.context, R.color.chipShop))
//        }

//        Log.d("RV", arrList.toString())

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
//                val intent = Intent(inflater.context, ShowPlaceActivity::class.java).apply {
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
//                if (wp.data.name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.type.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    items!!.add(wp)
//                }
                when (wp) {
                    is CommunityDao -> {
                        var temp = wp as CommunityDao
                        if (temp.data.community_name!!.toLowerCase(Locale.getDefault()).contains(
                                charText
                            )
                        ) {
                            items!!.add(wp)
                        }
                    }
                    is PlaceDao -> {
                        var temp = wp as PlaceDao
                        if (temp.data.name!!.toLowerCase(Locale.getDefault()).contains(
                                charText
                            )
                        ) {
                            items!!.add(wp)
                        }
                    }
                }
            }
        }
        notifyDataSetChanged()
    }


}