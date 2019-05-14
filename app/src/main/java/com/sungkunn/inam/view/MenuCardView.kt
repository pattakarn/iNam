package com.sungkunn.inam.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.sungkunn.inam.R
import kotlinx.android.synthetic.main.menu_card_view.view.*

/**
 * TODO: document your custom view class.
 */
class MenuCardView : LinearLayout, View.OnClickListener {

    var tvTitle: String? = null
    var ivPhoto: Drawable? = null
    var tag: String? = null

    var mClickListener: MenuClickListener? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        LinearLayout.inflate(context, R.layout.menu_card_view, this)
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MenuCardView, defStyle, 0
        )

        tvTitle = a.getString(R.styleable.MenuCardView_menu_title)
        ivPhoto = a.getDrawable(R.styleable.MenuCardView_menu_photo)

        a.recycle()
        setupView()
//        if (attrs != null) {
//            var typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuCardView)
//
//            title = typedArray.getString(R.styleable.MenuCardView_menu_title) as Nothing?
//            typedArray.recycle()
////            setupView()
//        }
    }

    private fun setupView(){
        menuPhoto.setOnClickListener(this)
        setTitle(tvTitle)
        setPhoto(ivPhoto)
    }

    fun setTitle(text: String?) {
        this.tvTitle = text
        menuTitle.setText(tvTitle)
    }

    fun getTitle(): String {
        return menuTitle.text.toString()
    }

    fun setPhoto(photo: Drawable?){
        this.ivPhoto = photo
        menuPhoto.setImageDrawable(photo)
    }

    override fun onClick(v: View?) {
        if (v == menuPhoto){
            onPhotoClick(v)
        }
    }

    fun setMenuClickListener(listener: MenuClickListener, tag: String) {
        this.mClickListener = listener
        this.tag = tag
    }

    fun onPhotoClick(v: View?) {
        if (mClickListener != null){
            mClickListener!!.onPhotoClick(v, tag)
        }
    }

    interface MenuClickListener {
        fun onPhotoClick(v: View?, tag: String?)
    }


}
