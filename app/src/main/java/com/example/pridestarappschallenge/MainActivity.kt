package com.example.pridestarappschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.pridestarappschallenge.base.BaseActivity
import com.example.pridestarappschallenge.fragments.HomeFragment
import com.example.pridestarappschallenge.fragments.StoriesFragment
import com.example.pridestarappschallenge.fragments.StoryDetailsFragment
import com.example.pridestarappschallenge.models.StoriesResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    //Instantiate fragments
    private val homeFragment = HomeFragment()
    private var currentFragment: FragmentType =
        FragmentType.HOME

    companion object {
        enum class FragmentType {
            HOME,
            STORYDETAILS
        }

        private const val BACK_STACK_ROOT_TAG = "root_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showHomeFragment()
        }
    }

    //Method to navigate between fragments
    fun switch(
        type: FragmentType,
        storiesResult: StoriesResult?
    ) {
        when (type) {

            FragmentType.HOME -> {
                popBackStack()
            }

            FragmentType.STORYDETAILS -> {
                storiesResult?.let {
                    replaceFragment(R.id.mainContainer, StoryDetailsFragment.newInstance(it))
                }
            }

        }
        currentFragment = type
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, homeFragment)
            .commit()
        supportFragmentManager.executePendingTransactions()
    }

    private fun popBackStack() {
        supportActionBar?.hide()
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}