package com.sungkunn.inam.fragment.manage.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.db.Market
import com.sungkunn.inam.db.WrapMarket

class MarketItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var marketItem: WrapMarket? = null

    val db = FirebaseFirestore.getInstance()

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var etName: TextInputEditText? = null

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
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        etName = rootView.findViewById(R.id.et_name)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        if (marketItem != null)
            etName!!.text!!.append(marketItem!!.data.name)
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.marketItemContainer, MarketItemPreferenceFragment())
//            .addToBackStack(null)
//            .commit()

        return rootView
    }

    override fun onClick(v: View?) {
        Log.d("ManageFragment", "close fragment")
        //fragmentManager!!.popBackStack()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save ->
                saveMarket()
//                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun saveMarket() {
        val snackbar: Snackbar = Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)

        snackbar.show()
        if(marketItem == null) {
            var temp = Market(etName!!.text!!.toString())
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
