package com.sungkunn.inam.new_design.ui.show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.ShowCartActivity
import com.sungkunn.inam.new_design.activity.ShowPlaceActivity
import com.sungkunn.inam.new_design.firestore.OrderViewModel
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.firestore.PlaceViewModel
import com.sungkunn.inam.new_design.firestore.ProductOrderViewModel
import com.sungkunn.inam.new_design.model.*
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Photo_Show_Hori_List


class ShowProductFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance(productItem: ProductPackDao?) = ShowProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable("item", productItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getParcelable("item")
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
    }

    private lateinit var placeVM: PlaceViewModel
    private lateinit var orderVM: OrderViewModel
    private lateinit var photoVM: PhotoViewModel
    private lateinit var productOrderVM: ProductOrderViewModel
    private var productItem: ProductPackDao? = null
    private var placeItem: PlacePackDao? = null
    private var productOrderList: ArrayList<ProductOrderDao>? = null
    private var orderItem: ArrayList<OrderDao> = ArrayList()
    var lifecycleOwner: LifecycleOwner? = null
    var observer: Observer<ArrayList<ProductOrderDao>>? = null

    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    var inflater: LayoutInflater? = null
    var TAG = "SHOW PRODUCT FRAGMENT"

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null

    // ----------------- Photo -----------------
    var rvPhoto: RecyclerView? = null

    // -------------------- Row Price --------------------
    var tvPrice: TextView? = null
    var iv1: ImageView? = null
    var iv2: ImageView? = null

    // -------------------- Row Name --------------------
    var tvNameProduct: TextView? = null
    var tvNamePlace: TextView? = null
    var btnNamePlace: Button? = null

    // -------------------- Row Add Cart --------------------
    var btnAddCart: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placeVM =
            ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        orderVM =
            ViewModelProviders.of(this).get(OrderViewModel::class.java)
        productOrderVM =
            ViewModelProviders.of(this).get(ProductOrderViewModel::class.java)
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        var rootView = inflater.inflate(R.layout.show_product_fragment, container, false)
        this.inflater = inflater
        init(rootView)

        toolbar!!.inflateMenu(R.menu.menu_show_product)
        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        tvToolbar!!.setText(productItem!!.data.name)

//        photoVM.getPhotoByItem(productItem!!.id).observe(this, Observer {
//            if (it != null) {
//                var adapterPhoto = RV_Adapter_Photo_Show_Hori_List(it, fragmentManager!!, photoVM)
//                val llm = LinearLayoutManager(
//                    inflater!!.context,
//                    LinearLayoutManager.HORIZONTAL,
//                    false
//                )
//                rvPhoto!!.setLayoutManager(llm)
//                rvPhoto!!.setAdapter(adapterPhoto)
//
//            }
//
//        })

        if (productItem!!.photo != null) {
            var adapterPhoto = RV_Adapter_Photo_Show_Hori_List(productItem!!.photo, fragmentManager!!, photoVM)
            val llm = LinearLayoutManager(
                inflater!!.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvPhoto!!.setLayoutManager(llm)
            rvPhoto!!.setAdapter(adapterPhoto)
        }

        tvPrice!!.setText("฿" + productItem!!.data.price)

        tvNameProduct!!.setText(productItem!!.data.name)
        placeVM!!.getPlacePack(productItem!!.data.place_id!!).observe(this, Observer {
            placeItem = it
            tvNamePlace!!.setText(it.data.name)
        })

        if (currentUser != null) {
            productOrderVM!!.getProductOrderByProduct(
                currentUser!!.uid,
                productItem!!.id,
                "waiting"
            )
                .observe(this, Observer {
                    productOrderList = it
                })
        }


        btnNamePlace!!.setOnClickListener(this)
        btnAddCart!!.setOnClickListener(this)

        return rootView
    }

    fun init(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)

        // ----------------- Photo -----------------
        rvPhoto = rootView.findViewById(R.id.photo_rv)

        // -------------------- Row Price --------------------
        tvPrice = rootView.findViewById(R.id.tv_price_price)
        iv1 = rootView.findViewById(R.id.iv_price_1)
        iv2 = rootView.findViewById(R.id.iv_price_2)

        // -------------------- Row Name --------------------
        tvNameProduct = rootView.findViewById(R.id.tv_name_product)
        tvNamePlace = rootView.findViewById(R.id.tv_name_place)
        btnNamePlace = rootView.findViewById(R.id.btn_name_place)

        // -------------------- Row Add Cart --------------------
        btnAddCart = rootView.findViewById(R.id.btn_cart_add)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v) {
            btnNamePlace -> {
                val intent = Intent(inflater!!.context, ShowPlaceActivity::class.java).apply {
                    putExtra("item", placeItem)
                }
//                val intent = Intent(inflater.context, PlaceShowActivity::class.java)
                inflater!!.context.startActivity(intent)
            }
            btnAddCart -> {
                if (currentUser != null) {
                    if (productOrderList!!.size == 0) {
                        var productOrderItem = ProductOrder(
                            currentUser!!.uid,
                            productItem!!.id,
                            placeItem!!.id,
                            "1",
                            productItem!!.data.price,
                            "waiting",
                            "",
                            ""
                        )
                        productOrderVM!!.addProductOrder(productOrderItem)
                        Toast.makeText(
                            inflater!!.context,
                            "Add " + productItem!!.data.name,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        var productOrderItem = productOrderList!!.get(0)
                        var quantity = productOrderItem.data.quantity!!.toInt() + 1
                        productOrderItem.data.quantity = quantity.toString()
                        var totalPrice = quantity * productItem!!.data.price!!.toInt()
                        productOrderItem.data.total_price = totalPrice.toString()
                        productOrderVM!!.saveProductOrder(productOrderItem)
                        Toast.makeText(
                            inflater!!.context,
                            "Add " + productItem!!.data.name,
                            Toast.LENGTH_SHORT
                        ).show()
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
            R.id.action_cart -> {
                var intent = Intent(inflater!!.context, ShowCartActivity::class.java)
                inflater!!.context.startActivity(intent)
            }
        }
        return true
    }


}
