package com.sungkunn.inam.new_design.rv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoMenuKT
import com.istyleglobalnetwork.istyleapplication.istyle.pack_new.viewholder.VHInfoTitleKT
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.NewAccountActivity
import com.sungkunn.inam.new_design.activity.NewManageActivity
import com.sungkunn.inam.new_design.activity.ShowCartActivity
import com.sungkunn.inam.new_design.activity.ShowPlaceActivity
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.model.UserDao
import com.sungkunn.inam.new_design.rv.viewholder.ViewHolderShowPlace
import java.util.*


class RV_Adapter_More_List() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<Any>? = null
    var fragmentManager: FragmentManager? = null
    lateinit var inflater: LayoutInflater
    private lateinit var auth: FirebaseAuth
//    var listField : Array<String> = inflater.context.resources.getStringArray(R.array.information)

    constructor(items: ArrayList<Any>, fragmentManager: FragmentManager) : this() {
        this.items = items
        this.fragmentManager = fragmentManager
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater = LayoutInflater.from(parent.getContext())
//        arrList = items
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
        if (items!!.get(position) is String) {
            var text = items!!.get(position) as String
            if (text.first().equals('*')) {
                return 0
            } else {
                return 1
            }
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
        var text = items!!.get(position) as String
        vh.tvTitle.text = text.drop(1)
    }

    private fun configureViewHolderMenu(vh: VHInfoMenuKT, position: Int) {
        vh.ll.dividerDrawable = null
//        vh.ivMenu.setImageResource(R.drawable.ic_dashboard_black_24dp)
//        Log.d("RV INFO", "========================== " + listField.get(position).first() + "  ===   " + listField.get(position))
        when (items!!.get(position)) {
            is String -> {
                var text = items!!.get(position) as String
                vh.tvTitle.text = text
                vh.ll.showDividers = LinearLayout.SHOW_DIVIDER_END
                vh.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (text.equals("Manage")) {
//                    var intent = Intent(inflater.context, ManageActivity::class.java)
                            var intent = Intent(inflater.context, NewManageActivity::class.java)
                            inflater.context.startActivity(intent)
                        } else if (text.equals("My Cart")) {
                            var intent = Intent(inflater.context, ShowCartActivity::class.java)
//                    var intent = Intent(inflater.context, NewMainActivity::class.java)
                            inflater.context.startActivity(intent)
                        } else if (text.equals("Sign out")) {
                            auth.signOut()
                            Toast.makeText(inflater.context, "Sign out", Toast.LENGTH_SHORT).show()
                        }
//                fragmentManager!!.beginTransaction()
//                    .replace(container, MarketListFragment.newInstance(5))
//                    .addToBackStack(null)
//                    .commit()

                    }
                })

            }
            is UserDao -> {
                var userItem = items!!.get(position) as UserDao
                vh.tvTitle.text = "Account"
                vh.ll.showDividers = LinearLayout.SHOW_DIVIDER_END
                vh.ll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent =
                            Intent(inflater.context, NewAccountActivity::class.java).apply {
                                putExtra("item", userItem)
                            }
                        inflater.context.startActivity(intent)
                    }

                })

            }

        }
    }

}