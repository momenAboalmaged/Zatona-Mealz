package com.zatona.zatona.ui.auth
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentLottiBinding
import com.zatona.zatona.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LottiFragment : Fragment(R.layout.fragment_lotti) {
    private var _binding: FragmentLottiBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLottiBinding.bind(view)
        binding.lottiAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                setUpAuth()
            }

            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })

    }


    private fun setUpAuth() {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        } else {
            findNavController().navigate(R.id.action_lottiFragment_to_welcomeFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}