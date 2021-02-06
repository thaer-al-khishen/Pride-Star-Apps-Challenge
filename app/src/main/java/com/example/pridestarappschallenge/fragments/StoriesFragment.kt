package com.example.pridestarappschallenge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pridestarappschallenge.MainActivity
import com.example.pridestarappschallenge.R
import com.example.pridestarappschallenge.adapters.HomeAdapter
import com.example.pridestarappschallenge.models.BookmarkedResult
import com.example.pridestarappschallenge.models.StoriesResult
import com.example.pridestarappschallenge.viewmodels.BookmarksViewModel
import com.example.pridestarappschallenge.viewmodels.HomeStoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stories.*
import java.net.UnknownHostException

//Home stories page of the app
@AndroidEntryPoint
class StoriesFragment : Fragment() {

    //Instantiate viewmodels, adapters, and lists
    private val viewModel by viewModels<HomeStoriesViewModel>()
    private val bookmarksViewModel by viewModels<BookmarksViewModel>()
    private lateinit var homeAdapter: HomeAdapter
    private val homeList: ArrayList<StoriesResult> = ArrayList()
    private var bookmarksList: List<BookmarkedResult> = ArrayList()

    companion object {
        fun getInstance(): StoriesFragment {
            val fragment =
                StoriesFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = StoriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpViewModel()
        setPullToRefresh()
        //Execute operation on a different thread to avoid blocking the ui thread
        Thread {
            bookmarksList = bookmarksViewModel.getBookmarkedAsList()!!
        }.start()
        viewModel.getHome()
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activity)

        homeAdapter = HomeAdapter(homeList, object : HomeAdapter.INewsItemSelected {
            override fun onNewsItemsClicked(position: Int) {
                (activity as MainActivity).switch(
                    MainActivity.Companion.FragmentType.STORYDETAILS,
                    (homeList[position])
                )
            }

            override fun onBookmarkIconClicked(position: Int, storiesResult: StoriesResult) {

                //Convert result to bookmarked result so that it can be added to the bookmarks section
                val bookmarkedResult = storiesResult.multimedia?.get(3)?.let {
                    BookmarkedResult(
                        storiesResult.url, storiesResult.abstract, it.url,
                        storiesResult.publishedDate, storiesResult.title
                    )
                }

                //Bookmark the story if it is not bookmarked
                if (!storiesResult.isBookmarked) {
                    if (bookmarkedResult != null) {
                        bookmarksViewModel.insertBookmarked(bookmarkedResult)
                    }
                    
                    //Execute operation on a different thread to avoid blocking the ui thread
                    Thread {
                        bookmarksList = bookmarksViewModel.getBookmarkedAsList()!!
                    }.start()
                } else {
                    //Delete the story from the bookmarks section if it is bookmarked
                    bookmarksViewModel.deleteBookmarked(storiesResult.url)

                    //Execute operation on a different thread to avoid blocking the ui thread
                    Thread {
                        bookmarksList = bookmarksViewModel.getBookmarkedAsList()!!
                    }.start()
                }
            }

        })
        recycler_view.adapter = homeAdapter
    }

    private fun setUpViewModel() {

        //Observe the value of the home attribute in the viewmodel and populate recyclerview accordingly
        viewModel.home.observe(viewLifecycleOwner) {
            it?.let {
                if (it.throwable is UnknownHostException) {
                    no_internet_layout.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                    empty_layout.visibility = View.GONE
                } else {
                    it.data?.let {
                        if (it.isEmpty()) {
                            no_internet_layout.visibility = View.GONE
                            empty_layout.visibility = View.VISIBLE
                            recycler_view.visibility = View.GONE

                        } else {
                            no_internet_layout.visibility = View.GONE
                            recycler_view.visibility = View.VISIBLE
                            empty_layout.visibility = View.GONE
                            homeList.clear()
                            if (!bookmarksList.isEmpty()) {
                                for (custom in it) {
                                    for (bookmarks in bookmarksList) {
                                        if (custom.url == bookmarks.url) {
                                            custom.isBookmarked = true
                                        }
                                    }
                                    homeList.add(custom)
                                }
                                homeAdapter.notifyDataSetChanged()
                            }
                            else {
                                homeList.addAll(it )
                                homeAdapter.notifyDataSetChanged()
                            }

                        }
                    }
                }
                refresh.isRefreshing = false
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //Execute operation on a different thread to avoid blocking the ui thread
        Thread {
            bookmarksList = bookmarksViewModel.getBookmarkedAsList()!!
        }.start()
        viewModel.getHome()
    }

    private fun setPullToRefresh() {
        refresh.setOnRefreshListener {
            viewModel.getHome()
        }
    }

}