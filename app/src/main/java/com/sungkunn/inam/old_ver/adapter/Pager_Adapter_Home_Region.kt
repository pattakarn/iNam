package com.sungkunn.inam.old_ver.adapter



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class Pager_Adapter_Home_Region(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    internal var items: ArrayList<Fragment> = ArrayList()
    internal var itemsTitle: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return items!!.get(position)
    }

    override fun getCount(): Int {
        return items!!.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        items!!.add(fragment)
        itemsTitle!!.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itemsTitle.get(position)
    }

}