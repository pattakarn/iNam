package com.sungkunn.inam.old_ver.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.fragment.ui.hotel.HotelInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.market.MarketInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.network.NetworkInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.product.ProductInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.service.ServiceInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.shop.ShopInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.travel.TravelInfoFragment
import com.sungkunn.inam.old_ver.fragment.ui.zone.ZoneInfo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PreviewFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PreviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PreviewFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var key: String? = null
    private var name: String? = null
    private var type: String? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var backdrop: ImageView? = null

    var TAG = "Preview"

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
        var rootView = inflater.inflate(R.layout.fragment_preview, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        backdrop = rootView.findViewById(R.id.backdrop)

        toolbar!!.setTitle(name)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)

        setPhoto()

        when (type) {
            "market" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, MarketInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "zone" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, ZoneInfo.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "shop" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, ShopInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "product" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, ProductInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "hotel" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, HotelInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
//            "room" -> {
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.container_preview, MarketInfoFragment.newInstance(key, name, ""))
//                    .addToBackStack(null)
//                    .commit()
//            }
            "travel" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, TravelInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "network" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, NetworkInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }
            "service" -> {
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_preview, ServiceInfoFragment.newInstance(key, name, ""))
                    .addToBackStack(null)
                    .commit()
            }

        }



        return rootView
    }

    fun setPhoto() {
        val storageRef = FirebaseStorage.getInstance().reference
        storageRef.child("images/" + key + "_0").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                .placeholder(R.drawable.ic_region)
                .into(backdrop!!)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    override fun onClick(v: View?) {
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(key: String, name: String, type: String) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
