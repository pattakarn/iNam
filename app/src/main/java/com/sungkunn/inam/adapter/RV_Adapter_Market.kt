package com.sungkunn.inam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.db.Market
import com.sungkunn.inam.viewholder.ViewHolderMenuCardView

class RV_Adapter_Market(var items: MutableList<Market>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
        val itemView = inflater.inflate(R.layout.card_market_list, parent, false)
        return ViewHolderMenuCardView(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderMenuCardView
        configureViewHolderMenuCardView(vh, position)
    }

    private fun configureViewHolderMenuCardView(vh: ViewHolderMenuCardView, position: Int) {
        val data = items!![position] as Market
        vh.tvTitle.text = data.name
    }

}