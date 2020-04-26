package com.sungkunn.inam.old_ver.fragment.manage.item

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
import com.sungkunn.inam.old_ver.activity.PhotoItemActivity
import com.sungkunn.inam.old_ver.fragment.home.PreviewFragment
import com.sungkunn.inam.old_ver.model.Market
import com.sungkunn.inam.old_ver.model.Travel
import com.sungkunn.inam.old_ver.model.WrapMarket
import com.sungkunn.inam.old_ver.model.WrapTravel
import java.sql.Timestamp
import java.util.*


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
class TravelItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var travelItem: WrapTravel? = null

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

    var inflater: LayoutInflater? = null
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


    var TAG = "Travel Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            travelItem = it.getParcelable("travelItem")
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
        var rootView = inflater.inflate(com.sungkunn.inam.R.layout.fragment_travel_item, container, false)
        this.inflater = inflater
        toolbar = rootView.findViewById<Toolbar>(com.sungkunn.inam.R.id.toolbar)
        ll = rootView.findViewById(com.sungkunn.inam.R.id.ll)
        spinMarket = rootView.findViewById(com.sungkunn.inam.R.id.spin_market)
        etName = rootView.findViewById(com.sungkunn.inam.R.id.et_name)
        etOwner = rootView.findViewById(com.sungkunn.inam.R.id.et_owner)
        etPhone = rootView.findViewById(com.sungkunn.inam.R.id.et_phone)
        etLine = rootView.findViewById(com.sungkunn.inam.R.id.et_line)
        etFacebook = rootView.findViewById(com.sungkunn.inam.R.id.et_facebook)
        etEmail = rootView.findViewById(com.sungkunn.inam.R.id.et_email)

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

        btnPhoto = rootView.findViewById(R.id.btn_photo)

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



        toolbar!!.inflateMenu(com.sungkunn.inam.R.menu.menu_item)
        toolbar!!.setNavigationIcon(com.sungkunn.inam.R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

//        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
//                intent.putExtra("key", travelItem!!.key)
//                intent.putExtra("name", travelItem!!.data.name)
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


        return rootView
    }

    fun setTravel() {
        travelItem ?: return
        etName!!.setText(travelItem!!.data.name)
        etOwner!!.setText(travelItem!!.data.owner)
        etPhone!!.setText(travelItem!!.data.phone)
        etLine!!.setText(travelItem!!.data.line)
        etFacebook!!.setText(travelItem!!.data.facebook)
        etEmail!!.setText(travelItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(travelItem!!.data.marketId))

        etMondayOpen!!.setText(travelItem!!.data.monday_open)
        etMondayClose!!.setText(travelItem!!.data.monday_close)
        etTuesdayOpen!!.setText(travelItem!!.data.tuesday_open)
        etTuesdayClose!!.setText(travelItem!!.data.tuesday_close)
        etWednesdayOpen!!.setText(travelItem!!.data.wednesday_open)
        etWednesdayClose!!.setText(travelItem!!.data.wednesday_close)
        etThursdayOpen!!.setText(travelItem!!.data.thursday_open)
        etThursdayClose!!.setText(travelItem!!.data.thursday_close)
        etFridayOpen!!.setText(travelItem!!.data.friday_open)
        etFridayClose!!.setText(travelItem!!.data.friday_close)
        etSaturdayOpen!!.setText(travelItem!!.data.saturday_open)
        etSaturdayClose!!.setText(travelItem!!.data.saturday_close)
        etSundayOpen!!.setText(travelItem!!.data.sunday_open)
        etSundayClose!!.setText(travelItem!!.data.sunday_close)

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
                setTravel()
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
//        Log.d(TAG, v!!.id.toString())
        when (v) {
            btnPhoto -> {
                var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
                intent.putExtra("key", travelItem!!.key)
                intent.putExtra("name", travelItem!!.data.name)
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


        //fragmentManager!!.popBackStack()
//        if (fragmentManager!!.backStackEntryCount > 0) {
//            fragmentManager!!.popBackStack()
//        } else {
//            activity!!.finish()
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
            R.id.action_preview ->
                getPreview()
            com.sungkunn.inam.R.id.action_save ->
                saveTravel()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun getPreview() {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container_manage, PreviewFragment.newInstance(travelItem!!.key, travelItem!!.data.name.toString(), "travel"))
            .addToBackStack(null)
            .commit()
    }

    private fun saveTravel() {
        val snackbar: Snackbar =
            Snackbar.make(
                ll!!,
                resources.getString(com.sungkunn.inam.R.string.save_process),
                Snackbar.LENGTH_INDEFINITE
            )

        snackbar.show()
        if (travelItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var temp = Travel(
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
            db.collection("travels").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(
                        ll!!,
                        resources.getString(com.sungkunn.inam.R.string.save_success),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    travelItem = WrapTravel(documentReference.id, temp)
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
            travelItem!!.data.name = etName!!.text!!.toString()
            travelItem!!.data.owner = etOwner!!.text!!.toString()
            travelItem!!.data.phone = etPhone!!.text!!.toString()
            travelItem!!.data.line = etLine!!.text!!.toString()
            travelItem!!.data.facebook = etFacebook!!.text!!.toString()
            travelItem!!.data.email = etEmail!!.text!!.toString()
            travelItem!!.data.monday_open = etMondayOpen!!.text!!.toString()
            travelItem!!.data.monday_close = etMondayClose!!.text!!.toString()
            travelItem!!.data.tuesday_open = etTuesdayOpen!!.text!!.toString()
            travelItem!!.data.tuesday_close = etTuesdayClose!!.text!!.toString()
            travelItem!!.data.wednesday_open = etWednesdayOpen!!.text!!.toString()
            travelItem!!.data.wednesday_close = etWednesdayClose!!.text!!.toString()
            travelItem!!.data.thursday_open = etThursdayOpen!!.text!!.toString()
            travelItem!!.data.thursday_close = etThursdayClose!!.text!!.toString()
            travelItem!!.data.friday_open = etFridayOpen!!.text!!.toString()
            travelItem!!.data.friday_close = etFridayClose!!.text!!.toString()
            travelItem!!.data.saturday_open = etSaturdayOpen!!.text!!.toString()
            travelItem!!.data.saturday_close = etSaturdayClose!!.text!!.toString()
            travelItem!!.data.sunday_open = etSundayOpen!!.text!!.toString()
            travelItem!!.data.sunday_close = etSundayClose!!.text!!.toString()
            db.collection("travels").document(travelItem!!.key)
                .set(travelItem!!.data)
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
        fun newInstance(travelItem: WrapTravel?) =
            TravelItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("travelItem", travelItem)
                }
            }
    }

}
