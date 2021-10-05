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


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: FragmentManager
    private lateinit var transaction: FragmentTransaction
    val announceFragment = AnnounceFragment()
    val profileFragment = ProfileFragment()
    val teacherFragment = TeacherFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // fragment
        manager = supportFragmentManager
        transaction = manager.beginTransaction()
        transaction.add(R.id.forFragment, announceFragment, Config.ANNOUNCE_FRAGMENT)
        transaction.commit()
//        hideFragment(fragment_1, fragment_2)
        binding.navigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)


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
        object :  NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.main -> {
                        showFragment(announceFragment,Config.ANNOUNCE_FRAGMENT)
                        hideFragment(teacherFragment, profileFragment)
                        return true
                    }

                    R.id.chat -> {
                        showFragment(teacherFragment, Config.TEACHER_FRAGMENT)
                        hideFragment(announceFragment, profileFragment)
                        return true
                    }

                    R.id.profile -> {
                        showFragment(profileFragment, Config.PROFILE_FRAGMENT)
                        hideFragment(announceFragment, teacherFragment)
                        return true
                    }
                }
                return false
            }
        }

    fun showFragment(frag: Fragment, tag: String) {
        transaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null) {
            Log.d("showFragment", "showFragment: $tag")
            transaction.add(R.id.forFragment, frag, tag)

        } else {
            transaction.show(frag)
        }
    }

    fun hideFragment(frag: Fragment, frag2: Fragment) {
        if (manager.findFragmentByTag(frag.tag) != null) transaction.hide(frag)
        if (manager.findFragmentByTag(frag2.tag) != null) transaction.hide(frag2)

        transaction.commit()
    }
}