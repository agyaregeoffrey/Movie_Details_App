package com.dev.gka.abda.screens.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dev.gka.abda.R
import com.dev.gka.abda.activities.MovieActivity
import com.dev.gka.abda.databinding.FragmentSignUpBinding
import com.dev.gka.abda.model.User
import com.dev.gka.abda.offline.PrefManager
import com.dev.gka.abda.utilities.Helpers.cancelAnimation
import com.dev.gka.abda.utilities.Helpers.playAnimation
import com.dev.gka.abda.utilities.Helpers.showSnack
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var fullname: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )


        binding.buttonSignUp.setOnClickListener {
            fullname = binding.editTextSignUpName.text.toString()
            email = binding.editTextSignUpEmail.text.toString()
            password = binding.editTextSignUpPassword.text.toString()
            phone = binding.editTextSignUpPhone.text.toString()
            if (isFormValidated())
                signUp(fullname, email, password, phone)
        }

        binding.buttonNavigateToSignIn.setOnClickListener {
            it?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        return binding.root
    }

    private fun signUp(name: String, email: String, password: String, phone: String) {
        playAnimation(binding.animSignUp)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = User(name, email, null, phone)
                    PrefManager.getInstance(requireContext()).saveUserInfo(user)
                    cancelAnimation(binding.animSignUp)
                    startActivity(Intent(requireActivity(), MovieActivity::class.java))
                    requireActivity().finish()
                }
            }.addOnFailureListener {
                cancelAnimation(binding.animSignUp)
                showSnack(binding.animSignUp, "Sign up failed. Try again")
                Timber.w("Sign Up Failed: ${it.message}")
            }
    }

    private fun isFormValidated(): Boolean {
        var isValid = true
        if (binding.editTextSignUpName.text?.isEmpty() == true) {
            binding.textInputSignUpName.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        }
        if (binding.editTextSignUpPhone.text?.isEmpty() == true) {
            binding.textInputSignUpPhone.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextSignUpEmail.text.toString())
                .matches()
        ) {
            binding.textInputSignUpEmail.error = getString(R.string.invalid_email)
            isValid = false
        }
        if (binding.editTextSignUpPassword.text?.isEmpty() == true) {
            binding.textInputSignUpPassword.error = getString(R.string.field_cannot_be_empty)
            isValid = false
        } else if (binding.editTextSignUpPassword.text?.length!! < 8) {
            binding.textInputSignUpPassword.error = getString(R.string.password_limit_less)
            isValid = false
        }
        return isValid
    }

}