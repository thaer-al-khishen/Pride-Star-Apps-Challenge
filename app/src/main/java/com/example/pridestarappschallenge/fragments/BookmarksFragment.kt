package com.example.pridestarappschallenge.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pridestarappschallenge.MainActivity
import com.example.pridestarappschallenge.R
import com.example.pridestarappschallenge.adapters.BookmarksAdapter
import com.example.pridestarappschallenge.models.BookmarkedResult
import com.example.pridestarappschallenge.models.StoriesResult
import com.example.pridestarappschallenge.viewmodels.BookmarksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bookmarks.*

@AndroidEntryPoint
class BookmarksFragment : Fragment(){

    //Instantiate adapter and viewmodel
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private val bookmarksViewModel by viewModels<BookmarksViewModel>()

    companion object {
        fun getInstance(): BookmarksFragment {
            val fragment =
                BookmarksFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
        fun newInstance() = BookmarksFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Populate recyclerview with users retrieved from the database
        bookmarksViewModel.getBookmarkedAsLiveData()?.observe(viewLifecycleOwner, { this.setUpRecyclerView(it) })
    }

    private fun setUpRecyclerView(homeList: List<BookmarkedResult>) {
        recycler_view.layoutManager = GridLayoutManager(activity, 2)
        bookmarksAdapter = BookmarksAdapter(homeList, object : BookmarksAdapter.INewsItemSelected {
            override fun onNewsItemsClicked(position: Int) {
                var bookmark = homeList[position]
                val bookmarkedResult = StoriesResult(
                    bookmark.customabstract, "", "", null, null, "", "", "", null,
                    null, null, bookmark.publishedDate, "", "", "", bookmark.title, "", "",
                    bookmark.url, false, bookmark.multimedia)
                (activity as MainActivity).switch(
                    MainActivity.Companion.FragmentType.STORYDETAILS,
                    (bookmarkedResult)
                )
            }

            override fun onBookmarkIconClicked(position: Int, bookmarkedResult: BookmarkedResult) {
                bookmarksViewModel.deleteBookmarked(bookmarkedResult.url)
            }

        })
        recycler_view.adapter = bookmarksAdapter
    }
}
