package com.zatona.zatona.ui.auth
import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeBinding.bind(view)

        binding.btnStart.setOnClickListener {
          findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }

        binding.welcomeAnimate.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {

            }

            override fun onAnimationCancel(p0: Animator) {

            }


            override fun onAnimationRepeat(p0: Animator) {}
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}