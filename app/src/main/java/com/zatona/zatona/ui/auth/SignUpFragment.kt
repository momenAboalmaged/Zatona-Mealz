package com.zatona.zatona.ui.auth
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentSignUpBinding
import com.zatona.zatona.ui.home.HomeViewModel
import com.zatona.zatona.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        auth = Firebase.auth
        binding.apply {
            signUpBtn.setOnClickListener {
                val email = editTextEmail.editText?.text.toString().trim()
                val name = editTextUserName.editText?.text.toString().trim()
                val psw = editTextPassword.editText?.text.toString().trim()

                if (email.isNotEmpty() && psw.isNotEmpty() && name.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, psw)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.apply {
                                    signUpBtn.visibility = View.GONE

                                }
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

                    viewModel.saveUserName(name)

                } else {
                    Toast.makeText(context, "Try Again ,invalid email or password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}