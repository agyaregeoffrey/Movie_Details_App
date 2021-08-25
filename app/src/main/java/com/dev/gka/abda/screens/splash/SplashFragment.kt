package com.dev.gka.abda.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.dev.gka.abda.R
import com.dev.gka.abda.activities.MovieActivity
import com.dev.gka.abda.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var handler: Handler

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
//            val user = auth.currentUser
//
//            if (null == currentUser) {
//                findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
//            } else {
//                startActivity(Intent(this.requireActivity(), MovieActivity::class.java))
//                requireActivity().finish()
//            }
            // Check if a user is already logged in
            when (auth.currentUser) {
                null -> {
                    findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
                }
                else -> {
                    startActivity(Intent(this.requireActivity(), MovieActivity::class.java))
                    requireActivity().finish()
                }
            }
        }, 2000)
        return binding.root
    }

}