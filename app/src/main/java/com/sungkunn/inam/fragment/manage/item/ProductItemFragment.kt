package com.sungkunn.inam.fragment.manage.item

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.model.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MarketItemFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MarketItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProductItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var productItem: WrapProduct? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null
    var mMarket: ArrayList<String>? = null
    var zoneList: MutableList<WrapZone>? = null
    var mZone: ArrayList<String>? = null
    var shopList: MutableList<WrapShop>? = null
    var mShop: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var spinZone: Spinner? = null
    var spinShop: Spinner? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null
    var etType: TextInputEditText? = null
    var etDetail: TextInputEditText? = null
    var etPrice: TextInputEditText? = null


    var btnPhoto: Button? = null

    var TAG = "Product Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getParcelable("productItem")
        }
    }

    override fun onStart() {
        super.onStart()
        getMarket()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_product_item, container, false)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        spinMarket = rootView.findViewById<Spinner>(R.id.spin_market)
        spinZone = rootView.findViewById<Spinner>(R.id.spin_zone)
        spinShop = rootView.findViewById<Spinner>(R.id.spin_shop)

        etName = rootView.findViewById(R.id.et_name)
        etType = rootView.findViewById(R.id.et_type)
        etDetail = rootView.findViewById(R.id.et_detail)
        etPrice = rootView.findViewById(R.id.et_price)

        btnPhoto = rootView.findViewById(R.id.btn_photo)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
                intent.putExtra("key", productItem!!.key)
                intent.putExtra("name", productItem!!.data.name)
                inflater.context.startActivity(intent)
            }
        })
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.marketItemContainer, MarketItemPreferenceFragment())
//            .addToBackStack(null)
//            .commit()
//        ArrayAdapter.createFromResource(
//            inflater.context,
//            R.array.more_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinMarket.adapter = adapter
//        }
//
//        ArrayAdapter.createFromResource(
//            inflater.context,
//            R.array.more_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinZone.adapter = adapter
//        }
//
//        ArrayAdapter.createFromResource(
//            inflater.context,
//            R.array.more_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinShop.adapter = adapter
//        }

        return rootView
    }

    fun setProduct(){
        productItem ?: return
        etName!!.text!!.append(productItem!!.data.name)
        etType!!.text!!.append(productItem!!.data.type)
        etDetail!!.text!!.append(productItem!!.data.detail)
        etPrice!!.text!!.append(productItem!!.data.price)
        spinMarket!!.setSelection(getIndexMarket(productItem!!.data.marketId))
        spinZone!!.setSelection(getIndexZone(productItem!!.data.zoneId))
        spinShop!!.setSelection(getIndexShop(productItem!!.data.shopId))

    }

    fun getIndexMarket(marketId: String?): Int{
        var index = 0
        for (item in this!!.marketList!!){
            if (item.key.equals(marketId)){
                return index
            }
            index++
        }
        return 0
    }

    fun getIndexZone(zoneId: String?): Int{
        var index = 0
        for (item in this!!.zoneList!!){
            if (item.key.equals(zoneId)){
                return index
            }
            index++
        }
        return 0
    }

    fun getIndexShop(shopId: String?): Int{
        var index = 0
        for (item in this!!.shopList!!){
            if (item.key.equals(shopId)){
                return index
            }
            index++
        }
        return 0
    }

    fun getMarket() {
        marketList = ArrayList()
        mMarket = ArrayList()
        db.collection("markets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    marketList!!.add(WrapMarket(document.id, document.toObject(Market::class.java)))
                    mMarket!!.add(document.toObject(Market::class.java).name.toString())
                }
                setMarketSpinner()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun setMarketSpinner() {
        val adapMarket = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mMarket)
        spinMarket!!.setAdapter(adapMarket)
        spinMarket!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                getZone(marketList!!.get(position).key)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                if (marketList != null)
                    getZone(marketList!!.get(0).key)
            }
        }

    }

    fun getZone(marketId: String?) {
        zoneList = ArrayList()
        mZone = ArrayList()
        db.collection("zones")
            .whereEqualTo("marketId", marketId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    zoneList!!.add(WrapZone(document.id, document.toObject(Zone::class.java)))
                    mZone!!.add(document.toObject(Zone::class.java).name.toString())
                }
                setZoneSpinner()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun setZoneSpinner() {
        val adapZone = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mZone)
        spinZone!!.setAdapter(adapZone)
        spinZone!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                getShop(zoneList!!.get(position).key)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                if (zoneList != null)
                    getShop(zoneList!!.get(0).key)
            }
        }

    }

    fun getShop(zoneId: String?) {
        shopList = ArrayList()
        mShop = ArrayList()
        db.collection("shops")
            .whereEqualTo("zoneId", zoneId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    shopList!!.add(WrapShop(document.id, document.toObject(Shop::class.java)))
                    mShop!!.add(document.toObject(Shop::class.java).name.toString())
                }
                setShopSpinner()
                setProduct()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun setShopSpinner() {
        val adapShop = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mShop)
        spinShop!!.setAdapter(adapShop)
//        spinZone!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                getZone(zoneList!!.get(position).key)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Code to perform some action when nothing is selected
//                if (zoneList != null)
//                    getZone(zoneList!!.get(0).key)
//            }
//        }

    }

    override fun onClick(v: View?) {
        //fragmentManager!!.popBackStack()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save ->
                saveProduct()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun saveProduct() {
        val snackbar: Snackbar = Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (productItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var indexZone = spinZone!!.selectedItemPosition
            var indexShop = spinShop!!.selectedItemPosition
            var temp = Product(
                etName!!.text!!.toString(),
                etType!!.text.toString(),
                etDetail!!.text.toString(),
                etPrice!!.text.toString(),
                marketList!!.get(indexMarket).key,
                zoneList!!.get(indexZone).key,
                shopList!!.get(indexShop).key
            )
            db.collection("products").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    productItem = WrapProduct(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            productItem!!.data.name = etName!!.text!!.toString()
            productItem!!.data.type = etType!!.text!!.toString()
            productItem!!.data.detail = etDetail!!.text!!.toString()
            productItem!!.data.price = etPrice!!.text!!.toString()
            db.collection("products").document(productItem!!.key)
                .set(productItem!!.data)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(productItem: WrapProduct?) =
            ProductItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("productItem", productItem)
                }
            }
    }

}
