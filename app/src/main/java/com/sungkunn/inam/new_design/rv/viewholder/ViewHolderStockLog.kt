package com.sungkunn.inam.new_design.rv.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_show_stocklog.view.*

class ViewHolderStockLog(view: View) : RecyclerView.ViewHolder(view) {

    var ll = view.ll
    var tv_quantity = view.tv_quantity
    var tv_detail = view.tv_detail
    var tv_user = view.tv_user
    var tv_date = view.tv_date

}
