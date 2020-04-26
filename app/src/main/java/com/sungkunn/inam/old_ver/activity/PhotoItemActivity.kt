package com.sungkunn.inam.old_ver.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R


class PhotoItemActivity : AppCompatActivity(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    var key: String? = ""
    var name: String? = ""

    var toolbar: Toolbar? = null
    var iv1: ImageView? = null
    var iv2: ImageView? = null
    var iv3: ImageView? = null
    var iv4: ImageView? = null
    var iv5: ImageView? = null
    var ll: LinearLayout? = null

    var pathPhoto: String? = ""
    var selectedImage: ArrayList<Uri?> = ArrayList(5)

    var TAG = "Photo Item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_item)

        key = intent.extras.getString("key")
        name = intent.extras.getString("name")

        toolbar = findViewById(R.id.toolbar)
        ll = findViewById(R.id.ll)
        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        iv4 = findViewById(R.id.iv4)
        iv5 = findViewById(R.id.iv5)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        iv1!!.setOnClickListener(this)
        iv2!!.setOnClickListener(this)
        iv3!!.setOnClickListener(this)
        iv4!!.setOnClickListener(this)
        iv5!!.setOnClickListener(this)

        for (i in 0..4){
            selectedImage.add(null)
        }

        setListImage()


//        Log.d(TAG, "key : " + key + ", name : " + name)



    }

    fun setListImage(){
        val storageRef = FirebaseStorage.getInstance().reference
//        val folderRef = storageRef.child("photos")
//        val imageRef = folderRef.child("images/" + key + "_")
        storageRef.child("images/" + key + "_0").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                //                                            .placeholder(R.mipmap.ic_floating_market)
                .into(iv1!!)
        }.addOnFailureListener {
            // Handle any errors
        }
        storageRef.child("images/" + key + "_1").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                //                                            .placeholder(R.mipmap.ic_floating_market)
                .into(iv2!!)
        }.addOnFailureListener {
            // Handle any errors
        }
        storageRef.child("images/" + key + "_2").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                //                                            .placeholder(R.mipmap.ic_floating_market)
                .into(iv3!!)
        }.addOnFailureListener {
            // Handle any errors
        }
        storageRef.child("images/" + key + "_3").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                //                                            .placeholder(R.mipmap.ic_floating_market)
                .into(iv4!!)
        }.addOnFailureListener {
            // Handle any errors
        }
        storageRef.child("images/" + key + "_4").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                //                                            .placeholder(R.mipmap.ic_floating_market)
                .into(iv5!!)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    override fun onClick(v: View?) {
//        Log.d("PhotoItemActivity View", v.toString())
//        Log.d("PhotoItemActivity", "close activity")
        //fragmentManager!!.popBackStack()
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        if (v == iv1){
            startActivityForResult(pickPhoto, 1)//one can be replaced with any action code
        } else if (v == iv2){
            startActivityForResult(pickPhoto, 2)//one can be replaced with any action code
        } else if (v == iv3){
            startActivityForResult(pickPhoto, 3)//one can be replaced with any action code
        } else if (v == iv4){
            startActivityForResult(pickPhoto, 4)//one can be replaced with any action code
        } else if (v == iv5){
            startActivityForResult(pickPhoto, 5)//one can be replaced with any action code
        } else {
            finish()
        }

            //finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
//        selectedImage = imageReturnedIntent!!.data
//        pathPhoto = selectedImage.path
//        Log.d("PhotoItemActivity Path", pathPhoto)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                selectedImage[0] = imageReturnedIntent!!.data
                iv1!!.setImageURI(selectedImage[0])
            }
            2 -> if (resultCode == Activity.RESULT_OK) {
                selectedImage[1] = imageReturnedIntent!!.data
                iv2!!.setImageURI(selectedImage[1])
            }
            3 -> if (resultCode == Activity.RESULT_OK) {
                selectedImage[2] = imageReturnedIntent!!.data
                iv3!!.setImageURI(selectedImage[2])
            }
            4 -> if (resultCode == Activity.RESULT_OK) {
                selectedImage[3] = imageReturnedIntent!!.data
                iv4!!.setImageURI(selectedImage[3])
            }
            5 -> if (resultCode == Activity.RESULT_OK) {
                selectedImage[4] = imageReturnedIntent!!.data
                iv5!!.setImageURI(selectedImage[4])
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save ->
                uploadFile()
//                saveMarket()
//                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun uploadFile(){
        val storage = FirebaseStorage.getInstance()
        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child("mountains.jpg")

        // Create a reference to 'images/mountains.jpg'
        val mountainImagesRef = storageRef.child("images/mountains.jpg")

        // While the file names are the same, the references point to different files
        mountainsRef.name == mountainImagesRef.name // true
        mountainsRef.path == mountainImagesRef.path // false

//        var file = Uri.fromFile(File(pathPhoto))
        for (item in selectedImage) {
            if (item != null) {

                var file = item
//                val riversRef = storageRef.child("images/${item!!.lastPathSegment}")
                val riversRef = storageRef.child("images/" + key + "_" + selectedImage.indexOf(item))
                var uploadTask = riversRef.putFile(file!!)
                val snackbar: Snackbar =
                    Snackbar.make(ll!!, resources.getString(R.string.save_process), Snackbar.LENGTH_INDEFINITE)
                snackbar.show()

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Log.d(TAG + " Upload", "Error")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_fault), Snackbar.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Log.d(TAG + " Upload", "Success")
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }
            }
        }
    }
}
