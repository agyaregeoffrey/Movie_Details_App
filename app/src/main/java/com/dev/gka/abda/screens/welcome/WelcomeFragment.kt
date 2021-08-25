package com.dev.gka.abda.screens.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dev.gka.abda.R
import com.dev.gka.abda.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome,
            container,
            false
        )

        binding.buttonGetStarted.setOnClickListener {
            it?.findNavController()?.navigate(R.id.action_welcomeFragment_to_loginFragment2)
        }

        return binding.root
    }


}