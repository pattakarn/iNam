package com.sungkunn.inam.old_ver.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.model.*
import com.sungkunn.inam.old_ver.fragment.manage.item.*
import com.sungkunn.inam.old_ver.viewholder.VHInfoMenuKT

class RV_Adapter_Manage_List(var items: MutableList<Any>?, var fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
        var itemView = inflater.inflate(R.layout.card_info_menu_new, parent, false)
        return VHInfoMenuKT(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as VHInfoMenuKT
        configureViewHolderMenu(vh, position)
    }


    private fun configureViewHolderMenu(vh: VHInfoMenuKT, position: Int) {
        if (items!!.get(position) is WrapMarket) {
            val data = items!!.get(position) as WrapMarket
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, MarketItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapZone) {
            val data = items!!.get(position) as WrapZone
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, ZoneItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapShop) {
            val data = items!!.get(position) as WrapShop
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, ShopItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapProduct) {
            val data = items!!.get(position) as WrapProduct
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, ProductItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapHotel) {
            val data = items!!.get(position) as WrapHotel
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, HotelItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapTravel) {
            val data = items!!.get(position) as WrapTravel
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, TravelItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapNetwork) {
            val data = items!!.get(position) as WrapNetwork
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, NetworkItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapService) {
            val data = items!!.get(position) as WrapService
            vh.tvTitle.text = data.data.name
            vh.ll.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, ServiceItemFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        }
//        val data = items!!.get(position) as Market
//        vh.ll.dividerDrawable = null
//        vh.tvTitle.text = data.name
//        vh.ll.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
////                var intent = Intent(inflater.context, ManageActivity::class.java)
////                var bundle = Bundle()
////                bundle.putString("category", listField.get(position))
////                intent.putExtra("category", items.get(position))
////                inflater.context.startActivity(intent)
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_manage, items.get(position).fragment!!)
//                    .addToBackStack(null)
//                    .commit()
//
//            }
//        })

    }

}