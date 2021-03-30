package com.dev.gka.abda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.gka.abda.NavigationHost
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.ActivityMovieBinding
import com.dev.gka.abda.model.Result

class MovieActivity : AppCompatActivity(), NavigationHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMovieBinding>(
            this, R.layout.activity_movie
        )

        val navController = findNavController(R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun navigateTo(fragment: Fragment, result: Result, addToBackstack: Boolean) {
        val bundle = Bundle()
        bundle.putString("title", result.title)
        bundle.putString("release", result.release_date)
        bundle.putInt("vote", result.vote_count)
        bundle.putString("overview", result.overview)
        bundle.putString("language", result.original_language)
        bundle.putString("backdrop", result.backdrop_path)
        bundle.putString("poster", result.poster_path)

        fragment.arguments = bundle

        supportFragmentManager
            .commit {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out_left
                )
                replace(R.id.nav_host, fragment)


                if (addToBackstack) {
                    this.addToBackStack(null)
                }
            }

    }

}