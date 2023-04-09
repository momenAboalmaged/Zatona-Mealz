package com.zatona.zatona.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentProfileBinding
import com.zatona.zatona.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dataStore: DataStore<Preferences>



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        firebaseAuth = Firebase.auth
        showUserData()

        binding.btnLogout.setOnClickListener {
            showDialog()
        }
    }

    private fun showUserData() {
        firebaseAuth = Firebase.auth
        binding.tvEmailProfile.text = firebaseAuth.currentUser?.email
        binding.tvNameProfile.text = viewModel.getUserName()
    }
    fun showDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are u sure want Logout?")
            .setTitle("")
            .setPositiveButton("Yes") { dialog, id ->
                firebaseAuth.signOut()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                // User clicked No button
            }

        val dialog = builder.create()
        dialog.show()
    }
}