package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.StockLogViewModel
import com.sungkunn.inam.new_design.firestore.StockViewModel
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.Stock
import com.sungkunn.inam.new_design.model.StockDao
import com.sungkunn.inam.new_design.model.StockLog
import com.sungkunn.inam.new_design.ui.main.StockPagerAdapter
import com.sungkunn.inam.new_design.ui.manage.stock.StockManageFragment

class StockMainActivity : AppCompatActivity(), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    private lateinit var stockVM: StockViewModel
    private lateinit var stockLogVM: StockLogViewModel

    private var productItem: ProductDao? = null
    private var stockItem: StockDao? = null

    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var tvNameProduct: TextView? = null
    var tvQuantity: TextView? = null
    var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_main)

        var bundle = intent.extras

        if (bundle != null){
            productItem = bundle.getParcelable("item")
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        stockVM = ViewModelProviders.of(this).get(StockViewModel::class.java)
        stockLogVM = ViewModelProviders.of(this).get(StockLogViewModel::class.java)

        initInstances()
        val sectionsPagerAdapter = StockPagerAdapter(this, productItem!!, supportFragmentManager)

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

        viewPager!!.adapter = sectionsPagerAdapter
        tabs!!.setupWithViewPager(viewPager)

        fab!!.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun initInstances() {
        toolbar = findViewById(R.id.toolbar)
        tvToolbar = findViewById(R.id.toolbar_title)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)
        tvNameProduct = findViewById(R.id.tv_name_product)
        tvQuantity = findViewById(R.id.tv_qunatity)
        fab = findViewById(R.id.fab)
    }

    override fun onClick(v: View?) {
        when (v) {
            else -> {
                finish()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_add -> {
//                var intent = Intent(inflater!!.context, NewEditPlaceActivity::class.java)
//                inflater!!.context.startActivity(intent)
                var layout_popup: View = inflate(this, R.layout.dialog_edit_stock, null)
                var radioGroup: RadioGroup = layout_popup.findViewById(R.id.radio_group)
                var radioAdd: RadioButton = layout_popup.findViewById(R.id.radio_add)
                var radioReduce: RadioButton = layout_popup.findViewById(R.id.radio_reduce)
                var etQuantity: EditText = layout_popup.findViewById(R.id.et_quantity)
                val builder = AlertDialog.Builder(this)
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
                positiveButton.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary))
                negativeButton.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary))
            }
        }
        return true
    }
}