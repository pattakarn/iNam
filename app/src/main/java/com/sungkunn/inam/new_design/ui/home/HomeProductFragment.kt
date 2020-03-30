package com.sungkunn.inam.new_design.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.istyleglobalnetwork.talatnoi.rv.adapter.RV_Adapter_Product_Grid_List
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.ProductViewModel


class HomeProductFragment : Fragment(), SearchView.OnQueryTextListener,
    ChipGroup.OnCheckedChangeListener {

    private lateinit var productVM: ProductViewModel

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null
    var sv: SearchView? = null
    var chip_group: ChipGroup? = null

    var rvProduct: RecyclerView? = null
    var adap_product: RV_Adapter_Product_Grid_List? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_home_product, container, false)
        init(rootView)

        sv!!.setOnQueryTextListener(this)

        productVM.getProductAll().observe(this, Observer {
            adap_product = RV_Adapter_Product_Grid_List(it, fragmentManager!!)

            rvProduct!!.setLayoutManager(GridLayoutManager(inflater.context, 2))
            rvProduct!!.setAdapter(adap_product)
        })

        chip_group!!.setOnCheckedChangeListener(this)

        return rootView
    }

    fun init(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)
        sv = rootView.findViewById(R.id.search)
        chip_group = rootView.findViewById(R.id.chip_group_dashboard)

        rvProduct = rootView.findViewById(R.id.rv)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adap_product!!.filter(newText)
        return false
    }

    override fun onCheckedChanged(p0: ChipGroup?, p1: Int) {
        Log.d("Chip", "========================== " + p1)
        when (p1) {
            6 -> adap_product!!.filter("food")
            7 -> adap_product!!.filter("gift")
            8 -> adap_product!!.filter("room")
            else -> adap_product!!.filter("")
        }
    }
}