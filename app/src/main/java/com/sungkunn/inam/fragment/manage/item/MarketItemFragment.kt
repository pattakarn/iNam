package com.sungkunn.inam.fragment.manage.item

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.fragment.home.PreviewFragment
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.WrapMarket
import java.sql.Timestamp
import java.util.*

class MarketItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var marketItem: WrapMarket? = null

    val db = FirebaseFirestore.getInstance()

    var toolbar: Toolbar? = null
    var inflater: LayoutInflater? = null
    var ll: LinearLayout? = null

    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etLine: TextInputEditText? = null
    var etFacebook: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    var etAddressName: TextInputEditText? = null
    var etAddressLatitude: TextInputEditText? = null
    var etAddressLongitude: TextInputEditText? = null

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


    var TAG = "Market Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            marketItem = it.getParcelable("marketItem")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_market_item, container, false)
        this.inflater = inflater
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        etName = rootView.findViewById(R.id.et_name)
        etOwner = rootView.findViewById(R.id.et_owner)
        etPhone = rootView.findViewById(R.id.et_phone)
        etLine = rootView.findViewById(R.id.et_line)
        etFacebook = rootView.findViewById(R.id.et_facebook)
        etEmail = rootView.findViewById(R.id.et_email)

        etAddressName = rootView.findViewById(R.id.et_address_name)
        etAddressLatitude = rootView.findViewById(R.id.et_address_latitude)
        etAddressLongitude = rootView.findViewById(R.id.et_address_longitude)

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

        btnPhoto = rootView.findViewById(R.id.btn_photo)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        if (marketItem != null)
            setMarket()
//            etName!!.text!!.append(marketItem!!.data.name)
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.marketItemContainer, MarketItemPreferenceFragment())
//            .addToBackStack(null)
//            .commit()
//        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
//                intent.putExtra("key", marketItem!!.key)
//                intent.putExtra("name", marketItem!!.data.name)
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

        return rootView
    }

    fun setMarket() {
        marketItem ?: return
        etName!!.setText(marketItem!!.data.name)
        etOwner!!.setText(marketItem!!.data.owner)
        etPhone!!.setText(marketItem!!.data.phone)
        etLine!!.setText(marketItem!!.data.line)
        etFacebook!!.setText(marketItem!!.data.facebook)
        etEmail!!.setText(marketItem!!.data.email)

        etAddressName!!.setText(marketItem!!.data.address)
        etAddressLatitude!!.setText(marketItem!!.data.latitude)
        etAddressLongitude!!.setText(marketItem!!.data.longitude)

        etMondayOpen!!.setText(marketItem!!.data.monday_open)
        etMondayClose!!.setText(marketItem!!.data.monday_close)
        etTuesdayOpen!!.setText(marketItem!!.data.tuesday_open)
        etTuesdayClose!!.setText(marketItem!!.data.tuesday_close)
        etWednesdayOpen!!.setText(marketItem!!.data.wednesday_open)
        etWednesdayClose!!.setText(marketItem!!.data.wednesday_close)
        etThursdayOpen!!.setText(marketItem!!.data.thursday_open)
        etThursdayClose!!.setText(marketItem!!.data.thursday_close)
        etFridayOpen!!.setText(marketItem!!.data.friday_open)
        etFridayClose!!.setText(marketItem!!.data.friday_close)
        etSaturdayOpen!!.setText(marketItem!!.data.saturday_open)
        etSaturdayClose!!.setText(marketItem!!.data.saturday_close)
        etSundayOpen!!.setText(marketItem!!.data.sunday_open)
        etSundayClose!!.setText(marketItem!!.data.sunday_close)

    }

    override fun onClick(v: View?) {
        Log.d("ManageFragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when (v) {
            btnPhoto -> {
                var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
                intent.putExtra("key", marketItem!!.key)
                intent.putExtra("name", marketItem!!.data.name)
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

        if (duration.equals("open")) {
            c.set(Calendar.HOUR_OF_DAY, 8)
            c.set(Calendar.MINUTE, 0)
        } else if (duration.equals("close")) {
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
                saveMarket()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun getPreview() {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container_manage, PreviewFragment.newInstance(marketItem!!.key, marketItem!!.data.name.toString(), "market"))
            .addToBackStack(null)
            .commit()
    }

    private fun saveMarket() {
        val snackbar: Snackbar =
            Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (marketItem == null) {
            var temp = Market(
                etName!!.text!!.toString(),
                etOwner!!.text.toString(),
                etPhone!!.text.toString(),
                etLine!!.text.toString(),
                etFacebook!!.text.toString(),
                etEmail!!.text.toString(),
                etAddressName!!.text.toString(),
                etAddressLatitude!!.text.toString(),
                etAddressLongitude!!.text.toString(),
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
            db.collection("markets").add(temp!!)
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
        } else {
            marketItem!!.data.name = etName!!.text!!.toString()
            marketItem!!.data.owner = etOwner!!.text!!.toString()
            marketItem!!.data.phone = etPhone!!.text!!.toString()
            marketItem!!.data.line = etLine!!.text!!.toString()
            marketItem!!.data.facebook = etFacebook!!.text!!.toString()
            marketItem!!.data.email = etEmail!!.text!!.toString()
            marketItem!!.data.address = etAddressName!!.text!!.toString()
            marketItem!!.data.latitude = etAddressLatitude!!.text!!.toString()
            marketItem!!.data.longitude = etAddressLongitude!!.text!!.toString()
            marketItem!!.data.monday_open = etMondayOpen!!.text!!.toString()
            marketItem!!.data.monday_close = etMondayClose!!.text!!.toString()
            marketItem!!.data.tuesday_open = etTuesdayOpen!!.text!!.toString()
            marketItem!!.data.tuesday_close = etTuesdayClose!!.text!!.toString()
            marketItem!!.data.wednesday_open = etWednesdayOpen!!.text!!.toString()
            marketItem!!.data.wednesday_close = etWednesdayClose!!.text!!.toString()
            marketItem!!.data.thursday_open = etThursdayOpen!!.text!!.toString()
            marketItem!!.data.thursday_close = etThursdayClose!!.text!!.toString()
            marketItem!!.data.friday_open = etFridayOpen!!.text!!.toString()
            marketItem!!.data.friday_close = etFridayClose!!.text!!.toString()
            marketItem!!.data.saturday_open = etSaturdayOpen!!.text!!.toString()
            marketItem!!.data.saturday_close = etSaturdayClose!!.text!!.toString()
            marketItem!!.data.sunday_open = etSundayOpen!!.text!!.toString()
            marketItem!!.data.sunday_close = etSundayClose!!.text!!.toString()
            db.collection("markets").document(marketItem!!.key)
                .set(marketItem!!.data)
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
        fun newInstance(marketItem: WrapMarket?) =
            MarketItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("marketItem", marketItem)
                }
            }
    }

}
