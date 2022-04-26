package com.example.linteacher.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.linteacher.R
import com.example.linteacher.databinding.ActivityMainBinding
import com.example.linteacher.ui.main.announce.AnnounceFragment
import com.example.linteacher.ui.main.profile.ProfileFragment
import com.example.linteacher.ui.main.teacherline.TeacherFragment
import com.example.linteacher.util.Config
import com.google.android.material.navigation.NavigationBarView
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: FragmentManager
    private lateinit var transaction: FragmentTransaction
    private lateinit var announceFragment:AnnounceFragment
    private lateinit var profileFragment :ProfileFragment
    private lateinit var teacherFragment :TeacherFragment
    var list= mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // fragment
        var fragment: Fragment = AnnounceFragment()
        var tag:String=Config.ANNOUNCE_FRAGMENT

        if (savedInstanceState == null || supportFragmentManager.findFragmentByTag(Config.ANNOUNCE_FRAGMENT) == null) {
            Log.e("MainActivity", "onCreate: savedInstanceState ")
//        hideFragment(fragment_1, fragment_2)
            announceFragment= fragment as AnnounceFragment
            profileFragment = ProfileFragment()
            teacherFragment= TeacherFragment()
        } else {

            if (getVisibleFragment()?.tag==Config.ANNOUNCE_FRAGMENT){
                announceFragment =
                    (supportFragmentManager.findFragmentByTag(Config.ANNOUNCE_FRAGMENT) as AnnounceFragment);
            }
            if (getVisibleFragment()?.tag==Config.PROFILE_FRAGMENT){
                profileFragment =
                    (supportFragmentManager.findFragmentByTag(Config.PROFILE_FRAGMENT) as ProfileFragment);
            }
            if (getVisibleFragment()?.tag==Config.TEACHER_FRAGMENT){
                teacherFragment =
                    (supportFragmentManager.findFragmentByTag(Config.TEACHER_FRAGMENT) as TeacherFragment);
            }
            getVisibleFragment()?.tag.let {
                if (it != null) {
                    tag=it
                }
            }
            getVisibleFragment()?.let {
                fragment= getVisibleFragment()
            }

            Log.e("MainActivity", "onCreate: supportFragmentManager $fragment")
        }
        list.add(announceFragment)
        list.add(profileFragment)
        list.add(teacherFragment)
        manager = supportFragmentManager
        transaction = manager.beginTransaction()
        showFragment(fragment, tag)
        hideFragment(fragment)
        binding.navigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun getVisibleFragment(): Fragment {
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible) return fragment
        }
        return announceFragment
    }

//    fun getActivityChangeFragmentTeacher(item: TeacherLineResponse){
//        manager = supportFragmentManager
//        transaction = manager.beginTransaction()
//        hideSpeficFragment(teacherFragment)
//        val bundle = Bundle()
//        bundle.putSerializable("items",item)
//        teacherSecondFragment.setArguments(bundle)
//        showFragment(teacherSecondFragment,Config.TEACHER_SECOND_FRAGMENT)
//
//
//    }

    private fun hideSpeficFragment(frag: Fragment) {
        if (manager.findFragmentByTag(frag.tag) != null) transaction.hide(frag)
        transaction.commit()
    }

    //fragment
    private val mOnNavigationItemSelectedListener =
        object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.main -> {
                        Log.e("MainActivity", "onNavigationItemSelected: main")
                        showFragment(announceFragment, Config.ANNOUNCE_FRAGMENT)
                        hideFragment(announceFragment)
                        return true
                    }

                    R.id.chat -> {
                        Log.e("MainActivity", "onNavigationItemSelected: chat")
                        showFragment(teacherFragment, Config.TEACHER_FRAGMENT)
                        hideFragment(teacherFragment)
                        return true
                    }

                    R.id.profile -> {
                        Log.e("MainActivity", "onNavigationItemSelected: profile")
                        showFragment(profileFragment, Config.PROFILE_FRAGMENT)
                        hideFragment(profileFragment)
                        return true
                    }
                }
                return false
            }
        }

    fun showFragment(frag: Fragment, tag: String) {
        transaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null) {
            Log.e("MainActivity", "addFragment: $frag")
            transaction.add(R.id.forFragment, frag, tag)

        } else {
            Log.e("MainActivity", "showFrag" +
                    ": $frag")
            transaction.show(frag)
        }
    }

    fun hideFragment(nowFrag: Fragment) {
//        if (manager.findFragmentByTag(frag.tag) != null) transaction.hide(frag)
//        if (manager.findFragmentByTag(frag2.tag) != null) transaction.hide(frag2)
        for (fr in  list){
            if (fr!=nowFrag){
                Log.e("MainActivity", "hideFragment: $fr", )
                transaction.hide(fr)
            }
        }
        transaction.commit()
    }
}