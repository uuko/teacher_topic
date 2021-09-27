package com.example.linteacher.util

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    open fun onFragmentBackPressed() {
        // add code in super class when override
    }
}