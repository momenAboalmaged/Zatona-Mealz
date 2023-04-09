package com.zatona.zatona.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentLoginBinding
import com.zatona.zatona.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        auth = Firebase.auth
        binding.apply {
            loginBtn.setOnClickListener {
                val email = editTextEmail.editText?.text.toString()
                val password = editTextPassword.editText?.text.toString()
                if (!password.isEmpty() && !email.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(activity, MainActivity::class.java))
                                activity?.finish()
                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                } else
                    Toast.makeText(
                        context,
                        "Sorry you can not enter empty fields",
                        Toast.LENGTH_SHORT
                    )
                        .show()


            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}