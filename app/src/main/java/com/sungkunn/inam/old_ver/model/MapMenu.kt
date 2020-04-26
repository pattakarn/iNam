package com.sungkunn.inam.old_ver.model

import androidx.fragment.app.Fragment
import com.sungkunn.inam.old_ver.fragment.manage.list.MarketListFragment

class MapMenu {

    var name: String = ""
    var type: String = ""
    var fragment: Fragment? = null

    constructor(name: String){
        this.name = name
        this.type = selectType()
        this.fragment = selectFragment()
    }

    constructor(name: String, type: String, fragment: Fragment){
        this.name = name
        this.type = type
        this.fragment = fragment
    }

    private fun selectType(): String {
        if (name.get(0).equals('*'))
            return "title"
        return "menu"
    }

    private fun selectFragment(): Fragment {
        return MarketListFragment.newInstance(1)
    }
}