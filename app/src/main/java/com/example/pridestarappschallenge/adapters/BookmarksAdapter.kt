package com.example.pridestarappschallenge.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pridestarappschallenge.R
import com.example.pridestarappschallenge.databinding.StoriesGridRowBinding
import com.example.pridestarappschallenge.models.BookmarkedResult
import com.example.pridestarappschallenge.utils.Utils
import com.squareup.picasso.Picasso

//Create adapter for bookmarks page
class BookmarksAdapter (private val home: List<BookmarkedResult>, val iNewsItemSelected: INewsItemSelected) :
    RecyclerView.Adapter<BookmarksAdapter.NewsItemViewHolder>() {

    override fun getItemCount(): Int {
        return home.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_stories_grid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val model = home[position]

        //Assign network model value to textview
        holder.binding.titleText.text = model.title

        //Format date properly
        Utils.formatISODate(model.publishedDate)?.let {
            val formattedDate= it.first +" at: " + it.second
            holder.binding.dateText.text=formattedDate
        }

        //Load an image if it exists
        if (!TextUtils.isEmpty(model.multimedia))
            Picasso.get().load(model.multimedia).placeholder(R.drawable.news_ic_placeholder)
                .into(holder.binding.newsImage)

        //Onclick listener for clicking on the item to navigate to details page
        holder.binding.parentLayout.setOnClickListener {
            iNewsItemSelected.onNewsItemsClicked(position)
        }

        //Onclick listener for clicking on the bookmark icon to add the item to the bookmarks section
        holder.binding.bookmarkIcon.setOnClickListener {
            iNewsItemSelected.onBookmarkIconClicked(position, model)
            holder.binding.bookmarkIcon.setImageResource(R.drawable.ic_data)
        }
    }

    //Databinding with adapter
    class NewsItemViewHolder(val binding: StoriesGridRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface INewsItemSelected {
        fun onNewsItemsClicked(position: Int)
        fun onBookmarkIconClicked(position: Int, bookmarkedResult: BookmarkedResult)
    }
}
