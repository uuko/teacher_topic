package com.example.linteacher.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.linteacher.ui.main.MainActivity

object ActivityNavigator{

    fun startActivity(activityClass: Class<out Activity>, activity: FragmentActivity) {
        val intent = Intent(activity, activityClass)
        activity.startActivity(intent)
    }
    fun startActivity(activityClass: Class<out Activity>, activity: Activity) {
        val intent = Intent(activity, activityClass)
        activity.startActivity(intent)
    }

    fun openActivity(resultLauncher: ActivityResultLauncher<Intent>
                             ,activityClass: Class<out Activity>
                             ,activity: AppCompatActivity) {
        val intent = Intent(activity, activityClass)
        resultLauncher.launch(intent)

    }
     fun openActivityWithData(resultLauncher: ActivityResultLauncher<Intent>
                                     ,activityClass: Class<out Activity>
                                     ,activity: AppCompatActivity
                                , bundle: Bundle) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        resultLauncher.launch(intent)

    }
    fun openFragmentActivityWithData(resultLauncher: ActivityResultLauncher<Intent>
                             ,activityClass: Class<out Activity>
                             ,activity: FragmentActivity
                             , bundle: Bundle) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        resultLauncher.launch(intent)

    }
    fun startActivityWithData(
        activityClass: Class<out Activity>,
        bundle: Bundle, activity: FragmentActivity
    ) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }
    fun startActivityWithData(
        activityClass: Class<out Activity>,
        bundle: Bundle, activity: AppCompatActivity
    ) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    fun startActivityWithDataAndAnimation(
        activityClass: Class<out Activity>,
        bundle: Bundle,
        inAnimation: Int,
        outAnimation: Int,
        activity: AppCompatActivity
    ) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        activity.startActivity(intent)
        activity.overridePendingTransition(inAnimation, outAnimation)
    }

    fun addFragment(
        containerId: Int,
        fragment: Fragment,
        activity: AppCompatActivity
    ) {
        activity.supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        activity: AppCompatActivity
    ) {
        activity.supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    fun finishActivityWithAnimation(
        inAnimation: Int,
        outAnimation: Int,
        activity: AppCompatActivity
    ) {
        activity.finish()
        activity.overridePendingTransition(inAnimation, outAnimation)
    }

}