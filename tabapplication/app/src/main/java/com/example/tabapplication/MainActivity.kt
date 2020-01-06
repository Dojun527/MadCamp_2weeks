package com.example.tabapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.ui.main.adapter.SectionsPagerAdapter
import com.facebook.login.LoginManager
import java.lang.AssertionError

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var btn_custom_logout = findViewById(R.id.btn_logout) as Button

        btn_custom_logout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                LoginManager.getInstance().logOut()
                val intent =
                    Intent(applicationContext, FacebookActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.setIcon(R.drawable.ic_perm_identity_24px)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_photo_library_24px)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_music_note_black_24dp)
        //val fab: FloatingActionButton = findViewById(R.id.fab)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if(Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }

    }
}