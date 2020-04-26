package com.sungkunn.inam.new_design.ui.manage.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.firestore.PlaceViewModel
import com.sungkunn.inam.new_design.firestore.ProductViewModel
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.model.Product
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Photo_Hori_List
import com.sungkunn.inam.new_design.rv.adapter.Spin_Adapter_Place_List


class NewEditProductFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        fun newInstance(productItem: ProductDao?) = NewEditProductFragment().apply {
            //            Log.d("NewEditPlaceFragment", "============================ " + communityItem!!.data.name)
            arguments = Bundle().apply {
                putParcelable("item", productItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getParcelable("item")
        }
    }

    private lateinit var placeVM: PlaceViewModel
    private lateinit var productVM: ProductViewModel
    private lateinit var photoVM: PhotoViewModel
    var inflater: LayoutInflater? = null
    private var productItem: ProductDao? = null
    private var adap_place: Spin_Adapter_Place_List? = null
    private var adap_photo: RV_Adapter_Photo_Hori_List? = null
    val db = FirebaseFirestore.getInstance()
    var TAG = "EDIT_PRODUCT_FRAGMENT"
    val RESULT_LOAD_IMAGE = 1

    // -------------------- Info --------------------
    var etInfoName: TextInputEditText? = null
    var etInfoType: TextInputEditText? = null

    // -------------------- Type --------------------
    var spin_place: Spinner? = null
    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null

    // -------------------- Product --------------------
    var contactView: View? = null
    var etDetail: TextInputEditText? = null
    var etPrice: TextInputEditText? = null

    // -------------------- Photo --------------------
    var btnPhoto: Button? = null
    var rvPhoto: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placeVM =
            ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)

        var rootView = inflater.inflate(R.layout.new_edit_product_fragment, container, false)
        this.inflater = inflater

        placeVM.getPlaceAll().observe(this, Observer {
            adap_place = Spin_Adapter_Place_List(
                inflater.context, it
            )
            spin_place!!.adapter = adap_place
        })

        init(rootView)
        setProduct()

        toolbar!!.inflateMenu(R.menu.menu_new_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        etInfoType!!.setOnClickListener(this)

        btnPhoto!!.setOnClickListener(this)


        return rootView
    }

    fun init(rootView: View) {
        ll = rootView.findViewById(R.id.ll)
        spin_place = rootView.findViewById(R.id.spin_place)
        toolbar = rootView.findViewById(R.id.toolbar)

        // -------------------- Info --------------------
        etInfoName = rootView.findViewById(R.id.et_info_name)
        etInfoType = rootView.findViewById(R.id.et_info_type)

        // -------------------- Contact --------------------
        contactView = rootView.findViewById(R.id.contact_item)
        etDetail = rootView.findViewById(R.id.et_product_data_detail)
        etPrice = rootView.findViewById(R.id.et_product_data_price)

        // -------------------- Photo --------------------
        btnPhoto = rootView.findViewById(R.id.btn_photo)
        rvPhoto = rootView.findViewById(R.id.rv_photo)

    }

    fun setProduct() {
        productItem ?: return

//        var temp = communityVM.getCommunity(placeItem!!.data.community_id!!)
//        Log.d(TAG, "================= " + temp.data.community_name)
//        spin!!.setSelection(adapter!!.getPositionItem(temp.key))

        placeVM.getPlace(productItem!!.data.place_id!!).observe(this, Observer {
            //            Log.d(TAG, "================= " + it.data.community_name)
            spin_place!!.setSelection(adap_place!!.getPositionItem(it.id))
        })


        etInfoName!!.setText(productItem!!.data.name)
        etInfoType!!.setText(productItem!!.data.type)

        etDetail!!.setText(productItem!!.data.detail)
        etPrice!!.setText(productItem!!.data.price)

        photoVM.getPhotoByItem(productItem!!.id).observe(this, Observer {
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

    private fun saveProduct() {
        val snackbar: Snackbar =
            Snackbar.make(
                ll!!,
                resources.getString(R.string.save_process),
                Snackbar.LENGTH_INDEFINITE
            )

        var placeTemp = spin_place!!.selectedItem as PlaceDao
        snackbar.show()
        if (productItem == null) {
            var temp = Product(
                etInfoName!!.text!!.toString(),
                etInfoType!!.text!!.toString(),
                etDetail!!.text.toString(),
                etPrice!!.text.toString(),
                placeTemp.id

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
            productVM.addProduct(temp!!)
            snackbar.dismiss()
            Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(inflater!!.context, "Save", Toast.LENGTH_SHORT).show()
//            activity!!.finish()
        } else {
            productItem!!.data.name = etInfoName!!.text!!.toString()
            productItem!!.data.type = etInfoType!!.text!!.toString()
            productItem!!.data.detail = etDetail!!.text!!.toString()
            productItem!!.data.price = etPrice!!.text!!.toString()
            productItem!!.data.place_id = placeTemp.id
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
            productVM.saveProduct(productItem!!)
            snackbar.dismiss()
            Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(inflater!!.context, "Save", Toast.LENGTH_SHORT).show()
//            activity!!.finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
//        Log.d("New Manage Fragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when (v!!) {
            etInfoType -> {
                val builder = AlertDialog.Builder(inflater!!.context)
                var listItem = inflater!!.context.resources.getStringArray(R.array.type_product)
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
                intent.putExtra("id", productItem!!.id)
                intent.putExtra("name", productItem!!.data.name)
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
                saveProduct()
            }
        }
        return true
    }




}
