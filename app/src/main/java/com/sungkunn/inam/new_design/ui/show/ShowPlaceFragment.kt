package com.sungkunn.inam.new_design.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.istyleglobalnetwork.talatnoi.rv.adapter.RV_Adapter_Product_Hori_List
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.ProductViewModel
import com.sungkunn.inam.new_design.model.PlaceDao

class ShowPlaceFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(placeItem: PlaceDao?) = ShowPlaceFragment().apply {
            arguments = Bundle().apply {
                putParcelable("item", placeItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeItem = it.getParcelable("item")
        }
    }

    private lateinit var productVM: ProductViewModel
    private var placeItem: PlaceDao? = null

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null

    var rvProduct: RecyclerView? = null
    var adap_product: RV_Adapter_Product_Hori_List? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val rootView = inflater.inflate(R.layout.show_place_fragment, container, false)

        init(rootView)

        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        tvToolbar!!.setText(placeItem!!.data.name)

        productVM.getProductAll().observe(this, Observer {
            adap_product = RV_Adapter_Product_Hori_List(it, fragmentManager!!)
            val llm = LinearLayoutManager(inflater!!.context, LinearLayoutManager.HORIZONTAL, false)

            rvProduct!!.setLayoutManager(llm)
            rvProduct!!.setAdapter(adap_product)
        })


        return rootView
    }

    fun init(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)

        rvProduct = rootView.findViewById(R.id.rv_product)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

}
