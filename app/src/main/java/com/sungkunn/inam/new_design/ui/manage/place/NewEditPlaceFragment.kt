package com.sungkunn.inam.new_design.ui.manage.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.NewEditPhotoActivity
import com.sungkunn.inam.new_design.firestore.CommunityViewModel
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.firestore.PlaceViewModel
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.model.Place
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Photo_Hori_List
import com.sungkunn.inam.new_design.rv.adapter.Spin_Adapter_Community_List

class NewEditPlaceFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        fun newInstance(placeItem: PlaceDao?) = NewEditPlaceFragment().apply {
            //            Log.d("NewEditPlaceFragment", "============================ " + communityItem!!.data.name)
            arguments = Bundle().apply {
                putParcelable("item", placeItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeItem = it.getParcelable("item")
        }
    }

    private lateinit var placeVM: PlaceViewModel
    private lateinit var communityVM: CommunityViewModel
    private lateinit var photoVM: PhotoViewModel
    var inflater: LayoutInflater? = null
    private var placeItem: PlaceDao? = null
    private var adapter: Spin_Adapter_Community_List? = null
    private var adap_photo: RV_Adapter_Photo_Hori_List? = null
    val db = FirebaseFirestore.getInstance()
    var TAG = "EDIT_PLACE_FRAGMENT"

    // -------------------- Info --------------------
    var etInfoName: TextInputEditText? = null
    var etInfoType: TextInputEditText? = null

    // -------------------- Type --------------------
    var spin: Spinner? = null
    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null

    // -------------------- Contact --------------------
    var contactView: View? = null
    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etLine: TextInputEditText? = null
    var etFacebook: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    // -------------------- Address --------------------
    var addressView: View? = null
    var etAddressName: TextInputEditText? = null
    var etAddressLatitude: TextInputEditText? = null
    var etAddressLongitude: TextInputEditText? = null

    // -------------------- Open Time --------------------
    var opentimeView: View? = null
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

    // -------------------- Photo --------------------
    var btnPhoto: Button? = null
    var rvPhoto: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communityVM =
            ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        var rootView = inflater.inflate(R.layout.new_edit_place_fragment, container, false)

        this.inflater = inflater

        communityVM.getCommunityAll().observe(this, Observer {
            adapter = Spin_Adapter_Community_List(
                inflater.context, it
            )
            spin!!.adapter = adapter
        })

        init(rootView)
        setPlace()
//        rv = rootView.findViewById(R.id.rv)
//        spin = rootView.findViewById(R.id.spinner)
//        toolbar = rootView.findViewById(R.id.toolbar)

        toolbar!!.inflateMenu(R.menu.menu_new_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        etInfoType!!.setOnClickListener(this)

        btnPhoto!!.setOnClickListener(this)

//        var listItem = this.resources.getStringArray(R.array.type_place)
//        val adapterItem: ArrayAdapter<String> = ArrayAdapter<String>(
//            inflater.context,
//            R.layout.support_simple_spinner_dropdown_item, listItem
//        )
//        spin!!.setAdapter(adapterItem)
//        spin!!.adapter = Spin_Adapter_Custom_List(
//            inflater.context,
//            listOf(
//                Spin_Adapter_Custom_List.Mood("Angry"),
//                Spin_Adapter_Custom_List.Mood("Happy"),
//                Spin_Adapter_Custom_List.Mood("Playful"),
//                Spin_Adapter_Custom_List.Mood("Wondering")
//            )
//        )



        return rootView
    }

