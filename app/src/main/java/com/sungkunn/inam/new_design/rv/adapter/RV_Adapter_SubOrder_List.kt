package com.sungkunn.inam.new_design.rv.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.OrderViewModel
import com.sungkunn.inam.new_design.model.*
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderCartOrder


class RV_Adapter_SubOrder_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<OrderPackDao>? = null
    var fragmentManager: FragmentManager? = null
    var arrList = ArrayList<OrderPackDao>()
    private var orderVM: OrderViewModel? = null
    lateinit var inflater: LayoutInflater

    constructor(
        items: ArrayList<OrderPackDao>,
        fragmentManager: FragmentManager,
        orderVM: OrderViewModel
    ) : this() {
        this.items = items
        this.arrList!!.addAll(items)
        this.fragmentManager = fragmentManager
        this.orderVM = orderVM

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
        var itemView = inflater.inflate(R.layout.card_show_order, parent, false)
        return ViewHolderCartOrder(itemView)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolderCartOrder
        configureViewHolder(vh, position)
    }

    private fun configureViewHolder(vh1: ViewHolderCartOrder, position: Int) {
//        vh1.iv.setImageResource(items!!.get(position).image)
//        vh1.tv_name.text = items!!.get(position).data.product_id
        vh1.tv_name.text = items!!.get(position).product.data.name
        vh1.tv_detail.text = "฿" + items!!.get(position).product.data.price
        if (items!!.get(position).product.photo != null) {
            Glide.with(inflater.context)
                .load(items!!.get(position).product.photo.get(0).data.image_url)
                .placeholder(R.drawable.inam_logo)
                .into(vh1.iv!!)
        }

        vh1.tv_quantity.text = items!!.get(position).data.quantity

//        Log.d("RV", arrList.toString())
        vh1.btn_down.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                var quantity = items!!.get(position).data.quantity!!.toInt()
                if (quantity > 0) {
                    quantity -= 1
                    items!!.get(position).data.quantity = quantity.toString()
                    vh1.tv_quantity.text = items!!.get(position).data.quantity


                    var productPrice = items!!.get(position).product.data.price!!.toInt()
                    items!!.get(position).data.total_price = (productPrice * quantity).toString()
                    orderVM!!.saveOrder(OrderDao(items!!.get(position).id, items!!.get(position).data))
                }

            }
        })

        vh1.btn_up.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                var quantity = items!!.get(position).data.quantity!!.toInt()
                quantity += 1
                items!!.get(position).data.quantity = quantity.toString()
                vh1.tv_quantity.text = items!!.get(position).data.quantity

                var productPrice = items!!.get(position).product.data.price!!.toInt()
                items!!.get(position).data.total_price = (productPrice * quantity).toString()
                orderVM!!.saveOrder(OrderDao(items!!.get(position).id, items!!.get(position).data))
            }
        })

        vh1.btn_remove.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val builder = AlertDialog.Builder(inflater!!.context)
                builder.setTitle("Delete order")
                    .setMessage("Are you sure ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        orderVM!!.deleteOrder(OrderDao(items!!.get(position).id, items!!.get(position).data))
                    }
                    .setNegativeButton("Cancel") {dialog, which ->
                        dialog.cancel()
                    }

                builder.setMessage("Are you sure ?")
                val dialog: AlertDialog = builder.create()
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                dialog.show()
                val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                positiveButton.setTextColor(
                    ContextCompat.getColor(
                    inflater!!.context,
                    R.color.colorSecondary
                ))
                negativeButton.setTextColor(
                    ContextCompat.getColor(
                    inflater!!.context,
                    R.color.colorSecondary
                ))
                
            }
        })

        vh1.iv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PlaceItemActivity::class.java)
//                val bundle = Bundle()
//                bundle.putString("item", items.get(position))
//                inflater.context.startActivity(intent)
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.nav_host_fragment, ShowPlaceFragment.newInstance(items!!.get(position)))
//                    .addToBackStack(null)
//                    .commit()
//                val intent = Intent(inflater.context, ShowCommunityActivity::class.java).apply {
//                    putExtra("item", items!!.get(position))
//                }
//                val intent = Intent(inflater.context, PlaceShowActivity::class.java)
//                inflater.context.startActivity(intent)

            }
        })

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
////                if (wp.data.community_name!!.toLowerCase(Locale.getDefault()).contains(charText) || wp.data.type!!.toLowerCase(Locale.getDefault()).contains(charText)) {
////                    items!!.add(wp)
////                }
//            }
//        }
//        notifyDataSetChanged()
//    }


}