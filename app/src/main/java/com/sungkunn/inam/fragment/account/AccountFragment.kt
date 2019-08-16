package com.sungkunn.inam.fragment.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.model.User

class AccountFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    var user: User? = null

    var ll: LinearLayout? = null
    var etFirstname: TextInputEditText? = null
    var etLastname: TextInputEditText? = null
    var etNickname: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etEmail: TextInputEditText? = null
    var toolbar: Toolbar? = null

    var TAG = "Account"

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView = inflater.inflate(R.layout.account_fragment, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        etFirstname = rootView.findViewById(R.id.firstname)
        etLastname = rootView.findViewById(R.id.lastname)
        etNickname = rootView.findViewById(R.id.nickname)
        etPhone = rootView.findViewById(R.id.phone)
        etEmail = rootView.findViewById(R.id.email)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        getData()

        return rootView
    }

    override fun onClick(v: View?) {
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save ->
                saveAccount()
        }
        return true
    }

    private fun saveAccount() {
        if (currentUser != null) {
            if (user == null) {
                user = User(
                    etFirstname?.text.toString(), etLastname?.text.toString(), etNickname?.text.toString()
                    , etPhone?.text.toString(), etEmail?.text.toString()
                )
            } else {
                user!!.firstname = etFirstname?.text.toString()
                user!!.lastname = etLastname?.text.toString()
                user!!.nickname = etNickname?.text.toString()
                user!!.phone = etPhone?.text.toString()
                user!!.email = etEmail?.text.toString()
            }

            Log.d(TAG, currentUser!!.uid)
            val snackbar: Snackbar = Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            db.collection("users").document(currentUser!!.uid)
                .set(user!!)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    saveProfile()
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "False", e)
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(activity, "Please login...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveProfile() {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(user!!.firstname + " " + user!!.lastname)
            .build()

        currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }

    private fun getData() {
        if (currentUser != null) {
            val docRef = db.collection("users").document(currentUser!!.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        user = document.toObject(User::class.java)
                        setDataEditText()
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        } else {
            Toast.makeText(activity, "Login please.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataEditText() {
        etFirstname!!.text!!.append(user!!.firstname)
        etLastname!!.text!!.append(user!!.lastname)
        etNickname!!.text!!.append(user!!.nickname)
        etPhone!!.text!!.append(user!!.phone)
        etEmail!!.text!!.append(user!!.email)
//        etFirstname.text = data[]

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
