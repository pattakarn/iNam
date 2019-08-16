package com.sungkunn.inam.fragment.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.Pager_Adapter_Title
import com.sungkunn.inam.model.Product



class ProductMainFragment : Fragment(), View.OnClickListener {

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var tabs: TabLayout? = null
    var pager: ViewPager? = null

    var dataProduct: Product? = null

    var TAG = "Product Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getString("key")
            name = it.getString("name")
            type = it.getString("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_product_main, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        tabs = rootView.findViewById(R.id.tabs)
        pager = rootView.findViewById(R.id.pager_shop)

        toolbar!!.setTitle(name)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)

        val adapter = Pager_Adapter_Title(this!!.fragmentManager!!)
        tabs!!.setupWithViewPager(pager)
        adapter.addFragment(ProductInfoFragment.newInstance(key, name, type), "Info")

        pager!!.adapter = adapter
        pager!!.setOnTouchListener(View.OnTouchListener { v, event -> true })

        return rootView
    }

    override fun onClick(v: View?) {
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            ProductMainFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
