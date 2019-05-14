package com.sungkunn.inam.fragment.manage.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.db.*

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
class ShopItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var shopItem: WrapShop? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null
    var mMarket: ArrayList<String>? = null
    var zoneList: MutableList<WrapZone>? = null
    var mZone: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var spinZone: Spinner? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etLine: TextInputEditText? = null
    var etFacebook: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    var TAG = "Shop Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shopItem = it.getParcelable("shopItem")
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
        var rootView = inflater.inflate(R.layout.fragment_shop_item, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        spinMarket = rootView.findViewById(R.id.spin_market)
        spinZone = rootView.findViewById(R.id.spin_zone)
        etName = rootView.findViewById(R.id.et_name)
        etOwner = rootView.findViewById(R.id.et_owner)
        etPhone = rootView.findViewById(R.id.et_phone)
        etLine = rootView.findViewById(R.id.et_line)
        etFacebook = rootView.findViewById(R.id.et_facebook)
        etEmail = rootView.findViewById(R.id.et_email)


        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
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

        return rootView
    }

    fun setShop(){
        shopItem ?: return
        etName!!.text!!.append(shopItem!!.data.name)
        etOwner!!.text!!.append(shopItem!!.data.owner)
        etPhone!!.text!!.append(shopItem!!.data.phone)
        etLine!!.text!!.append(shopItem!!.data.line)
        etFacebook!!.text!!.append(shopItem!!.data.facebook)
        etEmail!!.text!!.append(shopItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(shopItem!!.data.marketId))
        spinZone!!.setSelection(getIndexZone(shopItem!!.data.zoneId))

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
                setShop()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun setZoneSpinner() {
        val adapZone = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mZone)
        spinZone!!.setAdapter(adapZone)
//        spinMarket!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                getZone(marketList!!.get(position).key)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Code to perform some action when nothing is selected
//                if (marketList != null)
//                    getZone(marketList!!.get(0).key)
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
                saveShop()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun saveShop() {
        val snackbar: Snackbar = Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (shopItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var indexZone = spinZone!!.selectedItemPosition
            var temp = Shop(
                etName!!.text!!.toString(),
                etOwner!!.text.toString(),
                etPhone!!.text.toString(),
                etLine!!.text.toString(),
                etFacebook!!.text.toString(),
                etEmail!!.text.toString(),
                marketList!!.get(indexMarket).key,
                zoneList!!.get(indexZone).key
            )
            db.collection("shops").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    shopItem = WrapShop(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            shopItem!!.data.name = etName!!.text!!.toString()
            shopItem!!.data.owner = etOwner!!.text!!.toString()
            shopItem!!.data.phone = etPhone!!.text!!.toString()
            shopItem!!.data.line = etLine!!.text!!.toString()
            shopItem!!.data.facebook = etFacebook!!.text!!.toString()
            shopItem!!.data.email = etEmail!!.text!!.toString()
            db.collection("shops").document(shopItem!!.key)
                .set(shopItem!!.data)
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
        fun newInstance(shopItem: WrapShop?) =
            ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("shopItem", shopItem)
                }
            }
    }

}
