package com.example.pridestarappschallenge.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

//Use as a blueprint for the MainActivity though not necessary in this particular case
abstract class BaseActivity: AppCompatActivity() {

    //The function that implements the switching of fragments with replacement
    fun replaceFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment).addToBackStack("root_fragment").commit()
        supportFragmentManager.addOnBackStackChangedListener {
        }
    }

}
