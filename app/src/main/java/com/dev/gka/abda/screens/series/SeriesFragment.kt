package com.dev.gka.abda.screens.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.gka.abda.utilities.ApiStatus
import com.dev.gka.abda.utilities.NavigationHost
import com.dev.gka.abda.adapters.TvAdapter
import com.dev.gka.abda.databinding.FragmentSeriesBinding
import com.dev.gka.abda.screens.details.DetailsFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding

    private val viewModel: SeriesViewModel by lazy {
        ViewModelProvider(this).get(SeriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        initializeDisplayContent()
        return binding.root
    }

    private fun initializeDisplayContent() {
        viewModel.status.observe(viewLifecycleOwner,  {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.groupSeriesWidgets.visibility = View.GONE
                    binding.groupSeriesStatus.visibility = View.VISIBLE
                    binding.loadingAnimation.playAnimation()
                }

                ApiStatus.DONE -> {
                    binding.groupSeriesWidgets.visibility = View.VISIBLE
                    binding.groupSeriesStatus.visibility = View.GONE
                    binding.loadingAnimation.cancelAnimation()
                }

                ApiStatus.ERROR -> {
                    binding.groupSeriesWidgets.visibility = View.GONE
                    binding.groupSeriesStatus.visibility = View.GONE
                    binding.errorAnimation.visibility = View.VISIBLE
                    binding.errorAnimation.playAnimation()
                    binding.loadingAnimation.cancelAnimation()
                }
            }
        })

        viewModel.airingToday.observe(viewLifecycleOwner, { data ->
            binding.recyclerAiringTodaySeries.apply {
                adapter = TvAdapter(TvAdapter.OnClickListener {
                    viewModel.displayMovieDetails(it)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.topRated.observe(viewLifecycleOwner, { data ->
            binding.recyclerRatedSeries.apply {
                adapter = TvAdapter(TvAdapter.OnClickListener {
                    viewModel.displayMovieDetails(it)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.popular.observe(viewLifecycleOwner, { data ->
            binding.recyclerPopularSeries.apply {
                adapter = TvAdapter(TvAdapter.OnClickListener {
                    viewModel.displayMovieDetails(it)
                }, data)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        })

        viewModel.navigateToSelectedSeries.observe(viewLifecycleOwner, { result ->
            if (null != result) {
                val bundle = bundleOf(
                    "title" to result.name,
                    "release" to result.first_air_date,
                    "overview" to result.overview,
                    "vote" to result.vote_count,
                    "language" to result.original_language,
                    "backdrop" to result.backdrop_path,
                    "poster" to result.poster_path

                )
                (activity as NavigationHost).navigateTo(DetailsFragment(), bundle, false)
                viewModel.displayMovieDetailsComplete()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}