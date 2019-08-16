package com.sungkunn.inam.example

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.adapter.Pager_Adapter_Home_Region
import kotlinx.android.synthetic.main.activity_home_region.*


class HomeRegionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sungkunn.inam.R.layout.activity_home_region)
        setSupportActionBar(home_region_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        var region = intent.getStringExtra("region")
        Log.d("HomeRegionActivity", region)
//        home_region_toolbar_layout.title = region
//        setTitle(region.capitalize())

//        var mData = resources.getStringArray(R.array.region_data)
//        var adapSpin = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mData)
//        spin_region.adapter = adapSpin
//
//        var listData = ArrayList<Market>()
//        listData.add(Market("Market 1"))
//        listData.add(Market("Market 2"))
//        listData.add(Market("Market 3"))
//        listData.add(Market("Market 4"))
//        listData.add(Market("Market 5"))
//        listData.add(Market("Market 6"))
//        listData.add(Market("Market 7"))
//        listData.add(Market("Market 8"))
//        listData.add(Market("Market 9"))
//        listData.add(Market("Market 10"))
//
//        val adapMarket = RV_Adapter_Market(listData)
//        val llm = LinearLayoutManager(this)
//        val glm = GridLayoutManager(this, 2)
//
//        home_region_rv.layoutManager = glm
//        home_region_rv.adapter = adapMarket

        val adapter = Pager_Adapter_Home_Region(supportFragmentManager)
//        val pager = rootView.findViewById<View>(R.id.pager_solution_main) as ViewPager
//        val tabLayout = rootView.findViewById<View>(R.id.tabs) as TabLayout
//        tabLayout.setupWithViewPager(pager)
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Education"), "Education")
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Health"), "Health")
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Environment"), "Environment")
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Training"), "Training")
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Occupation"), "Occupation")
//        adapter.addFragment(SolutionTabsFragmentNew.newInstance("Well-Being"), "Well-Being")
//        pager.adapter = adapter
//        pager.setOnTouchListener(View.OnTouchListener { v, event -> true })

        home_region_tabs.setupWithViewPager(home_region_pager)
//        adapter.addFragment(TravelFragment.newInstance("", ""), "Market")
//        adapter.addFragment(TravelFragment.newInstance("", ""), "Travel")
//        adapter.addFragment(TravelFragment.newInstance("", ""), "Network")
        home_region_pager.adapter = adapter
        home_region_pager.setOnTouchListener(View.OnTouchListener { v, event -> true })



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
