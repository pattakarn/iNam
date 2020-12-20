package com.sungkunn.inam.new_design.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.firestore.CommunityViewModel
import com.sungkunn.inam.new_design.firestore.PhotoViewModel
import com.sungkunn.inam.new_design.model.OrderDao
import com.sungkunn.inam.new_design.model.PhotoDao
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Community_List
import com.sungkunn.inam.new_design.rv.adapter.RV_Adapter_Community_Pack_List

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    ChipGroup.OnCheckedChangeListener {

//    private lateinit var homeViewModel: HomeViewModel
    private lateinit var communityVM: CommunityViewModel
    private lateinit var photoVM: PhotoViewModel
    private lateinit var adapter: RV_Adapter_Community_Pack_List

    private var communityList: ArrayList<OrderDao>? = null
    private var photoList: ArrayList<PhotoDao>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        homeViewModel =
//            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        communityVM =
            ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        photoVM = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val rv: RecyclerView = root.findViewById(R.id.rv)
        val sv: SearchView = root.findViewById(R.id.search)
        val chip_group: ChipGroup = root.findViewById(R.id.chip_group)

        sv.setOnQueryTextListener(this)

//        homeViewModel.getData().observe(this, Observer {
//            adapter = RV_Adapter_Place_List(it, fragmentManager!!)
//            val llm = LinearLayoutManager(inflater.context)
//
//            rv!!.setLayoutManager(llm)
//            rv!!.setAdapter(adapter)
//        })
//        communityVM.getCommunityAll().observe(this, Observer {
//            communityList = it
//
//            var strList: ArrayList<String> = ArrayList()
//            for (temp in it){
//                strList.add(temp.id)
//            }
//            photoVM.getPhotoByItemList(strList).observe(this, Observer {
//                photoList = it
//
//                adapter = RV_Adapter_Community_Pack_List(communityList!!, photoList!!, fragmentManager!!)
//                val llm = LinearLayoutManager(inflater!!.context)
//
//                rv!!.setLayoutManager(llm)
//                rv!!.setAdapter(adapter)
//
//            })
//
//        })

        communityVM.getCommunityPackAll().observe(this, Observer {

            adapter = RV_Adapter_Community_Pack_List(it, fragmentManager!!)
            val llm = LinearLayoutManager(inflater!!.context)

            rv!!.setLayoutManager(llm)
            rv!!.setAdapter(adapter)

        })


        chip_group.setOnCheckedChangeListener(this)

        return root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText)
        return false
    }

    override fun onCheckedChanged(p0: ChipGroup?, p1: Int) {
        Log.d("Chip", "========================== " + p1)
        when (p1) {
            2 -> adapter.filter("community")
            3 -> adapter.filter("company")
            4 -> adapter.filter("government")
            else -> adapter.filter("")
        }
    }

}