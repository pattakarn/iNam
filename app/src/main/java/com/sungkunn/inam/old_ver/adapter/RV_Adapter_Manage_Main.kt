package com.sungkunn.inam.old_ver.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoMenuKT
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoTitleKT
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.fragment.manage.list.*

class RV_Adapter_Manage_Main(var items: Array<String>, var fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var inflater: LayoutInflater
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
        var viewHolder: RecyclerView.ViewHolder? = null
        var itemView: View
        when (viewType) {
            0 -> {
                itemView = inflater.inflate(R.layout.card_info_title_new, parent, false)
                viewHolder = VHInfoTitleKT(itemView)
            }
            else -> {
                itemView = inflater.inflate(R.layout.card_info_menu_new, parent, false)
                viewHolder = VHInfoMenuKT(itemView)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.get(position).first().equals('*')) {
            return 0
        } else {
            return 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val vh1 = holder as VHInfoTitleKT
                configureViewHolderTitle(vh1, position)
            }
            else -> {
                val vh = holder as VHInfoMenuKT
                configureViewHolderMenu(vh, position)
            }
        }
    }

    private fun configureViewHolderTitle(vh: VHInfoTitleKT, position: Int) {
//        vh.llTitle.dividerDrawable = inflater.context.resources.getDrawable(R.drawable.divider_drawable_1)
//        vh.llTitle.showDividers = LinearLayout.SHOW_DIVIDER_BEGINNING
        vh.tvTitle.text = items.get(position).drop(1)
    }

    private fun configureViewHolderMenu(vh: VHInfoMenuKT, position: Int) {
        vh.ll.dividerDrawable = null
//        vh.ivMenu.setImageResource(R.drawable.ic_dashboard_black_24dp)
//        Log.d("RV INFO", "========================== " + listField.get(position).first() + "  ===   " + listField.get(position))
        if (items.get(position).equals("Setting")) {
//            vh.ll.dividerDrawable = inflater.context.resources.getDrawable(R.drawable.divider_drawable_4)
            vh.tvTitle.text = items.get(position)
            vh.ll.showDividers = LinearLayout.SHOW_DIVIDER_END
//            vh.tvTitle.text = items.get(position).drop(1)
        } else {
            vh.tvTitle.text = items.get(position)
        }
        vh.ll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                try {
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.container_manage, selectFragment(items.get(position)))
                        .addToBackStack(null)
                        .commit()
                } catch (er: Exception){

                }


            }
        })

    }

    private fun selectFragment(name: String) : Fragment {
        Log.d("select fragment", "============ " + name)
        var fragment: Fragment? = null
        when(name) {
            "ตลาด" -> fragment = MarketListFragment.newInstance(1)
            "โซน" ->  fragment = ZoneListFragment.newInstance("", "")
            "ร้านค้า" ->  fragment = ShopListFragment.newInstance()
            "สินค้า" ->  fragment = ProductListFragment.newInstance("", "")
            "สต๊อค" ->  fragment = StockListFragment.newInstance("", "")
            "ที่พัก" ->  fragment = HotelListFragment.newInstance("", "")
            "ห้องพัก" ->  fragment = RoomListFragment.newInstance("", "")
            "จอง" ->  fragment = BookListFragment.newInstance("", "")
            "ท่องเที่ยว" ->  fragment = TravelListFragment.newInstance("", "")
            "เครือข่าย" ->  fragment = NetworkListFragment.newInstance("", "")
            "บริการ" ->  fragment = ServiceListFragment.newInstance("", "")
            else -> fragment = null
        }
        return fragment!!

    }

}