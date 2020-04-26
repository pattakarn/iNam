package com.sungkunn.inam.old_ver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoMenuKT
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoTitleKT
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.model.MapMenu

class RV_Adapter_Manage_Menu(var items: ArrayList<MapMenu>, var fragmentManager: FragmentManager) :
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
        if (items.get(position).type.equals("title")) {
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
//            1 -> {
//                val vh2 = holder as ViewHolderProfileDetailKT
//                configureViewHolderProfile(vh2, position)
//            }
//            2 -> {
//                val vh3 = holder as ViewHolderProfileDetailKT
//                configureViewHolderPersonal(vh3, position)
//            }
//            3 -> {
//                val vh4 = holder as ViewHolderProfileDetailKT
//                configureViewHolderEducation(vh4, position)
//            }
//            4 -> {
//                val vh5 = holder as ViewHolderProfileDetailKT
//                configureViewHolderExperience(vh5, position)
//            }
            else -> {
                val vh = holder as VHInfoMenuKT
                configureViewHolderMenu(vh, position)
            }
        }
    }

    private fun configureViewHolderTitle(vh: VHInfoTitleKT, position: Int) {
//        vh.llTitle.dividerDrawable = inflater.context.resources.getDrawable(R.drawable.divider_drawable_1)
//        vh.llTitle.showDividers = LinearLayout.SHOW_DIVIDER_BEGINNING
        vh.tvTitle.text = items.get(position).name
    }

    private fun configureViewHolderMenu(vh: VHInfoMenuKT, position: Int) {
        vh.ll.dividerDrawable = null
//        vh.ivMenu.setImageResource(R.drawable.ic_dashboard_black_24dp)
//        Log.d("RV INFO", "========================== " + listField.get(position).first() + "  ===   " + listField.get(position))
//        if (items.get(position).equals("Setting")) {
////            vh.ll.dividerDrawable = inflater.context.resources.getDrawable(R.drawable.divider_drawable_4)
//            vh.tvTitle.text = items.get(position)
//            vh.ll.showDividers = LinearLayout.SHOW_DIVIDER_END
////            vh.tvTitle.text = items.get(position).drop(1)
//        } else {
//            vh.tvTitle.text = items.get(position)
//        }

        vh.tvTitle.text = items.get(position).name
        vh.ll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, ManageActivity::class.java)
//                var bundle = Bundle()
//                bundle.putString("category", listField.get(position))
//                intent.putExtra("category", items.get(position))
//                inflater.context.startActivity(intent)
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, items.get(position).fragment!!)
                    .addToBackStack(null)
                    .commit()

            }
        })

//        when (position) {
//            4, 14, 16, 24, 29, 37, 39, 43, 45, 47, 49 -> {
//                vh.ll.dividerDrawable = inflater.context.resources.getDrawable(R.drawable.divider_drawable_1)
//                vh.ll.showDividers = LinearLayout.SHOW_DIVIDER_END
//            }
//        }
    }

}