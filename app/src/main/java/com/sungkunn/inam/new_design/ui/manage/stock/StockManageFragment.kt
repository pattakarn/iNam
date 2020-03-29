package com.sungkunn.inam.new_design.ui.manage.stock

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.istyleglobalnetwork.talatnoi.rv.adapter.RV_Adapter_Stock_List
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.StockLogViewModel
import com.sungkunn.inam.new_design.firestore.StockViewModel
import com.sungkunn.inam.new_design.firestore.UserViewModel
import com.sungkunn.inam.new_design.model.*


class StockManageFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance(productItem: ProductDao?) = StockManageFragment().apply {
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

    private var stockVM: StockViewModel? = null
    private var stockLogVM: StockLogViewModel? = null
    private var userVM: UserViewModel? = null
    private var productItem: ProductDao? = null
    private var stockItem: StockDao? = null
    private var stockLogList: ArrayList<StockLogDao>? = null
    private var userList: ArrayList<UserDao>? = null

    private lateinit var adapter: RV_Adapter_Stock_List
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    var inflater: LayoutInflater? = null
    var TAG = "STOCK FRAGMENT"

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null

    var tvNameProduct: TextView? = null
    var tvQuantity: TextView? = null
    var rvStockLog: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stockVM =
            ViewModelProviders.of(this).get(StockViewModel::class.java)
        stockLogVM =
            ViewModelProviders.of(this).get(StockLogViewModel::class.java)
        userVM =
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        var rootView = inflater.inflate(R.layout.stock_manage_fragment, container, false)
        this.inflater = inflater
        initInstances(rootView)

        toolbar!!.inflateMenu(R.menu.menu_new_manage)
        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        tvNameProduct!!.setText(productItem!!.data.name)

        stockVM!!.getStockByItem(productItem!!.id).observe(this, Observer {
            if (it.size == 0) {
                var item = Stock(productItem!!.id, "0")
                stockVM!!.addStock(item)
            } else {
                stockItem = it.get(0)
                tvQuantity!!.setText(stockItem!!.data.quantity)
            }
        })

        stockLogVM!!.getStockLogByItem(productItem!!.id).observe(this, Observer {
            //                Log.d("Show Cart", "===================== " + it.size)
            stockLogList = it


            userVM!!.getUserByStockLog(stockLogList!!).observe(this, Observer {
                userList = it

                adapter = RV_Adapter_Stock_List(stockLogList!!, userList!!, fragmentManager!!)
                val llm = LinearLayoutManager(inflater!!.context)
                rvStockLog!!.setLayoutManager(llm)
                rvStockLog!!.setAdapter(adapter)

            })



        })

        return rootView
    }

    fun initInstances(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)

        tvNameProduct = rootView.findViewById(R.id.tv_name_product)
        tvQuantity = rootView.findViewById(R.id.tv_qunatity)
        rvStockLog = rootView.findViewById(R.id.rv_stocklog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v) {
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
                var layout_popup: View = inflater!!.inflate(R.layout.dialog_edit_stock, null)
                var radioGroup: RadioGroup = layout_popup.findViewById(R.id.radio_group)
                var radioAdd: RadioButton = layout_popup.findViewById(R.id.radio_add)
                var radioReduce: RadioButton = layout_popup.findViewById(R.id.radio_reduce)
                var etQuantity: EditText = layout_popup.findViewById(R.id.et_quantity)
                val builder = AlertDialog.Builder(inflater!!.context)
                builder.setView(layout_popup)
                    .setTitle("Edit Stock")
//                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->

                        var stockQuantity = stockItem!!.data.quantity!!.toInt()
                        var actionQuantity = etQuantity.text.toString().toInt()
                        var sumQuantity = 0

                        var stockLogItem: StockLog = StockLog()
                        stockLogItem.item_id = productItem!!.id
                        stockLogItem.user_id = currentUser!!.uid
                        stockLogItem.quantity = actionQuantity.toString()
                        if (radioAdd.isChecked){
                            sumQuantity = stockQuantity + actionQuantity
                            stockLogItem.action = "add"
                        } else if (radioReduce.isChecked){
                            sumQuantity = stockQuantity - actionQuantity
                            stockLogItem.action = "reduce"
                        }

                        stockItem!!.data.quantity = sumQuantity.toString()

                        stockVM!!.saveStock(stockItem!!)
                        stockLogVM!!.addStockLog(stockLogItem)


//                        var rating = ratingBar.rating
//                        var comment = tvComment.text.toString()
//                        var commentItem = Comment(
//                            communityItem!!.id,
//                            currentUser!!.uid,
//                            comment,
//                            rating.toString()
//                        )
//                        commentVM.addComment(commentItem)
//                        Toast.makeText(
//                            inflater!!.context,
//                            "Comment success.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        productOrderVM!!.deleteProductOrder(items!!.get(position))
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                positiveButton.setTextColor(Color.BLACK)
                negativeButton.setTextColor(Color.BLACK)
            }
        }
        return true
    }

}
