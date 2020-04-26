package com.sungkunn.inam.new_design.rv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.CommunityDao
import kotlinx.android.synthetic.main.card_show_spin.view.*
import java.util.*

class Spin_Adapter_Community_List(ctx: Context,
                                  dataList: ArrayList<CommunityDao>) :

    ArrayAdapter<CommunityDao>(ctx, 0, dataList) {

    var TAG = "SPIN_ADAPTER"

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val data = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.card_show_spin,
            parent,
            false
        )

        view.tv.text = data.data.community_name
        return view
    }

    fun getPositionItem(id: String): Int {
        for (i in 0..count){
//            Log.d(TAG, "=================== " + getItem(i).key + " -> " + id)
            if (getItem(i).id.equals(id)){
                return i
            }
        }
        return -1
    }


}