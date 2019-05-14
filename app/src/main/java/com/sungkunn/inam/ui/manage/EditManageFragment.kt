package com.sungkunn.inam.ui.manage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.AppBarLayout
import com.sungkunn.inam.R

class EditManageFragment : Fragment() {

    var appBar: AppBarLayout? = null
    var toolbar: Toolbar? = null
    var fragment: Fragment? = null

    var category: String? = ""

    companion object {
        fun newInstance(category: String?) =
            EditManageFragment().apply {
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
        var rootView = inflater.inflate(R.layout.edit_manage_fragment, container, false)
        appBar = rootView.findViewById(R.id.app_bar_1)
        toolbar = rootView.findViewById(R.id.toolbar_1)


//        toolbar!!.setTitle(category!!.capitalize())
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

        fragmentManager!!.beginTransaction()
            .replace(R.id.editContextContainer, ProductPreferenceFragment())
            .addToBackStack(null)
            .commit()

        val a = PreferenceManager.getDefaultSharedPreferences(inflater.context)


        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    class MySettingsSampleFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref_general, rootKey)
//            addPreferencesFromResource(R.xml.pref_general)
        }
    }

    class ProductPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref_product_data, rootKey)

            val market = findPreference<Preference>("market")
            val zone = findPreference<Preference>("zone")
            val shop = findPreference<Preference>("shop")

            val name = findPreference<EditTextPreference>("name")
            val type = findPreference<EditTextPreference>("type")
            val detail = findPreference<EditTextPreference>("detail")
            val price = findPreference<EditTextPreference>("price")

            market!!.setOnPreferenceClickListener {
                Toast.makeText(activity, "Market", Toast.LENGTH_SHORT).show()
                return@setOnPreferenceClickListener true
            }


        }
    }


}
