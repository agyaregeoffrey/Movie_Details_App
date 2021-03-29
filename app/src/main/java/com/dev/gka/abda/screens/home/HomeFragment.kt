package com.dev.gka.abda.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.gka.abda.MovieAdapter
import com.dev.gka.abda.NavigationHost
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.FragmentHomeBinding
import com.dev.gka.abda.model.Result
import com.dev.gka.abda.screens.details.DetailsFragment
import timber.log.Timber


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

        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                MovieApiStatus.LOADING -> {
                    binding.groupWidgets.visibility = View.GONE
                    binding.groupStatus.visibility = View.VISIBLE
                    binding.loadingAnimation.playAnimation()
                }

                MovieApiStatus.DONE -> {
                    binding.groupWidgets.visibility = View.VISIBLE
                    binding.groupStatus.visibility = View.GONE
                    binding.loadingAnimation.cancelAnimation()
                }

                MovieApiStatus.ERROR -> {
                    binding.groupWidgets.visibility = View.GONE
                    binding.groupStatus.visibility = View.GONE
                    binding.loadingAnimation.cancelAnimation()
                }
            }
        })

        viewModel.trending.observe(viewLifecycleOwner, Observer { data ->
            binding.recyclerTrending.apply {
                adapter = MovieAdapter(MovieAdapter.OnClickListener { result ->
                    viewModel.displayMovieDetails(result)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
            Timber.i("Trending List: ${data.size}")
            val bannerImage = data.random()
            movieBanner(bannerImage)
        })

        viewModel.popular.observe(viewLifecycleOwner, Observer { data ->
            binding.recyclerPopular.apply {
                adapter = MovieAdapter(MovieAdapter.OnClickListener { result ->
                    viewModel.displayMovieDetails(result)
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
                    viewModel.displayMovieDetails(result)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, { result ->
            if (null != result) {
                (activity as NavigationHost).navigateTo(DetailsFragment(), result, true)
                viewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root
    }

    private fun movieBanner(result: Result) {
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/w500${result.poster_path}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading)
            )
            .centerCrop()
            .into(binding.imageSinglePopular)
        binding.textSinglePopular.text = result.title
    }
}
