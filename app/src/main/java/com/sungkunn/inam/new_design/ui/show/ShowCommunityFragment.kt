package com.sungkunn.inam.new_design.ui.show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.*
import com.sungkunn.inam.new_design.model.*
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Comment_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Photo_Show_Hori_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Place_Hori_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Product_Hori_List

class ShowCommunityFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(communityItem: CommunityPackDao?) = ShowCommunityFragment().apply {
            arguments = Bundle().apply {
                putParcelable("item", communityItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            communityItem = it.getParcelable("item")
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
    }

    private lateinit var placeVM: PlaceViewModel
    private lateinit var productVM: ProductViewModel
    private lateinit var commentVM: CommentViewModel
    private lateinit var photoVM: PhotoViewModel
    private var userVM: UserViewModel? = null
    private var communityItem: CommunityPackDao? = null
    private var commentList: ArrayList<CommentDao>? = null
    private var userList: ArrayList<UserDao>? = null
    var inflater: LayoutInflater? = null
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    var TAG = "SHOW COMMUNITY"

    var toolbar: Toolbar? = null
    var tvToolbar: TextView? = null


    var rvPlace: RecyclerView? = null
    var rvProduct: RecyclerView? = null
    var adapt_place: RV_Adapter_Place_Hori_List? = null
    var adapt_product: RV_Adapter_Product_Hori_List? = null

    // ----------------- Photo -----------------
    var rvPhoto: RecyclerView? = null

    // ----------------- Rating -----------------
    var rb: RatingBar? = null
    var btnReview: Button? = null
    var tvRatingMean: TextView? = null
    var tvRatingSum: TextView? = null
    var tv5Star: TextView? = null
    var tv4Star: TextView? = null
    var tv3Star: TextView? = null
    var tv2Star: TextView? = null
    var tv1Star: TextView? = null
    var rvComment: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.inflater = inflater
        placeVM =
            ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        productVM =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        commentVM =
            ViewModelProviders.of(this).get(CommentViewModel::class.java)
        userVM =
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        photoVM =
            ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        var rootView = inflater.inflate(R.layout.show_community_fragment, container, false)
        init(rootView)

        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener(this)
        tvToolbar!!.setText(communityItem!!.data.community_name)

//        photoVM.getPhotoByItem(communityItem!!.id).observe(this, Observer {
//            if (it != null) {
//                var adapterPhoto = RV_Adapter_Photo_Show_Hori_List(it, fragmentManager!!, photoVM)
//                val llm = LinearLayoutManager(
//                    inflater!!.context,
//                    LinearLayoutManager.HORIZONTAL,
//                    false
//                )
//                rvPhoto!!.setLayoutManager(llm)
//                rvPhoto!!.setAdapter(adapterPhoto)
//
//            }
//
//        })
        if (communityItem!!.photo != null) {
            var adapterPhoto = RV_Adapter_Photo_Show_Hori_List(communityItem!!.photo, fragmentManager!!, photoVM)
            val llm = LinearLayoutManager(
                inflater!!.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvPhoto!!.setLayoutManager(llm)
            rvPhoto!!.setAdapter(adapterPhoto)

        }

        placeVM.getPlacePackAll().observe(this, Observer {
            adapt_place = RV_Adapter_Place_Hori_List(it, fragmentManager!!)
            val llm = LinearLayoutManager(inflater!!.context, LinearLayoutManager.HORIZONTAL, false)

            rvPlace!!.setLayoutManager(llm)
            rvPlace!!.setAdapter(adapt_place)
        })

        productVM.getProductPackAll().observe(this, Observer {
            adapt_product = RV_Adapter_Product_Hori_List(it, fragmentManager!!)
            val llm = LinearLayoutManager(inflater!!.context, LinearLayoutManager.HORIZONTAL, false)

            rvProduct!!.setLayoutManager(llm)
            rvProduct!!.setAdapter(adapt_product)
        })

        commentVM.getCommentByItem(communityItem!!.id).observe(this, Observer {
            commentList = it

            userVM!!.getUserByComment(commentList!!).observe(this, Observer {
                userList = it
                var adapt_product =
                    RV_Adapter_Comment_List(commentList!!, userList!!, fragmentManager!!)
                val llm = LinearLayoutManager(inflater!!.context)

                rvComment!!.setLayoutManager(llm)
                rvComment!!.setAdapter(adapt_product)
                var count = IntArray(5) { i -> 0 }
                var sum: Float = 0.0F
                for (temp in commentList!!) {
                    Log.d(TAG, "================== " + temp.data.rating)
                    var rating: Float = temp.data.rating!!.toFloat()
                    sum += rating
                    when (rating) {
                        in 1.0..1.9 -> {
                            count[0]++
                        }
                        in 2.0..2.9 -> {
                            count[1]++
                        }
                        in 3.0..3.9 -> {
                            count[2]++
                        }
                        in 4.0..4.9 -> {
                            count[3]++
                        }
                        5.0F -> {
                            count[4]++
                        }
                    }
                }


                var countAll = count[0] + count[1] + count[2] + count[3] + count[4]
                var mean: Float = sum / countAll
                tv5Star!!.setText("5 stars : " + count[4])
                tv4Star!!.setText("4 stars : " + count[3])
                tv3Star!!.setText("3 stars : " + count[2])
                tv2Star!!.setText("2 stars : " + count[1])
                tv1Star!!.setText("1 stars : " + count[0])
                tvRatingSum!!.setText(countAll.toString() + " reviews")
                tvRatingMean!!.setText(mean.toInt().toString() + " out of 5")
            })
        })

        btnReview!!.setOnClickListener(this)

        return rootView
    }

    fun init(rootView: View) {
        toolbar = rootView.findViewById(R.id.toolbar)
        tvToolbar = rootView.findViewById(R.id.toolbar_title)

        rvPlace = rootView.findViewById(R.id.rv_place)
        rvProduct = rootView.findViewById(R.id.rv_product)

        // ----------------- Photo -----------------
        rvPhoto = rootView.findViewById(R.id.photo_rv)

        // ----------------- Rating -----------------
        rb = rootView.findViewById(R.id.rating_bar)
        btnReview = rootView.findViewById(R.id.btn_review)
        tvRatingMean = rootView.findViewById(R.id.tv_rating_mean)
        tvRatingSum = rootView.findViewById(R.id.tv_rating_sum)
        tv5Star = rootView.findViewById(R.id.tv_5star)
        tv4Star = rootView.findViewById(R.id.tv_4star)
        tv3Star = rootView.findViewById(R.id.tv_3star)
        tv2Star = rootView.findViewById(R.id.tv_2star)
        tv1Star = rootView.findViewById(R.id.tv_1star)
        rvComment = rootView.findViewById(R.id.rv_comment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v) {
            btnReview -> {
                var layout_popup: View = inflater!!.inflate(R.layout.dialog_comment, null)
                var ratingBar: RatingBar = layout_popup.findViewById(R.id.rating)
                var tvComment: TextInputEditText = layout_popup.findViewById(R.id.et_comment)
                val builder = AlertDialog.Builder(inflater!!.context)
                builder.setView(layout_popup)
                    .setTitle("Comment")
//                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        var rating = ratingBar.rating
                        var comment = tvComment.text.toString()
                        var commentItem = Comment(
                            communityItem!!.id,
                            currentUser!!.uid,
                            comment,
                            rating.toString()
                        )
                        commentVM.addComment(commentItem)
                        Toast.makeText(
                            inflater!!.context,
                            "Comment success.",
                            Toast.LENGTH_SHORT
                        ).show()
//                        productOrderVM!!.deleteProductOrder(items!!.get(position))
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                positiveButton.setTextColor(
                    ContextCompat.getColor(
                        inflater!!.context,
                        R.color.colorSecondary
                    )
                )
                negativeButton.setTextColor(
                    ContextCompat.getColor(
                        inflater!!.context,
                        R.color.colorSecondary
                    )
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

}
