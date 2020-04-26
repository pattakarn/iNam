package com.sungkunn.inam.old_ver.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.adapter.RV_Adapter_Home_Menu

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home2Fragment : Fragment() {
    private var param1: Int? = null
    private var param2: String? = null

    var rv: RecyclerView? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_home2, container, false)
        rv = rootView.findViewById<RecyclerView>(R.id.rv)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)

        var listItem = this.resources.getStringArray(R.array.region_data)
        val adapter = RV_Adapter_Home_Menu(listItem, fragmentManager!!)
        val llm = GridLayoutManager(inflater.context, 2)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            Home2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
