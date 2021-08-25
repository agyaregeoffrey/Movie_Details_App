package com.dev.gka.abda.screens.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dev.gka.abda.activities.MainActivity
import com.dev.gka.abda.databinding.FragmentProfileBinding
import com.dev.gka.abda.offline.PrefManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.buttonSignOut.setOnClickListener {
            signOut()
        }
        initializeProfileDetails()
        return binding.root
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun initializeProfileDetails() {
        val pref = PrefManager.getInstance(context)
        binding.textDisplayName.text = pref.getDisplayName()
        binding.textEmail.text = pref.getEmail()
        binding.textPhone.text = pref.getUserPhone()

        val initials = StringBuilder()

        val savedName = pref.getDisplayName()
        val names = savedName?.split(" ")
        if (names != null) {
            val takenNames = names.take(2)
            for (name in takenNames) {
                initials.append(name[0].toUpperCase())
            }
        }

        binding.imageProfile.visibility = View.GONE

        binding.textNameInitials.apply {
            visibility = View.VISIBLE
            text = initials.toString()
        }

    }

}