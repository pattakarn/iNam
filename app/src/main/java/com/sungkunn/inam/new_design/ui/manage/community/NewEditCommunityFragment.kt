package com.sungkunn.inam.new_design.ui.manage.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.CommunityViewModel
import com.sungkunn.inam.new_design.model.Community
import com.sungkunn.inam.new_design.model.CommunityDao

class NewEditCommunityFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        fun newInstance(communityItem: CommunityDao?) = NewEditCommunityFragment().apply {
            //            Log.d("NewEditPlaceFragment", "============================ " + communityItem!!.data.name)
            arguments = Bundle().apply {
                putParcelable("item", communityItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            communityItem = it.getParcelable("item")
        }
    }

    private lateinit var communityVM: CommunityViewModel
    var inflater: LayoutInflater? = null
    private var communityItem: CommunityDao? = null
    val db = FirebaseFirestore.getInstance()
    var TAG = "EDIT_COMMUNITY_FRAGMENT"

    // -------------------- Type --------------------
    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var rv: RecyclerView? = null

    // -------------------- Info --------------------
    var etInfoName: TextInputEditText? = null
    var etInfoType: TextInputEditText? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var rootView = inflater.inflate(R.layout.new_edit_community_fragment, container, false)
        this.inflater = inflater
        init(rootView)
        setCommunity()
//        rv = rootView.findViewById(R.id.rv)
//        spin = rootView.findViewById(R.id.spinner)
//        toolbar = rootView.findViewById(R.id.toolbar)

        toolbar!!.inflateMenu(R.menu.menu_new_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        etInfoType!!.setOnClickListener(this)

        return rootView
    }

    fun init(rootView: View){
        ll = rootView.findViewById(R.id.ll)
        rv = rootView.findViewById(R.id.rv)
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

    }

    fun setCommunity() {
        communityItem ?: return
        etInfoName!!.setText(communityItem!!.data.community_name)
        etInfoType!!.setText(communityItem!!.data.type)
        etOwner!!.setText(communityItem!!.data.owner)
        etPhone!!.setText(communityItem!!.data.phone)
        etLine!!.setText(communityItem!!.data.line)
        etFacebook!!.setText(communityItem!!.data.facebook)
        etEmail!!.setText(communityItem!!.data.email)

        etAddressName!!.setText(communityItem!!.data.address)
        etAddressLatitude!!.setText(communityItem!!.data.latitude)
        etAddressLongitude!!.setText(communityItem!!.data.longitude)

    }

    private fun saveCommunity() {
        val snackbar: Snackbar =
            Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if (communityItem == null) {
            var temp = Community(
                etInfoName!!.text!!.toString(),
                etInfoType!!.text!!.toString(),
                etOwner!!.text.toString(),
                etPhone!!.text.toString(),
                etLine!!.text.toString(),
                etFacebook!!.text.toString(),
                etEmail!!.text.toString(),
                etAddressName!!.text.toString(),
                etAddressLatitude!!.text.toString(),
                etAddressLongitude!!.text.toString()
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
            communityVM.addCommunity(temp!!)
            snackbar.dismiss()
            Toast.makeText(inflater!!.context,"Save", Toast.LENGTH_SHORT).show()
            activity!!.finish()
        } else {
            communityItem!!.data.community_name = etInfoName!!.text!!.toString()
            communityItem!!.data.type = etInfoType!!.text!!.toString()
            communityItem!!.data.owner = etOwner!!.text!!.toString()
            communityItem!!.data.phone = etPhone!!.text!!.toString()
            communityItem!!.data.line = etLine!!.text!!.toString()
            communityItem!!.data.facebook = etFacebook!!.text!!.toString()
            communityItem!!.data.email = etEmail!!.text!!.toString()
            communityItem!!.data.address = etAddressName!!.text!!.toString()
            communityItem!!.data.latitude = etAddressLatitude!!.text!!.toString()
            communityItem!!.data.longitude = etAddressLongitude!!.text!!.toString()
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
            communityVM.saveCommunity(communityItem!!)
            snackbar.dismiss()
            Toast.makeText(inflater!!.context,"Save",Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communityVM = ViewModelProviders.of(this).get(CommunityViewModel::class.java)

    }

    override fun onClick(v: View?) {
        Log.d("New Manage Fragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when(v!!){
            etInfoType -> {
                val builder = AlertDialog.Builder(inflater!!.context)
                var listItem = inflater!!.context.resources.getStringArray(R.array.type_community)
                builder.setItems(listItem) { _, which ->
                    val selected = listItem[which]
                    etInfoType!!.setText(selected)
//                    Toast.makeText(inflater!!.context,"===== " + selected + " ====",Toast.LENGTH_SHORT).show()

                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
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
