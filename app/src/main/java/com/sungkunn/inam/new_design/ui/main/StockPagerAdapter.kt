package com.sungkunn.inam.new_design.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.ProductDao

private val TAB_TITLES = arrayOf(
    R.string.tab_text_stock_1,
    R.string.tab_text_stock_2,
    R.string.tab_text_stock_3,
    R.string.tab_text_stock_4
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class StockPagerAdapter(private val context: Context,val productItem: ProductDao, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return StockTabFragment.newInstance(productItem, position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 4
    }
}