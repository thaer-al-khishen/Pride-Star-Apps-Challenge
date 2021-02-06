package com.example.pridestarappschallenge.fragments

import android.os.Bundle
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pridestarappschallenge.R
import com.example.pridestarappschallenge.models.StoriesResult
import com.example.pridestarappschallenge.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_story_details.*

//Result of clicking on home item or bookmark item
class StoryDetailsFragment : Fragment() {

    private lateinit var storiesResult: StoriesResult

    companion object {
        fun newInstance(storiesResult: StoriesResult) = StoryDetailsFragment().putArgs {
            putSerializable("storiesResult", storiesResult)
        }

        fun getInstance(): StoryDetailsFragment {
            val fragment =
                StoryDetailsFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        private inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit): FRAGMENT =
            this.apply { arguments = Bundle().apply(argsBuilder) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storiesResult = (arguments?.getSerializable("storiesResult") as? StoriesResult)!!
        loadDetails()
    }

    private fun loadDetails() {
        //Populate textviews
        fragmentAbstract.text = storiesResult.abstract
        fragmentTitle.text = storiesResult.title
        Utils.formatISODate(storiesResult.publishedDate)?.let {
            val formattedDate= it.first +" at: " + it.second
            fragmentPublicationDate.text = formattedDate
        }
        fragmentUrl.text = storiesResult.url
        if (storiesResult.multimedia != null) {
            if (!TextUtils.isEmpty(storiesResult.multimedia!![3].url))
                Picasso.get().load(storiesResult.multimedia!![3].url).into(fragmentStoryDetailsImage)
        } else {
            Picasso.get().load(storiesResult.imageUrl).into(fragmentStoryDetailsImage)
        }
        fragmentUrl.movementMethod = LinkMovementMethod.getInstance()
    }
}
