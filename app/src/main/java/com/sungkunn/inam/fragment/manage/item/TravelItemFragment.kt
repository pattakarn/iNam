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
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.Travel
import com.sungkunn.inam.model.WrapMarket
import com.sungkunn.inam.model.WrapTravel
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

    var tvDay1Open: TextView? = null
    var tvDay1Close: TextView? = null
    var tvDay2Open: TextView? = null
    var tvDay2Close: TextView? = null
    var tvDay3Open: TextView? = null
    var tvDay3Close: TextView? = null
    var tvDay4Open: TextView? = null
    var tvDay4Close: TextView? = null
    var tvDay5Open: TextView? = null
    var tvDay5Close: TextView? = null
    var tvDay6Open: TextView? = null
    var tvDay6Close: TextView? = null
    var tvDay7Open: TextView? = null
    var tvDay7Close: TextView? = null

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
        toolbar = rootView.findViewById<Toolbar>(com.sungkunn.inam.R.id.toolbar)
        ll = rootView.findViewById(com.sungkunn.inam.R.id.ll)
        spinMarket = rootView.findViewById(com.sungkunn.inam.R.id.spin_market)
        etName = rootView.findViewById(com.sungkunn.inam.R.id.et_name)
        etOwner = rootView.findViewById(com.sungkunn.inam.R.id.et_owner)
        etPhone = rootView.findViewById(com.sungkunn.inam.R.id.et_phone)
        etLine = rootView.findViewById(com.sungkunn.inam.R.id.et_line)
        etFacebook = rootView.findViewById(com.sungkunn.inam.R.id.et_facebook)
        etEmail = rootView.findViewById(com.sungkunn.inam.R.id.et_email)

        tvDay1Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day1_open)
        tvDay1Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day1_close)
        tvDay2Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day2_open)
        tvDay2Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day2_close)
        tvDay3Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day3_open)
        tvDay3Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day3_close)
        tvDay4Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day4_open)
        tvDay4Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day4_close)
        tvDay5Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day5_open)
        tvDay5Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day5_close)
        tvDay6Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day6_open)
        tvDay6Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day6_close)
        tvDay7Open = rootView.findViewById(com.sungkunn.inam.R.id.tv_day7_open)
        tvDay7Close = rootView.findViewById(com.sungkunn.inam.R.id.tv_day7_close)

        btnPhoto = rootView.findViewById(R.id.btn_photo)

        tvDay1Open!!.setOnClickListener(this)
        tvDay1Close!!.setOnClickListener(this)
        tvDay2Open!!.setOnClickListener(this)
        tvDay2Close!!.setOnClickListener(this)
        tvDay3Open!!.setOnClickListener(this)
        tvDay3Close!!.setOnClickListener(this)
        tvDay4Open!!.setOnClickListener(this)
        tvDay4Close!!.setOnClickListener(this)
        tvDay5Open!!.setOnClickListener(this)
        tvDay5Close!!.setOnClickListener(this)
        tvDay6Open!!.setOnClickListener(this)
        tvDay6Close!!.setOnClickListener(this)
        tvDay7Open!!.setOnClickListener(this)
        tvDay7Close!!.setOnClickListener(this)



        toolbar!!.inflateMenu(com.sungkunn.inam.R.menu.menu_item)
        toolbar!!.setNavigationIcon(com.sungkunn.inam.R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
                intent.putExtra("key", travelItem!!.key)
                intent.putExtra("name", travelItem!!.data.name)
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

    fun setTravel() {
        travelItem ?: return
        etName!!.text!!.append(travelItem!!.data.name)
        etOwner!!.text!!.append(travelItem!!.data.owner)
        etPhone!!.text!!.append(travelItem!!.data.phone)
        etLine!!.text!!.append(travelItem!!.data.line)
        etFacebook!!.text!!.append(travelItem!!.data.facebook)
        etEmail!!.text!!.append(travelItem!!.data.email)
        spinMarket!!.setSelection(getIndexMarket(travelItem!!.data.marketId))

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
        Log.d(TAG, v!!.id.toString())
        when (v) {
            tvDay1Open -> getTimeDialog(tvDay1Open!!)
            tvDay1Close -> getTimeDialog(tvDay1Close!!)
            tvDay2Open -> getTimeDialog(tvDay2Open!!)
            tvDay2Close -> getTimeDialog(tvDay2Close!!)
            tvDay3Open -> getTimeDialog(tvDay3Open!!)
            tvDay3Close -> getTimeDialog(tvDay3Close!!)
            tvDay4Open -> getTimeDialog(tvDay4Open!!)
            tvDay4Close -> getTimeDialog(tvDay4Close!!)
            tvDay5Open -> getTimeDialog(tvDay5Open!!)
            tvDay5Close -> getTimeDialog(tvDay5Close!!)
            tvDay6Open -> getTimeDialog(tvDay6Open!!)
            tvDay6Close -> getTimeDialog(tvDay6Close!!)
            tvDay7Open -> getTimeDialog(tvDay7Open!!)
            tvDay7Close -> getTimeDialog(tvDay7Close!!)
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

    private fun getTimeDialog(tv: TextView) {
        var c = Calendar.getInstance()
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
                saveTravel()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
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
                marketList!!.get(indexMarket).key
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
