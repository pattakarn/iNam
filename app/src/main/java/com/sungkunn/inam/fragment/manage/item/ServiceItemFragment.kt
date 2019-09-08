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
import com.sungkunn.inam.R
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.fragment.home.PreviewFragment
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.Service
import com.sungkunn.inam.model.WrapMarket
import com.sungkunn.inam.model.WrapService
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
class ServiceItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var serviceItem: WrapService? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null

    var mMarket: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var inflater: LayoutInflater? = null
    var spinMarket: Spinner? = null
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

    var TAG = "Service Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            serviceItem = it.getParcelable("serviceItem")
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
        var rootView = inflater.inflate(R.layout.fragment_service_item, container, false)
        this.inflater = inflater
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

        etMondayOpen = rootView.findViewById(R.id.et_monday_open)
        etMondayClose = rootView.findViewById(R.id.et_monday_close)
        etTuesdayOpen = rootView.findViewById(R.id.et_tuesday_open)
        etTuesdayClose = rootView.findViewById(R.id.et_tuesday_close)
        etWednesdayOpen = rootView.findViewById(R.id.et_wednesday_open)
        etWednesdayClose = rootView.findViewById(R.id.et_wednesday_close)
        etThursdayOpen = rootView.findViewById(R.id.et_thursday_open)
        etThursdayClose = rootView.findViewById(R.id.et_thursday_close)
        etFridayOpen = rootView.findViewById(R.id.et_friday_open)
        etFridayClose = rootView.findViewById(R.id.et_friday_close)
        etSaturdayOpen = rootView.findViewById(R.id.et_saturday_open)
        etSaturdayClose = rootView.findViewById(R.id.et_saturday_close)
        etSundayOpen = rootView.findViewById(R.id.et_sunday_open)
        etSundayClose = rootView.findViewById(R.id.et_sunday_close)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

//        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
//                intent.putExtra("key", serviceItem!!.key)
//                intent.putExtra("name", serviceItem!!.data.name)
//                inflater.context.startActivity(intent)
//            }
//        })

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

    fun setService() {
        serviceItem ?: return
        etName!!.setText(serviceItem!!.data.name)
        etOwner!!.setText(serviceItem!!.data.owner)
        etPhone!!.setText(serviceItem!!.data.phone)
        etLine!!.setText(serviceItem!!.data.line)
        etFacebook!!.setText(serviceItem!!.data.facebook)
        etEmail!!.setText(serviceItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(serviceItem!!.data.marketId))

        etMondayOpen!!.setText(serviceItem!!.data.monday_open)
        etMondayClose!!.setText(serviceItem!!.data.monday_close)
        etTuesdayOpen!!.setText(serviceItem!!.data.tuesday_open)
        etTuesdayClose!!.setText(serviceItem!!.data.tuesday_close)
        etWednesdayOpen!!.setText(serviceItem!!.data.wednesday_open)
        etWednesdayClose!!.setText(serviceItem!!.data.wednesday_close)
        etThursdayOpen!!.setText(serviceItem!!.data.thursday_open)
        etThursdayClose!!.setText(serviceItem!!.data.thursday_close)
        etFridayOpen!!.setText(serviceItem!!.data.friday_open)
        etFridayClose!!.setText(serviceItem!!.data.friday_close)
        etSaturdayOpen!!.setText(serviceItem!!.data.saturday_open)
        etSaturdayClose!!.setText(serviceItem!!.data.saturday_close)
        etSundayOpen!!.setText(serviceItem!!.data.sunday_open)
        etSundayClose!!.setText(serviceItem!!.data.sunday_close)

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
                setService()
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
        when (v) {
            btnPhoto -> {
                var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
                intent.putExtra("key", serviceItem!!.key)
                intent.putExtra("name", serviceItem!!.data.name)
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
            R.id.action_preview ->
                getPreview()
            R.id.action_save ->
                saveService()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun getPreview() {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container_manage, PreviewFragment.newInstance(serviceItem!!.key, serviceItem!!.data.name.toString(), "service"))
            .addToBackStack(null)
            .commit()
    }

    private fun saveService() {
        val snackbar: Snackbar =
            Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (serviceItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var temp = Service(
                etName!!.text!!.toString(),
                etOwner!!.text.toString(),
                etPhone!!.text.toString(),
                etLine!!.text.toString(),
                etFacebook!!.text.toString(),
                etEmail!!.text.toString(),
                marketList!!.get(indexMarket).key,
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
            db.collection("services").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    serviceItem = WrapService(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            serviceItem!!.data.name = etName!!.text!!.toString()
            serviceItem!!.data.owner = etOwner!!.text!!.toString()
            serviceItem!!.data.phone = etPhone!!.text!!.toString()
            serviceItem!!.data.line = etLine!!.text!!.toString()
            serviceItem!!.data.facebook = etFacebook!!.text!!.toString()
            serviceItem!!.data.email = etEmail!!.text!!.toString()
            serviceItem!!.data.monday_open = etMondayOpen!!.text!!.toString()
            serviceItem!!.data.monday_close = etMondayClose!!.text!!.toString()
            serviceItem!!.data.tuesday_open = etTuesdayOpen!!.text!!.toString()
            serviceItem!!.data.tuesday_close = etTuesdayClose!!.text!!.toString()
            serviceItem!!.data.wednesday_open = etWednesdayOpen!!.text!!.toString()
            serviceItem!!.data.wednesday_close = etWednesdayClose!!.text!!.toString()
            serviceItem!!.data.thursday_open = etThursdayOpen!!.text!!.toString()
            serviceItem!!.data.thursday_close = etThursdayClose!!.text!!.toString()
            serviceItem!!.data.friday_open = etFridayOpen!!.text!!.toString()
            serviceItem!!.data.friday_close = etFridayClose!!.text!!.toString()
            serviceItem!!.data.saturday_open = etSaturdayOpen!!.text!!.toString()
            serviceItem!!.data.saturday_close = etSaturdayClose!!.text!!.toString()
            serviceItem!!.data.sunday_open = etSundayOpen!!.text!!.toString()
            serviceItem!!.data.sunday_close = etSundayClose!!.text!!.toString()

            db.collection("services").document(serviceItem!!.key)
                .set(serviceItem!!.data)
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
        fun newInstance(serviceItem: WrapService?) =
            ServiceItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("serviceItem", serviceItem)
                }
            }
    }

}
