package com.sungkunn.inam.new_design.ui.manage.photo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.internal.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.istyleglobalnetwork.talatnoi.rv.adapter.RV_Adapter_Photo_List
import com.sungkunn.inam.R
import com.sungkunn.inam.activity.PhotoItemActivity
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.model.Photo
import com.sungkunn.inam.new_design.model.PhotoDao

class NewEditPhotoFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance(itemId: String?, itemName: String) = NewEditPhotoFragment().apply {
            //            Log.d("NewEditPlaceFragment", "============================ " + communityItem!!.data.name)
            arguments = Bundle().apply {
                putString("id", itemId)
                putString("name", itemName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getString("id")
            itemName = it.getString("name")
        }
    }

    private lateinit var photoVM: PhotoViewModel
    private lateinit var adapter: RV_Adapter_Photo_List
    private var arrItem: ArrayList<PhotoDao> = ArrayList()
    private var photoItem: PhotoDao? = null
    private var itemId: String? = ""
    private var itemName: String? = ""
    private val RESULT_LOAD_IMAGE = 1

    var inflater: LayoutInflater? = null
    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        var rootView = inflater.inflate(R.layout.new_edit_photo_fragment, container, false)
        this.inflater = inflater
        init(rootView)

        toolbar!!.inflateMenu(R.menu.menu_photo)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_black)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

        photoVM.getPhotoByItem(itemId!!).observe(this, Observer {
            if (it != null) {
                arrItem = it
                adapter = RV_Adapter_Photo_List(arrItem, fragmentManager!!, photoVM)
//            val llm = LinearLayoutManager(inflater!!.context)
//            rv!!.setLayoutManager(llm)
                rv!!.setLayoutManager(GridLayoutManager(inflater.context, 2))
                rv!!.setAdapter(adapter)
            }
        })

        return rootView
    }

    fun init(rootView: View) {
        rv = rootView.findViewById(R.id.rv)
        ll = rootView.findViewById(R.id.ll)
        toolbar = rootView.findViewById(R.id.toolbar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        Log.d("New Edit Photo Fragment", "close fragment")
        //fragmentManager!!.popBackStack()
        when (v!!) {
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
            R.id.action_add -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                intent.putExtra(Intent., 3)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    RESULT_LOAD_IMAGE
                )
//                var intent = Intent(inflater!!.context, PhotoItemActivity::class.java)
//                intent.putExtra("id", itemId)
//                intent.putExtra("name", itemName)
//                inflater!!.context.startActivity(intent)

            }
        }
        return true
    }

    private fun savePhoto(uri: Uri, position: Int) {
        val snackbar: Snackbar =
            Snackbar.make(
                ll!!,
                resources.getString(R.string.save_process),
                Snackbar.LENGTH_INDEFINITE
            )

        snackbar.show()
//        Toast.makeText(inflater!!.context, "Save" + photoVM.getNewId(), Toast.LENGTH_SHORT).show()
        if (photoItem == null) {

            val storage = FirebaseStorage.getInstance().reference
            var photoId = photoVM.getNewId()
            val fileToUpload: StorageReference = storage.child("photo").child(photoId)
            fileToUpload.putFile(uri).addOnSuccessListener {
                fileToUpload.downloadUrl.addOnSuccessListener {
                    var temp = Photo(
                        itemId,
                        "user_id",
                        it.toString(),
                        if (arrItem.size == 0 && position == 0) "main" else ""
                    )
//                    photoVM.addPhoto(temp!!)
                    photoVM.savePhoto(PhotoDao(photoId, temp))
                    snackbar.dismiss()
                    Snackbar.make(ll!!, resources.getString(R.string.save_success), Snackbar.LENGTH_SHORT).show()
                }

            }
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === RESULT_LOAD_IMAGE && resultCode === Activity.RESULT_OK) {
            if (data!!.getClipData() != null) {
                val storage = FirebaseStorage.getInstance().reference
                val totalItemsSelected: Int = data.getClipData().getItemCount()
                var text = ""
                for (i in 0 until totalItemsSelected) {
                    text += data.getClipData().getItemAt(i).getUri().toString() + "\n"
                    val fileUri: Uri = data.getClipData().getItemAt(i).getUri()
                    savePhoto(fileUri, i)
//                    val fileName: String = getFileName(fileUri)
//                    fileNameList.add(fileName)
//                    fileDoneList.add("uploading")
//                    uploadListAdapter.notifyDataSetChanged()
//                    val fileToUpload: StorageReference = storage.child("Images").child(fileName)
//                    fileToUpload.putFile(fileUri).addOnSuccessListener {
//                        fileDoneList.remove(i)
//                        fileDoneList.add(i, "done")
//                        uploadListAdapter.notifyDataSetChanged()
//                    }
                }
//                savePhoto()
//                Toast.makeText(inflater!!.context, "Selected Multiple Files\n" + text, Toast.LENGTH_SHORT).show();
            } else if (data!!.getData() != null) {
                Toast.makeText(inflater!!.context, "Selected Single File", Toast.LENGTH_SHORT)
                    .show()
//                Log.d("Edit Photo", data.data)
                savePhoto(data.data, 0)
            }
        }
    }

}
