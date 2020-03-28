package com.sungkunn.inam.new_design.ui.manage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.NewEditCommunityActivity
import com.sungkunn.inam.new_design.activity.NewEditPlaceActivity
import com.sungkunn.inam.new_design.activity.NewEditProductActivity
import com.sungkunn.inam.new_design.firestore.CommunityViewModel
import com.sungkunn.inam.new_design.firestore.PlaceViewModel
import com.sungkunn.inam.new_design.firestore.ProductViewModel
import com.sungkunn.inam.new_design.rv.viewholder.RV_Adapter_Manage_List


class NewManageFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() =
            NewManageFragment()
    }

    private lateinit var communityVM: CommunityViewModel
    private lateinit var placeVM: PlaceViewModel
    private lateinit var productVM: ProductViewModel
    private lateinit var adapter: RV_Adapter_Manage_List

    var spin: Spinner? = null
    var btnSearch: Button? = null
    var toolbar: Toolbar? = null
    var rv: RecyclerView? = null
    var inflater: LayoutInflater? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communityVM =
            ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        placeVM = ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        productVM = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        var rootView = inflater.inflate(R.layout.new_manage_fragment, container, false)
        this.inflater = inflater

        rv = rootView.findViewById(R.id.rv)
        spin = rootView.findViewById(R.id.spinner)
        btnSearch = rootView.findViewById(R.id.btn_search)
        toolbar = rootView.findViewById(R.id.toolbar)

        toolbar!!.inflateMenu(R.menu.menu_new_manage)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        btnSearch!!.setOnClickListener(this)

        communityVM.getCommunityAll().observe(this, Observer {
            adapter = RV_Adapter_Manage_List(it, fragmentManager!!)
            val llm = LinearLayoutManager(inflater.context)

            rv!!.setLayoutManager(llm)
            rv!!.setAdapter(adapter)
        })
//        placeVM.getPlaceAll().observe(this, Observer {
//            adapter = RV_Adapter_Manage_List(it, fragmentManager!!)
//            val llm = LinearLayoutManager(inflater.context)
//
//            rv!!.setLayoutManager(llm)
//            rv!!.setAdapter(adapter)
//        })

        var listItem = this.resources.getStringArray(com.sungkunn.inam.R.array.new_manage_menu)
        val adapterEnglish: ArrayAdapter<String> = ArrayAdapter<String>(
            inflater.context,
            R.layout.support_simple_spinner_dropdown_item, listItem
        )
        spin!!.setAdapter(adapterEnglish)



        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communityVM = ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        placeVM = ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }

    override fun onClick(v: View?) {
        Log.d("New Manage Fragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when(v){
            btnSearch -> {
                when(spin!!.selectedItem.toString()){
                    "ชุมชน" -> {
                        communityVM.getCommunityAll().observe(this, Observer {
                            adapter = RV_Adapter_Manage_List(it, fragmentManager!!)
                            val llm = LinearLayoutManager(inflater!!.context)

                            rv!!.setLayoutManager(llm)
                            rv!!.setAdapter(adapter)
                        })
                    }
                    "สถานที่" -> {
                        placeVM.getPlaceAll().observe(this, Observer {
                            adapter = RV_Adapter_Manage_List(it, fragmentManager!!)
                            val llm = LinearLayoutManager(inflater!!.context)

                            rv!!.setLayoutManager(llm)
                            rv!!.setAdapter(adapter)
                        })
                    }
                    "สินค้า" -> {
                        productVM.getProductAll().observe(this, Observer {
                            adapter = RV_Adapter_Manage_List(it, fragmentManager!!)
                            val llm = LinearLayoutManager(inflater!!.context)

                            rv!!.setLayoutManager(llm)
                            rv!!.setAdapter(adapter)
                        })
                    }
                }
            }
            else -> {
                if (fragmentManager!!.backStackEntryCount > 0) {
                    fragmentManager!!.popBackStack()
                } else {
                    activity!!.finish()
                }
            }
        }

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_add -> {
//                var intent = Intent(inflater!!.context, NewEditPlaceActivity::class.java)
//                inflater!!.context.startActivity(intent)
                val builder = AlertDialog.Builder(inflater!!.context)
                var listItem = this.resources.getStringArray(R.array.menu_add_manage)
                builder.setItems(listItem) { _, which ->
                    val selected = listItem[which]
//                    Toast.makeText(inflater!!.context,"===== " + selected + " ====",Toast.LENGTH_SHORT).show()
                    when (selected) {
                        "Community" -> {
                            var intent =
                                Intent(inflater!!.context, NewEditCommunityActivity::class.java)
                            inflater!!.context.startActivity(intent)
                        }
                        "Place" -> {
                            var intent =
                                Intent(inflater!!.context, NewEditPlaceActivity::class.java)
                            inflater!!.context.startActivity(intent)
                        }
                        "Product" -> {
                            var intent =
                                Intent(inflater!!.context, NewEditProductActivity::class.java)
                            inflater!!.context.startActivity(intent)
                        }
                    }

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
        return true
    }

}
