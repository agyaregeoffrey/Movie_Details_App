package com.dev.gka.abda.screens.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.dev.gka.abda.R
import com.dev.gka.abda.activities.MovieActivity
import com.dev.gka.abda.databinding.FragmentLoginBinding
import com.dev.gka.abda.model.User
import com.dev.gka.abda.offline.PrefManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)



        binding.buttonNavigateToSignUp.setOnClickListener {
            it?.findNavController()?.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.buttonSignIn.setOnClickListener {
            var isValid = true

            if (!isEmailValid(binding.editTextEmail.text)) {
                isValid = false
                binding.textInputEmail.error = "Email field cannot be empty"
            } else {
                binding.textInputEmail.error = null
            }

            if (!isPasswordValid(binding.editTextPassword.text)) {
                isValid = false
                binding.textInputPassword.error = "Password field cannot be empty"
            } else {
                binding.textInputPassword.error = null
            }

            if (isValid) {

            }

        }

        binding.buttonSignWithGoogle.setOnClickListener {
            signInWithGoogle()
        }

        binding.editTextEmail.setOnKeyListener { _, _, _ ->
            if (isEmailValid(binding.editTextEmail.text)) {
                binding.textInputEmail.error = null
            }
            false
        }

        binding.editTextPassword.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(binding.editTextPassword.text)) {
                binding.textInputPassword.error = null
            }
            false
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Timber.d(account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                binding.lottieAnimation.visibility = View.GONE
                binding.lottieAnimation.cancelAnimation()
                Timber.w("Login Failed")
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {

    }

    private fun signInWithGoogle() {
        binding.lottieAnimation.visibility = View.VISIBLE
        binding.lottieAnimation.playAnimation()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun isEmailValid(email: Editable?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()
                && email != null
    }

    private fun isPasswordValid(password: Editable?): Boolean {
        return password != null && password.length >= 8
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        binding.lottieAnimation.visibility = View.VISIBLE
        binding.lottieAnimation.playAnimation()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser!!

                    val user = User(
                        firebaseUser.displayName,
                        firebaseUser.email,
                        firebaseUser.photoUrl
                    )

                    binding.lottieAnimation.visibility = View.GONE
                    binding.lottieAnimation.cancelAnimation()
                    PrefManager.getInstance(context).saveUserInfo(user)
                    startActivity(Intent(requireActivity(), MovieActivity::class.java))
                    requireActivity().finish()
                } else {
                    binding.lottieAnimation.visibility = View.GONE
                    binding.lottieAnimation.cancelAnimation()
                    Timber.w("Authentication Failed ${task.exception}")
                }
            }
    }

    private fun showAndHideAnimation(lottieAnimationView: LottieAnimationView, view: View) {

    }


    companion object {
        const val RC_SIGN_IN = 123
    }

}