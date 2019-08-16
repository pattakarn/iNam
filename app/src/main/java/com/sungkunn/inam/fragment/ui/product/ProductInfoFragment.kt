package com.sungkunn.inam.fragment.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.model.Product
import com.sungkunn.inam.model.WrapProduct

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProductInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProductInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProductInfoFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null
    var data: WrapProduct? = null

    var infoDetail: TextView? = null

    var TAG = "Product Info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getString("key")
            name = it.getString("name")
            type = it.getString("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_product_info, container, false)
        infoDetail = rootView.findViewById(R.id.info_detail)

        getProduct()

        return rootView
    }

    fun getProduct() {
        db.collection("products").document(key!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    WrapShop(document.id, document.toObject(Shop::class.java))
                    data = WrapProduct(document.id, document.toObject(Product::class.java)!!)
//                    infoDetail!!.setText(document.toObject(Shop::class.java)!!.name)
                    setValue()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun setValue(){
        infoDetail!!.setText(data!!.data.name)


    }


    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            ProductInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
