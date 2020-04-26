package com.sungkunn.inam.new_design.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.firestore.ProductViewModel
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.model.PlacePackDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Photo_Show_Hori_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Product_Hori_List

class ShowPlaceFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(placeItem: PlacePackDao?) = ShowPlaceFragment().apply {
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
    private lateinit var photoVM: PhotoViewModel
    private var placeItem: PlacePackDao? = null
    private lateinit var adapterPhoto: RV_Adapter_Photo_Show_Hori_List

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null

    var rvProduct: RecyclerView? = null
    var adap_product: RV_Adapter_Product_Hori_List? = null

    // ----------------- Photo -----------------
    var rvPhoto: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        val rootView = inflater.inflate(R.layout.show_place_fragment, container, false)

        init(rootView)

        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        tvToolbar!!.setText(placeItem!!.data.name)

        photoVM.getPhotoByItem(placeItem!!.id).observe(this, Observer {
            if (it != null) {
                adapterPhoto = RV_Adapter_Photo_Show_Hori_List(it, fragmentManager!!, photoVM)
                val llm = LinearLayoutManager(
                    inflater!!.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvPhoto!!.setLayoutManager(llm)
                rvPhoto!!.setAdapter(adapterPhoto)

            }

        })

        productVM.getProductPackAll().observe(this, Observer {
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

        // ----------------- Photo -----------------
        rvPhoto = rootView.findViewById(R.id.photo_rv)

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
