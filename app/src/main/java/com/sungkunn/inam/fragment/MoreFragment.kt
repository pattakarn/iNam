package com.sungkunn.inam.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_More_Menu
import com.sungkunn.inam.db.User


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MoreFragment : Fragment(), View.OnClickListener {

    private var param1: Int = 0
    private var param2: String? = null

    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null

    var ll: LinearLayout? = null
    var iv: ImageView? = null
    var tvUsername: TextView? = null
    var tvEmail: TextView? = null
    var btnLogin: MaterialButton? = null
    var btnSignout: MaterialButton? = null

    private var RC_SIGN_IN = 101
    var TAG = "MoreFrag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

    }

    override fun onStart() {
        super.onStart()
        updateUI()

    }

    private fun updateUI() {
        if (currentUser == null) {
            ll!!.visibility = View.INVISIBLE
            btnLogin!!.visibility = View.VISIBLE
            tvUsername!!.text = ""
            tvEmail!!.text = ""
        } else {
            ll!!.visibility = View.VISIBLE
            btnLogin!!.visibility = View.INVISIBLE
            getData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_more, container, false)
        var rv = rootView.findViewById<RecyclerView>(R.id.rv_more)
//        var ll = rootView.findViewById<LinearLayout>(com.sungkunn.inam.R.id.ll_more)
        ll = rootView.findViewById(R.id.ll)
        iv = rootView.findViewById(R.id.iv)
        tvUsername = rootView.findViewById(R.id.tv_username)
        tvEmail = rootView.findViewById(R.id.tv_email)
        btnLogin = rootView.findViewById(R.id.btn_login)
        btnSignout = rootView.findViewById(R.id.btn_signout)

//        getData()

        var listItem = this.resources.getStringArray(R.array.more_menu)
        val adapter = RV_Adapter_More_Menu(listItem)
        val llm = LinearLayoutManager(inflater.context)

        rv.setLayoutManager(llm)
        rv.setAdapter(adapter)

        btnLogin!!.setOnClickListener(this)
        btnSignout!!.setOnClickListener(this)



        return rootView
    }

    override fun onClick(v: View?) {
        if (v == btnLogin) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(com.sungkunn.inam.R.drawable.inam_logo)
                    .build(),
                RC_SIGN_IN
            )
        } else if (v == btnSignout) {
            auth.signOut()
            Toast.makeText(activity, "Sign out", Toast.LENGTH_SHORT).show()
            updateUI()
        }
    }

    private fun getData() {
        if (currentUser != null) {
            tvUsername!!.text = currentUser!!.displayName
            tvEmail!!.text = currentUser!!.email
            context?.let {
                Glide.with(it)
                    .load(currentUser!!.photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv!!)
            };
//            val docRef = db.collection("users").document(currentUser!!.uid)
//            docRef.get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                        tvUsername!!.text =
//                            document.data!!["firstname"].toString() + " " + document.data!!["lastname"].toString()
//                        tvEmail!!.text = document.data!!["email"].toString()
//
//                    } else {
//                        Log.d(TAG, "No such document")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "get failed with ", exception)
//                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
//                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()
                Log.d("Login", response.toString())
                currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null){
                    if (response!!.isNewUser) {
                        for (profile in currentUser!!.getProviderData()) {
                            saveProfile(profile)
                            saveAccount(profile)
                            // Id of the provider (ex: google.com)
//                            val providerId = profile.getProviderId()
//                            val uid = profile.getUid()
//                            val name = profile.getDisplayName()
//                            val email = profile.getEmail()
//                            val photoUrl = profile.getPhotoUrl()
//                            user = User()
//                            user!!.firstname = profile.displayName
//                            user!!.email = profile.email
                        }
                    }
                    updateUI()
                }
//                val user = FirebaseAuth.getInstance().currentUser
//                updateUI(user)
                // ...
            } else {
                Log.d("Login Error", response.toString())
                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private fun saveProfile(profile: UserInfo) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(profile.displayName)
            .build()

        currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

        currentUser?.updateEmail(profile.email.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.");
                }
            }
    }

    private fun saveAccount(profile: UserInfo) {
        if (currentUser != null) {
            var user = User()
            user.firstname = profile.displayName
            user.email = profile.email
            user.photoURL = profile.photoUrl.toString()
//            Log.d(TAG, currentUser!!.uid)
            db.collection("users").document(currentUser!!.uid)
                .set(user!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            MoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
