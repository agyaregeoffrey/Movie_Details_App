package com.dev.gka.abda.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.gka.abda.adapters.MovieAdapter
import com.dev.gka.abda.utilities.NavigationHost
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.FragmentHomeBinding
import com.dev.gka.abda.model.Result
import com.dev.gka.abda.screens.details.DetailsFragment
import com.dev.gka.abda.utilities.ApiStatus


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        initHomeFragmentDisplayContent()

        return binding.root
    }

    private fun initHomeFragmentDisplayContent() {
        apiStatus()
        viewModel.trending.observe(viewLifecycleOwner, Observer { data ->
            binding.recyclerTrending.apply {
                adapter = MovieAdapter(MovieAdapter.OnClickListener { result ->
                    viewModel.navigateToSelectedMovieDetails(result)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.popular.observe(viewLifecycleOwner, Observer { data ->
            binding.recyclerPopular.apply {
                adapter = MovieAdapter(MovieAdapter.OnClickListener { result ->
                    viewModel.navigateToSelectedMovieDetails(result)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.top.observe(viewLifecycleOwner, { data ->
            binding.recyclerTopRated.apply {
                adapter = MovieAdapter(MovieAdapter.OnClickListener { result ->
                    viewModel.navigateToSelectedMovieDetails(result)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.navigateToSelected.observe(viewLifecycleOwner, { result ->
            if (null != result) {
                val bundle = bundleOf(
                    "title" to result.original_title,
                    "release" to result.release_date,
                    "overview" to result.overview,
                    "vote" to result.vote_count,
                    "language" to result.original_language,
                    "backdrop" to result.backdrop_path,
                    "poster" to result.poster_path

                )
                (activity as NavigationHost).navigateTo(DetailsFragment(), bundle, true)
                viewModel.navigateToSelectedMovieDetailsComplete()
            }
        })

        viewModel.onBanner.observe(viewLifecycleOwner, { result ->
            movieOnBanner(result)
            binding.imageSinglePopular.setOnClickListener { navigateToSelectedMovieOnBanner(result) }
        })
    }

    private fun apiStatus() {
        viewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.groupWidgets.visibility = View.GONE
                    binding.groupStatus.visibility = View.VISIBLE
                    binding.loadingAnimation.playAnimation()
                }

                ApiStatus.DONE -> {
                    binding.groupWidgets.visibility = View.VISIBLE
                    binding.groupStatus.visibility = View.GONE
                    binding.loadingAnimation.cancelAnimation()
                }

                ApiStatus.ERROR -> {
                    binding.groupWidgets.visibility = View.GONE
                    binding.groupStatus.visibility = View.GONE
                    binding.errorAnimation.visibility = View.VISIBLE
                    binding.errorAnimation.playAnimation()
                    binding.loadingAnimation.cancelAnimation()
                }
            }
        })
    }

    private fun navigateToSelectedMovieOnBanner(result: Result) {
        val bundle = bundleOf(
            "title" to result.original_title,
            "release" to result.release_date,
            "overview" to result.overview,
            "vote" to result.vote_count,
            "language" to result.original_language,
            "backdrop" to result.backdrop_path,
            "poster" to result.poster_path

        )
        (activity as NavigationHost).navigateTo(DetailsFragment(), bundle, false)
        viewModel.navigateToSelectedMovieDetailsComplete()

    }

    private fun movieOnBanner(result: Result) {
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/w500${result.poster_path}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading_banner)
            )
            .centerCrop()
            .into(binding.imageSinglePopular)
        binding.textSinglePopular.text = result.title
    }
}
