package com.sungkunn.inam.fragment.manage.item

import android.app.TimePickerDialog
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
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.model.*
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


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
    var inflater: LayoutInflater? = null
    var spinMarket: Spinner? = null
    var spinZone: Spinner? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etLine: TextInputEditText? = null
    var etFacebook: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    var etMondayOpen: TextInputEditText? = null
    var etMondayClose: TextInputEditText? = null
    var etTuesdayOpen: TextInputEditText? = null
    var etTuesdayClose: TextInputEditText? = null
    var etWednesdayOpen: TextInputEditText? = null
    var etWednesdayClose: TextInputEditText? = null
    var etThursdayOpen: TextInputEditText? = null
    var etThursdayClose: TextInputEditText? = null
    var etFridayOpen: TextInputEditText? = null
    var etFridayClose: TextInputEditText? = null
    var etSaturdayOpen: TextInputEditText? = null
    var etSaturdayClose: TextInputEditText? = null
    var etSundayOpen: TextInputEditText? = null
    var etSundayClose: TextInputEditText? = null


    var btnPhoto: Button? = null

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
        var rootView = inflater.inflate(com.sungkunn.inam.R.layout.fragment_shop_item, container, false)
        this.inflater = inflater
        toolbar = rootView.findViewById(com.sungkunn.inam.R.id.toolbar)
        ll = rootView.findViewById(com.sungkunn.inam.R.id.ll)
        spinMarket = rootView.findViewById(com.sungkunn.inam.R.id.spin_market)
        spinZone = rootView.findViewById(com.sungkunn.inam.R.id.spin_zone)
        etName = rootView.findViewById(com.sungkunn.inam.R.id.et_name)
        etOwner = rootView.findViewById(com.sungkunn.inam.R.id.et_owner)
        etPhone = rootView.findViewById(com.sungkunn.inam.R.id.et_phone)
        etLine = rootView.findViewById(com.sungkunn.inam.R.id.et_line)
        etFacebook = rootView.findViewById(com.sungkunn.inam.R.id.et_facebook)
        etEmail = rootView.findViewById(com.sungkunn.inam.R.id.et_email)
        btnPhoto = rootView.findViewById(com.sungkunn.inam.R.id.btn_photo)

        etMondayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_monday_open)
        etMondayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_monday_close)
        etTuesdayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_tuesday_open)
        etTuesdayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_tuesday_close)
        etWednesdayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_wednesday_open)
        etWednesdayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_wednesday_close)
        etThursdayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_thursday_open)
        etThursdayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_thursday_close)
        etFridayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_friday_open)
        etFridayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_friday_close)
        etSaturdayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_saturday_open)
        etSaturdayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_saturday_close)
        etSundayOpen = rootView.findViewById(com.sungkunn.inam.R.id.et_sunday_open)
        etSundayClose = rootView.findViewById(com.sungkunn.inam.R.id.et_sunday_close)


        toolbar!!.inflateMenu(com.sungkunn.inam.R.menu.menu_item)
        toolbar!!.setNavigationIcon(com.sungkunn.inam.R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        btnPhoto!!.setOnClickListener(this)

        etMondayOpen!!.setOnClickListener(this)
        etMondayClose!!.setOnClickListener(this)
        etTuesdayOpen!!.setOnClickListener(this)
        etTuesdayClose!!.setOnClickListener(this)
        etWednesdayOpen!!.setOnClickListener(this)
        etWednesdayClose!!.setOnClickListener(this)
        etThursdayOpen!!.setOnClickListener(this)
        etThursdayClose!!.setOnClickListener(this)
        etFridayOpen!!.setOnClickListener(this)
        etFridayClose!!.setOnClickListener(this)
        etSaturdayOpen!!.setOnClickListener(this)
        etSaturdayClose!!.setOnClickListener(this)
        etSundayOpen!!.setOnClickListener(this)
        etSundayClose!!.setOnClickListener(this)

//        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
//                intent.putExtra("key", shopItem!!.key)
//                intent.putExtra("name", shopItem!!.data.name)
//                inflater.context.startActivity(intent)
//            }
//        })
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

    fun setShop() {
        shopItem ?: return
        etName!!.setText(shopItem!!.data.name)
        etOwner!!.setText(shopItem!!.data.owner)
        etPhone!!.setText(shopItem!!.data.phone)
        etLine!!.setText(shopItem!!.data.line)
        etFacebook!!.setText(shopItem!!.data.facebook)
        etEmail!!.setText(shopItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(shopItem!!.data.marketId))
        spinZone!!.setSelection(getIndexZone(shopItem!!.data.zoneId))

        etMondayOpen!!.setText(shopItem!!.data.monday_open)
        etMondayClose!!.setText(shopItem!!.data.monday_close)
        etTuesdayOpen!!.setText(shopItem!!.data.tuesday_open)
        etTuesdayClose!!.setText(shopItem!!.data.tuesday_close)
        etWednesdayOpen!!.setText(shopItem!!.data.wednesday_open)
        etWednesdayClose!!.setText(shopItem!!.data.wednesday_close)
        etThursdayOpen!!.setText(shopItem!!.data.thursday_open)
        etThursdayClose!!.setText(shopItem!!.data.thursday_close)
        etFridayOpen!!.setText(shopItem!!.data.friday_open)
        etFridayClose!!.setText(shopItem!!.data.friday_close)
        etSaturdayOpen!!.setText(shopItem!!.data.saturday_open)
        etSaturdayClose!!.setText(shopItem!!.data.saturday_close)
        etSundayOpen!!.setText(shopItem!!.data.sunday_open)
        etSundayClose!!.setText(shopItem!!.data.sunday_close)

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

    fun getIndexZone(zoneId: String?): Int {
        var index = 0
        for (item in this!!.zoneList!!) {
            if (item.key.equals(zoneId)) {
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
        when (v) {
            btnPhoto -> {
                var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
                intent.putExtra("key", shopItem!!.key)
                intent.putExtra("name", shopItem!!.data.name)
                inflater!!.context.startActivity(intent)
            }
            etMondayOpen -> getTimeDialog(etMondayOpen!!, "open")
            etMondayClose -> getTimeDialog(etMondayClose!!, "close")
            etTuesdayOpen -> getTimeDialog(etTuesdayOpen!!, "open")
            etTuesdayClose -> getTimeDialog(etTuesdayClose!!, "close")
            etWednesdayOpen -> getTimeDialog(etWednesdayOpen!!, "open")
            etWednesdayClose -> getTimeDialog(etWednesdayClose!!, "close")
            etThursdayOpen -> getTimeDialog(etThursdayOpen!!, "open")
            etThursdayClose -> getTimeDialog(etThursdayClose!!, "close")
            etFridayOpen -> getTimeDialog(etFridayOpen!!, "open")
            etFridayClose -> getTimeDialog(etFridayClose!!, "close")
            etSaturdayOpen -> getTimeDialog(etSaturdayOpen!!, "open")
            etSaturdayClose -> getTimeDialog(etSaturdayClose!!, "close")
            etSundayOpen -> getTimeDialog(etSundayOpen!!, "open")
            etSundayClose -> getTimeDialog(etSundayClose!!, "close")
            else -> {
                if (fragmentManager!!.backStackEntryCount > 0) {
                    fragmentManager!!.popBackStack()
                } else {
                    activity!!.finish()
                }
            }


        }
//        if (v == btnPhoto) {
//            var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
//            intent.putExtra("key", shopItem!!.key)
//            intent.putExtra("name", shopItem!!.data.name)
//            inflater!!.context.startActivity(intent)
//        } else if (v == etMondayOpen || v == etTuesdayOpen || v == etWednesdayOpen || v == etThursdayOpen || v == etFridayOpen || v == etSaturdayOpen || v == etSundayOpen) {
//            val mcurrentTime = Calendar.getInstance()
//            mcurrentTime.set(Calendar.HOUR_OF_DAY, 8)
//            mcurrentTime.set(Calendar.MINUTE, 0)
//            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
//            val minute = mcurrentTime.get(Calendar.MINUTE)
//            val mTimePicker: TimePickerDialog
//            var etTemp = v as TextInputEditText
//            mTimePicker = TimePickerDialog(
//                inflater!!.context,
//                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
//                    etTemp!!.setText(
//                        "$selectedHour:$selectedMinute"
//                    )
//                }, hour, minute, true
//            )//Yes 24 hour time
//            mTimePicker.setTitle("Select Time")
//            mTimePicker.show()
//        } else if (v == etMondayClose || v == etTuesdayClose || v == etWednesdayClose || v == etThursdayClose || v == etFridayClose || v == etSaturdayClose || v == etSundayClose) {
//            val mcurrentTime = Calendar.getInstance()
//            mcurrentTime.set(Calendar.HOUR_OF_DAY, 17)
//            mcurrentTime.set(Calendar.MINUTE, 0)
//            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
//            val minute = mcurrentTime.get(Calendar.MINUTE)
//            val mTimePicker: TimePickerDialog
//            var etTemp = v as TextInputEditText
//            mTimePicker = TimePickerDialog(
//                inflater!!.context,
//                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
//                    etTemp!!.setText(
//                        "$selectedHour:$selectedMinute"
//                    )
//                }, hour, minute, true
//            )//Yes 24 hour time
//            mTimePicker.setTitle("Select Time")
//            mTimePicker.show()
//        } else {
//            if (fragmentManager!!.backStackEntryCount > 0) {
//                fragmentManager!!.popBackStack()
//            } else {
//                activity!!.finish()
//            }
//        }
    }

    private fun getTimeDialog(tv: TextView, duration: String) {
        var c = Calendar.getInstance()

        if (duration.equals("open")){
            c.set(Calendar.HOUR_OF_DAY, 8)
            c.set(Calendar.MINUTE, 0)
        } else if (duration.equals("close")){
            c.set(Calendar.HOUR_OF_DAY, 17)
            c.set(Calendar.MINUTE, 0)
        }

        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDate = c.get(Calendar.DATE)
        var mHour = c.get(Calendar.HOUR_OF_DAY)
        var mMinute = c.get(Calendar.MINUTE)
        var mSecond = c.get(Calendar.SECOND)


        var test = Timestamp(c.timeInMillis)
        Log.d(TAG, test.toString() + " -> " + c.timeInMillis)

        val timePickerDialog = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> tv!!.setText("$hourOfDay:$minute") },
            mHour,
            mMinute,
            true
        )
        timePickerDialog.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            com.sungkunn.inam.R.id.action_save ->
                saveShop()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun saveShop() {
        val snackbar: Snackbar = Snackbar.make(
            ll!!,
            resources.getString(com.sungkunn.inam.R.string.save_process),
            Snackbar.LENGTH_INDEFINITE
        )


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
                zoneList!!.get(indexZone).key,
                etMondayOpen!!.text!!.toString(),
                etMondayClose!!.text!!.toString(),
                etTuesdayOpen!!.text!!.toString(),
                etTuesdayClose!!.text!!.toString(),
                etWednesdayOpen!!.text!!.toString(),
                etWednesdayClose!!.text!!.toString(),
                etThursdayOpen!!.text!!.toString(),
                etThursdayClose!!.text!!.toString(),
                etFridayOpen!!.text!!.toString(),
                etFridayClose!!.text!!.toString(),
                etSaturdayOpen!!.text!!.toString(),
                etSaturdayClose!!.text!!.toString(),
                etSundayOpen!!.text!!.toString(),
                etSundayClose!!.text!!.toString()
            )
            db.collection("shops").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(
                        ll!!,
                        resources.getString(com.sungkunn.inam.R.string.save_success),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    shopItem = WrapShop(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(
                        ll!!,
                        resources.getString(com.sungkunn.inam.R.string.save_fault),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        } else {
            shopItem!!.data.name = etName!!.text!!.toString()
            shopItem!!.data.owner = etOwner!!.text!!.toString()
            shopItem!!.data.phone = etPhone!!.text!!.toString()
            shopItem!!.data.line = etLine!!.text!!.toString()
            shopItem!!.data.facebook = etFacebook!!.text!!.toString()
            shopItem!!.data.email = etEmail!!.text!!.toString()
            shopItem!!.data.monday_open = etMondayOpen!!.text!!.toString()
            shopItem!!.data.monday_close = etMondayClose!!.text!!.toString()
            shopItem!!.data.tuesday_open = etTuesdayOpen!!.text!!.toString()
            shopItem!!.data.tuesday_close = etTuesdayClose!!.text!!.toString()
            shopItem!!.data.wednesday_open = etWednesdayOpen!!.text!!.toString()
            shopItem!!.data.wednesday_close = etWednesdayClose!!.text!!.toString()
            shopItem!!.data.thursday_open = etThursdayOpen!!.text!!.toString()
            shopItem!!.data.thursday_close = etThursdayClose!!.text!!.toString()
            shopItem!!.data.friday_open = etFridayOpen!!.text!!.toString()
            shopItem!!.data.friday_close = etFridayClose!!.text!!.toString()
            shopItem!!.data.saturday_open = etSaturdayOpen!!.text!!.toString()
            shopItem!!.data.saturday_close = etSaturdayClose!!.text!!.toString()
            shopItem!!.data.sunday_open = etSundayOpen!!.text!!.toString()
            shopItem!!.data.sunday_close = etSundayClose!!.text!!.toString()

            db.collection("shops").document(shopItem!!.key)
                .set(shopItem!!.data)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(
                        ll!!,
                        resources.getString(com.sungkunn.inam.R.string.save_success),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(
                        ll!!,
                        resources.getString(com.sungkunn.inam.R.string.save_fault),
                        Snackbar.LENGTH_SHORT
                    ).show()
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