    fun init(rootView: View) {
        ll = rootView.findViewById(R.id.ll)
        spin = rootView.findViewById(R.id.spin_community)
        toolbar = rootView.findViewById(R.id.toolbar)

        // -------------------- Info --------------------
        etInfoName = rootView.findViewById(R.id.et_info_name)
        etInfoType = rootView.findViewById(R.id.et_info_type)

        // -------------------- Contact --------------------
        contactView = rootView.findViewById(R.id.contact_item)
        etName = rootView.findViewById(R.id.et_name)
        etOwner = rootView.findViewById(R.id.et_owner)
        etPhone = rootView.findViewById(R.id.et_phone)
        etLine = rootView.findViewById(R.id.et_line)
        etFacebook = rootView.findViewById(R.id.et_facebook)
        etEmail = rootView.findViewById(R.id.et_email)

        // -------------------- Address --------------------
        addressView = rootView.findViewById(R.id.address_item)
        etAddressName = rootView.findViewById(R.id.et_address_name)
        etAddressLatitude = rootView.findViewById(R.id.et_address_latitude)
        etAddressLongitude = rootView.findViewById(R.id.et_address_longitude)

        // -------------------- Open Time --------------------
        opentimeView = rootView.findViewById(R.id.opentime_item)
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

        // -------------------- Photo --------------------
        btnPhoto = rootView.findViewById(R.id.btn_photo)
        rvPhoto = rootView.findViewById(R.id.rv_photo)
    }

    fun setPlace() {
        placeItem ?: return

//        var temp = communityVM.getCommunity(placeItem!!.data.community_id!!)
//        Log.d(TAG, "================= " + temp.data.community_name)
//        spin!!.setSelection(adapter!!.getPositionItem(temp.key))
        communityVM.getCommunity(placeItem!!.data.community_id!!).observe(this, Observer {
//            Log.d(TAG, "================= " + it.data.community_name)
            spin!!.setSelection(adapter!!.getPositionItem(it.id))
        })

        etInfoName!!.setText(placeItem!!.data.name)
        etInfoType!!.setText(placeItem!!.data.type)
        etOwner!!.setText(placeItem!!.data.owner)
        etPhone!!.setText(placeItem!!.data.phone)
        etLine!!.setText(placeItem!!.data.line)
        etFacebook!!.setText(placeItem!!.data.facebook)
        etEmail!!.setText(placeItem!!.data.email)

        etAddressName!!.setText(placeItem!!.data.address)
        etAddressLatitude!!.setText(placeItem!!.data.latitude)
        etAddressLongitude!!.setText(placeItem!!.data.longitude)

        etMondayOpen!!.setText(placeItem!!.data.monday_open)
        etMondayClose!!.setText(placeItem!!.data.monday_close)
        etTuesdayOpen!!.setText(placeItem!!.data.tuesday_open)
        etTuesdayClose!!.setText(placeItem!!.data.tuesday_close)
        etWednesdayOpen!!.setText(placeItem!!.data.wednesday_open)
        etWednesdayClose!!.setText(placeItem!!.data.wednesday_close)
        etThursdayOpen!!.setText(placeItem!!.data.thursday_open)
        etThursdayClose!!.setText(placeItem!!.data.thursday_close)
        etFridayOpen!!.setText(placeItem!!.data.friday_open)
        etFridayClose!!.setText(placeItem!!.data.friday_close)
        etSaturdayOpen!!.setText(placeItem!!.data.saturday_open)
        etSaturdayClose!!.setText(placeItem!!.data.saturday_close)
        etSundayOpen!!.setText(placeItem!!.data.sunday_open)
        etSundayClose!!.setText(placeItem!!.data.sunday_close)

        photoVM.getPhotoByItem(placeItem!!.id).observe(this, Observer {
            if (it != null){
//                Glide.with(inflater!!.context)
//                    .load(it.get(0).data.image_url!!)
//                    .placeholder(R.drawable.inam_logo)
//                    .into(ivPhoto!!)
                adap_photo = RV_Adapter_Photo_Hori_List(it, fragmentManager!!)
                val llm = LinearLayoutManager(inflater!!.context, LinearLayoutManager.HORIZONTAL, false)

                rvPhoto!!.setLayoutManager(llm)
                rvPhoto!!.setAdapter(adap_photo)
            }
        })

    }

