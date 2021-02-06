package com.example.pridestarappschallenge.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pridestarappschallenge.MainActivity
import com.example.pridestarappschallenge.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

//Create fragment responsible for the tabs
class HomeFragment : Fragment() {
    companion object {
        fun getInstance(): HomeFragment {
            val fragment =
                HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()

        //Add 2 tabs to the tablayout
        tabLayout?.addTab(tabLayout.newTab())
        tabLayout?.addTab(tabLayout.newTab())

        //Setup the layout manager for the viewpager
        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewPager?.adapter = PagerAdapter(childFragmentManager, tabLayout!!.tabCount)

        //Implement the ability to perform an action after pressing the tab
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)

        //Set the text for each tab
        tabLayout.getTabAt(0)?.text = "Home";
        tabLayout.getTabAt(1)?.text = "Bookmarks"

        //Add custom separator between tabs
        val root = tabLayout.getChildAt(0)
        if (root is LinearLayout) {
            (root).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(resources.getColor(R.color.darkRed))
            drawable.setSize(2, 1)
            (root).dividerPadding = 10
            (root).dividerDrawable = drawable
        }

        //Specify what to do after each tab is selected, unselected, and reselected.
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    class PagerAdapter(fm: FragmentManager?, var mNumOfTabs: Int) :
        FragmentPagerAdapter(fm!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {

                //This is where you declare the destination fragments which will be the result of the onclick of every tab

                0 -> StoriesFragment.newInstance()
                1 -> BookmarksFragment.newInstance()

                else -> StoriesFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            //The number of tabs to be used
            return 2
        }
    }
}
