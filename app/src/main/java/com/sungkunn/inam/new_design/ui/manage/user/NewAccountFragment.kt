package com.sungkunn.inam.new_design.ui.manage.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.UserViewModel
import com.sungkunn.inam.new_design.model.Photo
import com.sungkunn.inam.new_design.model.PhotoDao
import com.sungkunn.inam.new_design.model.UserDao
import kotlinx.android.synthetic.main.account_fragment.*
import java.net.URI

class NewAccountFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance(userItem: UserDao?) = NewAccountFragment().apply {
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
    private val RESULT_LOAD_IMAGE = 1
    private var imageUri: Uri? = null

    private var userItem: UserDao? = null
    var inflater: LayoutInflater? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var iv: ImageView? = null
    var etFirstname: TextInputEditText? = null
    var etLastname: TextInputEditText? = null
    var etNickname: TextInputEditText? = null
    var etPhone: TextInputEditText? = null
    var etEmail: TextInputEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userVM = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var rootView = inflater.inflate(R.layout.new_account_fragment, container, false)
        this.inflater = inflater

        init(rootView)
        setUser()

        toolbar!!.inflateMenu(R.menu.menu_new_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        iv!!.setOnClickListener(this)

        return rootView
    }

    fun init(rootView: View) {

        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        iv = rootView.findViewById(R.id.iv)
        etFirstname = rootView.findViewById(R.id.firstname)
        etLastname = rootView.findViewById(R.id.lastname)
        etNickname = rootView.findViewById(R.id.nickname)
        etPhone = rootView.findViewById(R.id.phone)
        etEmail = rootView.findViewById(R.id.email)

    }

    fun setUser() {
        userItem ?: return

        etFirstname!!.setText(userItem!!.data.firstname)
        etLastname!!.setText(userItem!!.data.lastname)
        etNickname!!.setText(userItem!!.data.nickname)
        etPhone!!.setText(userItem!!.data.phone)
        etEmail!!.setText(userItem!!.data.email)

        Glide.with(inflater!!.context)
            .load(userItem!!.data.photoURL)
            .placeholder(R.drawable.inam_logo)
            .apply(RequestOptions.circleCropTransform())
            .into(iv!!)

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

        userItem!!.data.firstname = etFirstname!!.text.toString()
        userItem!!.data.lastname = etLastname!!.text.toString()
        userItem!!.data.nickname = etNickname!!.text.toString()
        userItem!!.data.phone = etPhone!!.text.toString()
        userItem!!.data.email = etEmail!!.text.toString()

        if (imageUri != null){
            val storage = FirebaseStorage.getInstance().reference
            val fileToUpload: StorageReference = storage.child("user-photo").child(userItem!!.id)
            fileToUpload.putFile(imageUri!!).addOnSuccessListener {
                fileToUpload.downloadUrl.addOnSuccessListener {
                    userItem!!.data.photoURL = it.toString()
                    userVM.saveUser(userItem!!)
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
        } else {

            userVM.saveUser(userItem!!)
            snackbar.dismiss()
            Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT)
                .show()
        }
//        Toast.makeText(inflater!!.context, "Save", Toast.LENGTH_SHORT).show()
//        activity!!.finish()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v!!) {
            iv -> {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(
                    Intent.createChooser(pickPhoto, "Select Picture"),
                    RESULT_LOAD_IMAGE
                )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        selectedImage = imageReturnedIntent!!.data
//        pathPhoto = selectedImage.path
//        Log.d("PhotoItemActivity Path", pathPhoto)
        if (requestCode === RESULT_LOAD_IMAGE && resultCode === Activity.RESULT_OK) {
            imageUri = data!!.data
            iv!!.setImageURI(imageUri)
        }
    }

}
