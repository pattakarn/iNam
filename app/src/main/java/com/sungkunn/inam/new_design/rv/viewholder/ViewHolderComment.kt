package com.sungkunn.inam.new_design.rv.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_show_comment.view.*
import kotlinx.android.synthetic.main.card_show_place.view.iv
import kotlinx.android.synthetic.main.card_show_place.view.tv_detail

class ViewHolderComment(view: View) : RecyclerView.ViewHolder(view) {

    var ll = view.ll
    var iv = view.iv
    var tv_title = view.tv_title
    var rb = view.rating
    var tv_detail = view.tv_detail

}
