package com.sungkunn.inam.fragment.manage.item

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.fragment.home.PreviewFragment
import com.sungkunn.inam.model.Hotel
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.WrapHotel
import com.sungkunn.inam.model.WrapMarket

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
class HotelItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var hotelItem: WrapHotel? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null

    var mMarket: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etLine: TextInputEditText? = null
    var etFacebook: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    var btnPhoto: Button? = null

    var TAG = "Hotel Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hotelItem = it.getParcelable("hotelItem")
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
        var rootView = inflater.inflate(R.layout.fragment_hotel_item, container, false)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        spinMarket = rootView.findViewById(R.id.spin_market)
        etName = rootView.findViewById(R.id.et_name)
        etOwner = rootView.findViewById(R.id.et_owner)
        etPhone = rootView.findViewById(R.id.et_phone)
        etLine = rootView.findViewById(R.id.et_line)
        etFacebook = rootView.findViewById(R.id.et_facebook)
        etEmail = rootView.findViewById(R.id.et_email)

        btnPhoto = rootView.findViewById(R.id.btn_photo)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
                intent.putExtra("key", hotelItem!!.key)
                intent.putExtra("name", hotelItem!!.data.name)
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


        return rootView
    }

    fun setHotel() {

        hotelItem ?: return
        etName!!.setText(hotelItem!!.data.name)
        etOwner!!.setText(hotelItem!!.data.owner)
        etPhone!!.setText(hotelItem!!.data.phone)
        etLine!!.setText(hotelItem!!.data.line)
        etFacebook!!.setText(hotelItem!!.data.facebook)
        etEmail!!.setText(hotelItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(hotelItem!!.data.marketId))

    }

    fun getIndexMarket(marketId: String?): Int {
        var index = 0
        for (item in this!!.marketList!!) {
            if (item.key.equals(marketId)) {
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
                setSpinner()
                setHotel()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun setSpinner() {
        val adapMarket = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mMarket)
        spinMarket!!.setAdapter(adapMarket)
//        spinMarket!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Code to perform some action when nothing is selected
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
            R.id.action_preview ->
                getPreview()
            R.id.action_save ->
                saveHotel()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun getPreview() {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container_manage, PreviewFragment.newInstance(hotelItem!!.key, hotelItem!!.data.name.toString(), "hotel"))
            .addToBackStack(null)
            .commit()
    }

    private fun saveHotel() {
        val snackbar: Snackbar =
            Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (hotelItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var temp = Hotel(
                etName!!.text!!.toString(),
                etOwner!!.text.toString(),
                etPhone!!.text.toString(),
                etLine!!.text.toString(),
                etFacebook!!.text.toString(),
                etEmail!!.text.toString(),
                marketList!!.get(indexMarket).key
            )
            db.collection("hotels").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    hotelItem = WrapHotel(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            hotelItem!!.data.name = etName!!.text!!.toString()
            hotelItem!!.data.owner = etOwner!!.text!!.toString()
            hotelItem!!.data.phone = etPhone!!.text!!.toString()
            hotelItem!!.data.line = etLine!!.text!!.toString()
            hotelItem!!.data.facebook = etFacebook!!.text!!.toString()
            hotelItem!!.data.email = etEmail!!.text!!.toString()
            db.collection("hotels").document(hotelItem!!.key)
                .set(hotelItem!!.data)
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
        fun newInstance(hotelItem: WrapHotel?) =
            HotelItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("hotelItem", hotelItem)
                }
            }
    }

}
