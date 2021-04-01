package com.dev.gka.abda.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.gka.abda.DateUtil
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.FragmentDetailsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var movieTitle: String
    private lateinit var releaseDate: String
    private var rating: Int = 0
    private lateinit var overview: String
    private lateinit var posterPath: String
    private lateinit var backdropPath: String
    private lateinit var language: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieTitle = arguments?.getString("title")!!
        releaseDate = arguments?.getString("release")!!
        rating = arguments?.getInt("vote")!!
        overview = arguments?.getString("overview")!!
        language = arguments?.getString("language")!!
        posterPath = arguments?.getString("backdrop")!!
        backdropPath = arguments?.getString("poster")!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.textDetailTitle.text = movieTitle
        binding.textReleaseDate.text = DateUtil.formatDateString(releaseDate)
        binding.textRating.text = rating.toString()
        glideModule(backdropPath, binding.imageBackdrop)
        glideModule(posterPath, binding.imagePoster)


        if (language == "en") {
            binding.textLanguage.text = "English"
            binding.textSubtitle.text = "English"
        } else {
            binding.textLanguage.text = "Not Available"
            binding.textSubtitle.text = "Not Available"
        }

        return binding.root
    }

    private fun glideModule(path: String, imageView: ImageView) {
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/w500${path}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading)
            )
            .centerCrop()
            .into(imageView)
    }
}