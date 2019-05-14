package com.sungkunn.inam.ui.manage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_Manage_Main
import com.sungkunn.inam.fragment.LoginDialogFragment


class ManageFragment : Fragment() {

    var appBar: AppBarLayout? = null
    var toolbar: Toolbar? = null
    var fab: FloatingActionButton? = null
    var category: String? = ""

    companion object {
        fun newInstance(category: String?) =
            ManageFragment().apply {
                arguments = Bundle().apply {
                    putString("category", category)
                }
            }
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView = inflater.inflate(R.layout.manage_fragment, container, false)
        appBar = rootView.findViewById(R.id.app_bar)
        toolbar = rootView.findViewById(R.id.toolbar)
        var rv = rootView.findViewById<RecyclerView>(R.id.rv_manage)
        var btn = rootView.findViewById<Button>(R.id.button)

        btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d("========", "=======================")
                openDialog()
            }
        })


        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("ManageFragment", "close fragment")
                //fragmentManager!!.popBackStack()
                if (fragmentManager!!.backStackEntryCount > 0) {
                    fragmentManager!!.popBackStack()
                } else {
                    activity!!.finish()
                }
            }
        })

//        var listItem = ArrayList<MapMenu>()
        var listItem = this.resources.getStringArray(R.array.manage_menu)
//        for (item in arr)
//            listItem.add(MapMenu(item))


        val adapter = RV_Adapter_Manage_Main(listItem, fragmentManager!!)
//        val adapter = RV_Adapter_Manage_Menu(listItem, fragmentManager!!)
        val llm = LinearLayoutManager(inflater.context)

        rv.setLayoutManager(llm)
        rv.setAdapter(adapter)




        return rootView
    }

    private fun openDialog() {
        LoginDialogFragment.display(fragmentManager)
//        fragmentManager!!.beginTransaction()
//            .replace(container, EditManageFragment.ProductPreferenceFragment())
//            .addToBackStack(null)
//            .commit()
//        fragmentManager!!.beginTransaction()
//            .replace(container, LoginDialogFragment.newInstance())
//            .addToBackStack(null)
//            .commit()
//        fragmentManager!!.beginTransaction().replace(container, LoginDialogFragment.newInstance()).commit()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
