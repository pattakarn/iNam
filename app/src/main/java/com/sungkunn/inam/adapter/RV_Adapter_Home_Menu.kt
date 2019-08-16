package com.sungkunn.inam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.example.HomeActivity
import com.sungkunn.inam.viewholder.ViewHolderHomeMenu

class RV_Adapter_Home_Menu(var items: Array<String>, var fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
        var itemView = inflater.inflate(R.layout.card_home_menu, parent, false)
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
        vh1.tv.text = items.get(position)
        vh1.iv.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, HomeActivity::class.java)
                intent.putExtra("category", items.get(position))
                inflater.context.startActivity(intent)
            }
        })

    }


}