package com.sungkunn.inam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.db.*
import com.sungkunn.inam.fragment.show.ShowMainFragment
import com.sungkunn.inam.viewholder.ViewHolderHomeMenu

class RV_Adapter_Home_Market(var items: MutableList<Any>?, var fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
        var itemView = inflater.inflate(R.layout.card_show_market_menu, parent, false)
        return ViewHolderHomeMenu(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderHomeMenu
        configureViewHolderManageMarket(vh, position)
    }

    private fun configureViewHolderManageMarket(vh1: ViewHolderHomeMenu, position: Int) {
        if (items!!.get(position) is WrapMarket) {
            val data = items!!.get(position) as WrapMarket
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
                    .addToBackStack(null)
                    .commit()
            }
        } else if (items!!.get(position) is WrapShop) {
            val data = items!!.get(position) as WrapShop
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
//                    .addToBackStack(null)
//                    .commit()
            }
        } else if (items!!.get(position) is WrapHotel) {
            val data = items!!.get(position) as WrapHotel
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
//                    .addToBackStack(null)
//                    .commit()
            }
        } else if (items!!.get(position) is WrapTravel) {
            val data = items!!.get(position) as WrapTravel
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
//                    .addToBackStack(null)
//                    .commit()
            }
        } else if (items!!.get(position) is WrapNetwork) {
            val data = items!!.get(position) as WrapNetwork
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
//                    .addToBackStack(null)
//                    .commit()
            }
        } else if (items!!.get(position) is WrapService) {
            val data = items!!.get(position) as WrapService
            vh1.tv.text = data.data.name
            vh1.iv.setOnClickListener { v ->
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_home, ShowMainFragment.newInstance(data))
//                    .addToBackStack(null)
//                    .commit()
            }
        }


    }


}