package com.sungkunn.inam.old_ver.fragment.ui.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PhotoPagerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PhotoPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PhotoPagerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var iv: ImageView? = null
    var TAG = "Photo Pager"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_photo_pager, container, false)
        iv = rootView.findViewById(R.id.iv)
        Log.d(TAG, "========== " + param1)

        setPhoto()


        return rootView
    }

    fun setPhoto() {
        val storageRef = FirebaseStorage.getInstance().reference
        if (!param1.equals("")) {
            storageRef.child(param1!!).downloadUrl.addOnSuccessListener {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(this)
                    .load(it.toString())
                    .placeholder(R.drawable.ic_region)
                    .into(iv!!)
            }.addOnFailureListener {
                // Handle any errors
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