    private fun saveCommunity() {
        val snackbar: Snackbar =
            Snackbar.make(
                ll!!,
                resources.getString(R.string.save_process),
                Snackbar.LENGTH_INDEFINITE
            )

        var communityTemp = spin!!.selectedItem as CommunityDao
        snackbar.show()
        if (placeItem == null) {
            var temp = Place(
                etInfoName!!.text!!.toString(),
                etInfoType!!.text!!.toString(),
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
                etSundayClose!!.text!!.toString(),
                communityTemp.id

            )
//            db.collection("markets").add(temp!!)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "DocumentSnapshot successfully written!")
//                    snackbar.dismiss()
//                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "False", e)
//                    snackbar.dismiss()
//                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
//                }
            placeVM.addPlace(temp!!)
            snackbar.dismiss()
            Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(inflater!!.context,"Save",Toast.LENGTH_SHORT).show()
//            activity!!.finish()
        } else {
            placeItem!!.data.name = etInfoName!!.text!!.toString()
            placeItem!!.data.owner = etInfoType!!.text!!.toString()
            placeItem!!.data.phone = etPhone!!.text!!.toString()
            placeItem!!.data.line = etLine!!.text!!.toString()
            placeItem!!.data.facebook = etFacebook!!.text!!.toString()
            placeItem!!.data.email = etEmail!!.text!!.toString()
            placeItem!!.data.address = etAddressName!!.text!!.toString()
            placeItem!!.data.latitude = etAddressLatitude!!.text!!.toString()
            placeItem!!.data.longitude = etAddressLongitude!!.text!!.toString()
            placeItem!!.data.monday_open = etMondayOpen!!.text!!.toString()
            placeItem!!.data.monday_close = etMondayClose!!.text!!.toString()
            placeItem!!.data.tuesday_open = etTuesdayOpen!!.text!!.toString()
            placeItem!!.data.tuesday_close = etTuesdayClose!!.text!!.toString()
            placeItem!!.data.wednesday_open = etWednesdayOpen!!.text!!.toString()
            placeItem!!.data.wednesday_close = etWednesdayClose!!.text!!.toString()
            placeItem!!.data.thursday_open = etThursdayOpen!!.text!!.toString()
            placeItem!!.data.thursday_close = etThursdayClose!!.text!!.toString()
            placeItem!!.data.friday_open = etFridayOpen!!.text!!.toString()
            placeItem!!.data.friday_close = etFridayClose!!.text!!.toString()
            placeItem!!.data.saturday_open = etSaturdayOpen!!.text!!.toString()
            placeItem!!.data.saturday_close = etSaturdayClose!!.text!!.toString()
            placeItem!!.data.sunday_open = etSundayOpen!!.text!!.toString()
            placeItem!!.data.sunday_close = etSundayClose!!.text!!.toString()
            placeItem!!.data.community_id = communityTemp.id
//            db.collection("markets").document(marketItem!!.key)
//                .set(marketItem!!.data)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "DocumentSnapshot successfully written!")
//                    snackbar.dismiss()
//                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "False", e)
//                    snackbar.dismiss()
//                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
//                }
            placeVM.savePlace(placeItem!!)
            snackbar.dismiss()
            Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(inflater!!.context,"Save",Toast.LENGTH_SHORT).show()
//            activity!!.finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placeVM = ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        communityVM = ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        Log.d("New Manage Fragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when (v!!) {
            etInfoType -> {
                val builder = AlertDialog.Builder(inflater!!.context)
                var listItem = inflater!!.context.resources.getStringArray(R.array.type_place)
                builder.setItems(listItem) { _, which ->
                    val selected = listItem[which]
                    etInfoType!!.setText(selected)
//                    Toast.makeText(inflater!!.context,"===== " + selected + " ====",Toast.LENGTH_SHORT).show()

                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
            }
            btnPhoto -> {
                var intent = Intent(inflater!!.context, NewEditPhotoActivity::class.java)
                intent.putExtra("id", placeItem!!.id)
                intent.putExtra("name", placeItem!!.data.name)
                inflater!!.context.startActivity(intent)
            }
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
            R.id.action_save -> {
                saveCommunity()
//                var item: CommunityDao = spin!!.selectedItem as CommunityDao
//                Toast.makeText(
//                    inflater!!.context,
//                    "===== " + item.data.community_name + " ====",
//                    Toast.LENGTH_SHORT
//                ).show()
//                var intent = Intent(inflater!!.context, NewEditPlaceActivity::class.java)
//                inflater!!.context.startActivity(intent)
            }
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_manage, MarketItemFragment.newInstance(null))
//                    .addToBackStack(null)
//                    .commit()
//                        MarketItemFragment.display(fragmentManager)
        }
        return true
    }

}
