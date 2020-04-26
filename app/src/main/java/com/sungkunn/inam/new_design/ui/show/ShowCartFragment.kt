package com.sungkunn.inam.new_design.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.OrderViewModel
import com.sungkunn.inam.new_design.firestore.ProductOrderViewModel
import com.sungkunn.inam.new_design.firestore.ProductViewModel
import com.sungkunn.inam.new_design.model.OrderDao
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.ProductOrderDao
import com.sungkunn.inam.new_design.model.ProductPackDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Order_List

class ShowCartFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            ShowCartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            productItem = it.getParcelable("item")
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
    }

    private var orderVM: OrderViewModel? = null
    private var productOrderVM: ProductOrderViewModel? = null
    private var productVM: ProductViewModel? = null
    private var orderItem: OrderDao? = null
    private var productOrderItem: ProductOrderDao? = null
    private var productOrderList: ArrayList<ProductOrderDao>? = null
    private var productList: ArrayList<ProductPackDao>? = null
    private lateinit var adapter: RV_Adapter_Order_List
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    var inflater: LayoutInflater? = null
    var totalPrice: Int = 0

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null
    var rv: RecyclerView? = null

    // ------------------- Cart -------------------
    var tvTotal: TextView? = null
    var btnPay: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productOrderVM =
            ViewModelProviders.of(this).get(ProductOrderViewModel::class.java)
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        var rootView = inflater.inflate(R.layout.show_cart_fragment, container, false)
        this.inflater = inflater
        init(rootView)

        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        tvToolbar!!.setText("My Cart")

        if (currentUser != null) {
            productOrderVM!!.getProductOrderByStatus(currentUser!!.uid, "waiting").observe(this, Observer {
//                Log.d("Show Cart", "===================== " + it.size)
                productOrderList = it


                productVM!!.getProductPackByOrder(it!!).observe(this, Observer {
                    productList = it

                    adapter = RV_Adapter_Order_List(productOrderList!!, productList!!, fragmentManager!!, productOrderVM!!)
                    val llm = LinearLayoutManager(inflater!!.context)
                    rv!!.setLayoutManager(llm)
                    rv!!.setAdapter(adapter)

                    totalPrice = 0
                    for(temp in productOrderList!!){
                        totalPrice += temp.data.total_price!!.toInt()
                    }
                    tvTotal!!.text = "Total : ฿" + totalPrice.toString()

                })



            })

        }

        return rootView
    }

    fun init(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)
        rv = rootView.findViewById(R.id.rv)

        // ------------------- Cart -------------------
        tvTotal = rootView.findViewById(R.id.tv_cart_total)
        btnPay = rootView.findViewById(R.id.btn_cart_pay)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when(v){
//            btnNamePlace -> {
//                val intent = Intent(inflater!!.context, ShowPlaceActivity::class.java).apply {
//                    putExtra("item", placeItem)
//                }
////                val intent = Intent(inflater.context, PlaceShowActivity::class.java)
//                inflater!!.context.startActivity(intent)
//            }
            else -> {
                if (fragmentManager!!.backStackEntryCount > 0) {
                    fragmentManager!!.popBackStack()
                } else {
                    activity!!.finish()
                }
            }
        }
    }

}
