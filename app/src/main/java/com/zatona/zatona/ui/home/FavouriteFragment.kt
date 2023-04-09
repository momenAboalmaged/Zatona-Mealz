package com.zatona.zatona.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zatona.zatona.R
import com.zatona.zatona.adapters.FavouriteAdapter
import com.zatona.zatona.databinding.FragmentFavouriteBinding


class FavouritesFragment : Fragment(R.layout.fragment_favourite) {
    private val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    private var _binding: FragmentFavouriteBinding? = null
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouriteBinding.bind(view)
        favouriteAdapter = FavouriteAdapter(viewModel)


        binding.favouriteRv.apply {
            adapter = favouriteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }
        viewModel.Favorites.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                favouriteAdapter.differ.submitList(it)
                Log.e("favourite size", "onViewCreated: ${it.size}")
                binding.favouriteEmptyList.visibility = View.GONE
            } else {
                binding.favouriteRv.visibility = View.GONE
                binding.favouriteEmptyList.visibility = View.VISIBLE
            }
        })

        favouriteAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("mealId", it.idMeal)
            findNavController().navigate(
                R.id.action_favouritesFragment2_to_mealDetailsFragment,
                args
            )

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

