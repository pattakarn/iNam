package com.sungkunn.inam.new_design.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.*
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.StockLogDao
import com.sungkunn.inam.new_design.model.UserDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Stock_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_SubOrder_List

/**
 * A placeholder fragment containing a simple view.
 */
class StockTabFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var stockLogVM: StockLogViewModel
    private lateinit var orderVM: OrderViewModel
    private lateinit var userVM: UserViewModel

    private var productItem: ProductDao? = null
    private var stockLogList: ArrayList<StockLogDao>? = null
    private var userList: ArrayList<UserDao>? = null

    var page_position = 1
    var rv: RecyclerView? = null
    var tv: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getParcelable("item")
        }
        page_position = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stockLogVM = ViewModelProviders.of(this).get(StockLogViewModel::class.java)
        userVM = ViewModelProviders.of(this).get(UserViewModel::class.java)
        orderVM = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_stock_tab, container, false)
        initInstances(rootView)
        pageViewModel.text.observe(this, Observer<String> {
            tv!!.text = it
        })

        when(page_position){
            1 -> {
                orderVM!!.getOrderPackByProductAndStatus(productItem!!.id, "order").observe(this, Observer {
//                Log.d("Show Cart", "===================== " + it.size)
                    var adapter = RV_Adapter_SubOrder_List(it, fragmentManager!!, orderVM!!)
                    val llm = LinearLayoutManager(inflater!!.context)
                    rv!!.setLayoutManager(llm)
                    rv!!.setAdapter(adapter)

                })
            }
            2 -> {
                stockLogVM!!.getStockLogByItem(productItem!!.id).observe(this, Observer {
                    //                Log.d("Show Cart", "===================== " + it.size)
                    stockLogList = it

                    userVM!!.getUserByStockLog(stockLogList!!).observe(this, Observer {
                        userList = it

                        var adapter = RV_Adapter_Stock_List(stockLogList!!, userList!!, fragmentManager!!)
                        val llm = LinearLayoutManager(inflater!!.context)
                        rv!!.setLayoutManager(llm)
                        rv!!.setAdapter(adapter)

                    })

                })
            }
        }
        return rootView
    }

    fun initInstances(rootView: View) {
        tv = rootView.findViewById(R.id.section_label)
        rv = rootView.findViewById(R.id.rv)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(productItem: ProductDao, sectionNumber: Int): StockTabFragment {
            return StockTabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("item", productItem)
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}