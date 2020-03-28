package com.sungkunn.inam.new_design.rv.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_show_order.view.*
import kotlinx.android.synthetic.main.card_show_place.view.iv
import kotlinx.android.synthetic.main.card_show_place.view.tv_detail
import kotlinx.android.synthetic.main.card_show_place.view.tv_name

class ViewHolderCartOrder(view: View) : RecyclerView.ViewHolder(view) {

    var iv = view.iv
    var tv_name = view.tv_name
    var tv_detail = view.tv_detail
    var btn_down = view.btn_down
    var tv_quantity = view.tv_qunatity
    var btn_up = view.btn_up
    var btn_remove = view.btn_remove

}
