package com.sungkunn.inam.new_design.ui.manage.user

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.PlaceViewModel
import com.sungkunn.inam.new_design.firestore.UserViewModel
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.UserDao

class NewEditUserFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance(userItem: UserDao?) = NewEditUserFragment().apply {
            //            Log.d("NewEditPlaceFragment", "============================ " + communityItem!!.data.name)
            arguments = Bundle().apply {
                putParcelable("item", userItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userItem = it.getParcelable("item")
        }
    }

    private lateinit var userVM: UserViewModel

    private var userItem: UserDao? = null
    var inflater: LayoutInflater? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var etEmail: TextInputEditText? = null
    var etRole: TextInputEditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userVM = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var rootView = inflater.inflate(R.layout.new_edit_user_fragment, container, false)
        this.inflater = inflater

        init(rootView)
        setUser()

        toolbar!!.inflateMenu(R.menu.menu_new_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
        etRole!!.setOnClickListener(this)

        return rootView
    }

    fun init(rootView: View) {

        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        // -------------------- Info --------------------
        etEmail = rootView.findViewById(R.id.et_email)
        etRole = rootView.findViewById(R.id.et_role)

    }

    fun setUser() {
        userItem ?: return

        etEmail!!.setText(userItem!!.data.email)
        etRole!!.setText(userItem!!.data.role)

    }

    private fun saveUser() {
        userItem ?: return

        val snackbar: Snackbar =
            Snackbar.make(
                ll!!,
                resources.getString(R.string.save_process),
                Snackbar.LENGTH_INDEFINITE
            )

        snackbar.show()

        userItem!!.data.role = etRole!!.text!!.toString()
//        Log.d("Edit User", userItem!!.data.toString())
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
        userVM.saveUser(userItem!!)
        snackbar.dismiss()
        Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
//        Toast.makeText(inflater!!.context, "Save", Toast.LENGTH_SHORT).show()
//        activity!!.finish()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v!!) {
            etRole -> {
                val builder = AlertDialog.Builder(inflater!!.context)
                var listItem = inflater!!.context.resources.getStringArray(R.array.role_user)
                builder.setItems(listItem) { _, which ->
                    val selected = listItem[which]
                    etRole!!.setText(selected)
                    saveUser()
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
                saveUser()
            }
        }
        return true
    }


}
