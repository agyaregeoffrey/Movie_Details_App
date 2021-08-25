package com.dev.gka.abda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.gka.abda.utilities.NavigationHost
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity(), NavigationHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMovieBinding>(
            this, R.layout.activity_movie
        )

        val navController = findNavController(R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(navController)

    }


    override fun navigateTo(fragment: Fragment, bundle: Bundle, addToBackstack: Boolean) {
        fragment.arguments = bundle

        supportFragmentManager
            .commit {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
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