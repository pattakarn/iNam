package com.sungkunn.inam.new_design.ui.home

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import com.istyleglobalnetwork.talatnoi.rv.adapter.RV_Adapter_More_List
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_More_Menu
import com.sungkunn.inam.new_design.firestore.UserViewModel
import com.sungkunn.inam.new_design.model.User
import com.sungkunn.inam.new_design.model.UserDao

class HomeMoreFragment : Fragment(), View.OnClickListener {

    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    private lateinit var userVM: UserViewModel
    private var userItem: UserDao? = null
    var inflater: LayoutInflater? = null

    var ll: LinearLayout? = null
    var iv: ImageView? = null
    var rv: RecyclerView? = null
    var tvUsername: TextView? = null
    var tvEmail: TextView? = null
    var btnLogin: MaterialButton? = null
    var btnSignout: MaterialButton? = null

    private var RC_SIGN_IN = 101
    var TAG = "MoreFrag"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userVM = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var rootView = inflater.inflate(R.layout.fragment_home_more, container, false)
        this.inflater = inflater

        init(rootView)

//        getData()

//        var listItem = this.resources.getStringArray(R.array.more_menu)


        btnLogin!!.setOnClickListener(this)
        btnSignout!!.setOnClickListener(this)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        updateUI()

    }

    fun init(rootView: View) {
        rv = rootView.findViewById(R.id.rv)
        ll = rootView.findViewById(R.id.ll)
        iv = rootView.findViewById(R.id.iv)
        tvUsername = rootView.findViewById(R.id.tv_username)
        tvEmail = rootView.findViewById(R.id.tv_email)
        btnLogin = rootView.findViewById(R.id.btn_login)
        btnSignout = rootView.findViewById(R.id.btn_signout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

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
                    .setLogo(R.drawable.inam_logo)
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
//            tvUsername!!.text = currentUser!!.displayName
//            tvEmail!!.text = currentUser!!.email
//            context?.let {
//                Glide.with(it)
//                    .load(currentUser!!.photoUrl)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(iv!!)
//            }

            userVM.getUser(currentUser!!.uid).observe(this, Observer {
                //            Log.d(TAG, "================= " + it.data.community_name)
                userItem = it
                tvUsername!!.text = userItem!!.data.firstname + " " + userItem!!.data.lastname
                tvEmail!!.text = userItem!!.data.email
//                Glide.with(it)
//                    .load(currentUser!!.photoUrl)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(iv!!)
                Glide.with(inflater!!.context)
                    .load(userItem!!.data.photoURL)
                    .placeholder(R.drawable.inam_logo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv!!)

                var listItem: ArrayList<Any> = ArrayList()
                if (userItem!!.data.role.equals("Administrator")) {
                    listItem.add(userItem!!)
                    listItem.add("My Cart")
                    listItem.add("Manage")
                } else {
                    listItem.add(userItem!!)
                    listItem.add("My Cart")
                }

                val adapter = RV_Adapter_More_List(listItem, fragmentManager!!)
                val llm = LinearLayoutManager(inflater!!.context)

                rv!!.setLayoutManager(llm)
                rv!!.setAdapter(adapter)
            })
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
            user.role = "Customer"
//            Log.d(TAG, currentUser!!.uid)
            userItem = UserDao(currentUser!!.uid, user)
            userVM.saveUser(userItem!!)
//            db.collection("users").document(currentUser!!.uid)
//                .set(user!!)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "DocumentSnapshot successfully written!")
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "False", e)
//                }


        }
    }
}