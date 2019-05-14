package com.sungkunn.inam

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sungkunn.inam.fragment.Home2Fragment
import com.sungkunn.inam.fragment.MoreFragment
import com.sungkunn.inam.fragment.PinFragment

class HomeNewActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private lateinit var container: FrameLayout

    val fragment1: Fragment = Home2Fragment.newInstance(R.id.container, "")
    val fragment2: Fragment = PinFragment.newInstance("", "")
    val fragment3: Fragment = MoreFragment.newInstance(R.id.container, "")
    var active: Fragment = fragment1

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                textMessage.setText(R.string.title_home)
                supportFragmentManager.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_pin -> {
                supportFragmentManager.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2
//                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_more -> {
                supportFragmentManager.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3
//                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val container: FrameLayout = findViewById(R.id.homeNewContainer)



        supportFragmentManager.beginTransaction()
            .add(R.id.homeNewContainer, fragment3, "more")
            .hide(fragment3)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.homeNewContainer, fragment2, "pin")
            .hide(fragment2)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.homeNewContainer, fragment1, "home")
            .commit()
//        textMessage = findViewById(R.id.message)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
