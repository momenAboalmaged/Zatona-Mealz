package com.zatona.zatona.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zatona.zatona.R
import com.zatona.zatona.adapters.FavouriteAdapter
import com.zatona.zatona.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        favouriteAdapter = FavouriteAdapter(viewModel)


        binding.favouriteRv.apply {
            adapter = favouriteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.favouriteRv.visibility = View.GONE
                binding.favouriteEmptyList.visibility = View.VISIBLE
            } else {
                favouriteAdapter.differ.submitList(it)
                binding.favouriteEmptyList.visibility = View.GONE
                binding.favouriteRv.visibility = View.VISIBLE
            }
        })

        favouriteAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("mealId", it.idMeal)
            findNavController().navigate(
                R.id.action_favouritesFragment_to_mealDetailsFragment,
                args
            )

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}