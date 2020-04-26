package com.sungkunn.inam.old_ver.fragment.manage.item

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
import com.sungkunn.inam.old_ver.activity.PhotoItemActivity
import com.sungkunn.inam.old_ver.fragment.home.PreviewFragment
import com.sungkunn.inam.old_ver.model.Market
import com.sungkunn.inam.old_ver.model.WrapMarket
import com.sungkunn.inam.old_ver.model.WrapZone
import com.sungkunn.inam.old_ver.model.Zone

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
class ZoneItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private var zoneItem: WrapZone? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null

    var mMarket: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null
    var etOwner: TextInputEditText? = null
    var etSize: TextInputEditText? = null

    var btnPhoto: Button? = null

    var TAG = "Zone Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zoneItem = it.getParcelable("zoneItem")
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
        var rootView = inflater.inflate(R.layout.fragment_zone_item, container, false)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        spinMarket = rootView.findViewById<Spinner>(R.id.spin)
        ll = rootView.findViewById(R.id.ll)
        etName = rootView.findViewById(R.id.et_name)
        etOwner = rootView.findViewById(R.id.et_owner)
        etSize = rootView.findViewById(R.id.et_size)

        btnPhoto = rootView.findViewById(R.id.btn_photo)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        btnPhoto!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, PhotoItemActivity::class.java)
                intent.putExtra("key", zoneItem!!.key)
                intent.putExtra("name", zoneItem!!.data.name)
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
//        setZone()

        return rootView
    }

    fun setZone(){
        zoneItem ?: return
        etName!!.setText(zoneItem!!.data.name)
        etOwner!!.setText(zoneItem!!.data.owner)
        etSize!!.setText(zoneItem!!.data.size)
        spinMarket!!.setSelection(getIndexMarket(zoneItem!!.data.marketId))

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
                setZone()
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
        Log.d(TAG, "close fragment")
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
                saveZone()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun getPreview() {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container_manage, PreviewFragment.newInstance(zoneItem!!.key, zoneItem!!.data.name.toString(), "zone"))
            .addToBackStack(null)
            .commit()
    }

    private fun saveZone() {
        val snackbar: Snackbar = Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if(zoneItem == null) {
            var indexMarket = spinMarket!!.selectedItemPosition
            var temp = Zone(etName!!.text!!.toString(),etOwner!!.text.toString(), etSize!!.text.toString(), marketList!!.get(indexMarket).key)
            db.collection("zones").add(temp!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    zoneItem = WrapZone(documentReference.id, temp)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            zoneItem!!.data.name = etName!!.text!!.toString()
            zoneItem!!.data.owner = etOwner!!.text!!.toString()
            zoneItem!!.data.size = etSize!!.text!!.toString()
            db.collection("zones").document(zoneItem!!.key)
                .set(zoneItem!!.data)
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
        fun newInstance(zoneItem: WrapZone?) =
            ZoneItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("zoneItem", zoneItem)
                }
            }
    }

}
