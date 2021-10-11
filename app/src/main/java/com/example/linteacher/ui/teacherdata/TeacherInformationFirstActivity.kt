package com.example.linteacher.ui.teacherdata

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.linteacher.R
import com.example.linteacher.databinding.ActivityTeacherInformationFirst2Binding

import com.google.android.material.navigation.NavigationView
import kotlin.math.log


class TeacherInformationFirstActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTeacherInformationFirst2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeacherInformationFirst2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarTeacherInformationFirst.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController =
            findNavController(R.id.nav_host_fragment_content_teacher_information_first)
        val loginId=intent.getIntExtra("loginId",0)
        val bundle= bundleOf("loginId" to loginId)
        navController.navigate(R.id.nav_home_profile_first,bundle)
//        val action = Fragment.fromBundle()
//        // navigate to FragmentB
//        navController.navigate(action)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_profile_first, R.id.nav_gallery
                ,R.id.nav_license,R.id.nav_award,R.id.nav_event,R.id.nav_gov,R.id.nav_adamic,R.id.nav_book,
                    R.id.nav_patent,R.id.nav_journal,R.id.nav_paper
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.teacher_information_first, menu)
        return true
    }

    override fun onBackPressed() {

        Log.d("TeacherInforFstActivity", "onBackPressed: ")
        setResult(Activity.RESULT_OK)
        finish()
        super.onBackPressed()
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_teacher_information_first)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}