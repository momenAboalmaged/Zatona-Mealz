package com.zatona.zatona.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zatona.zatona.R
import com.zatona.zatona.adapters.MealAdapter
import com.zatona.zatona.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {

                    binding.searchRv.visibility = View.VISIBLE
                    binding.noResultsImg.visibility = View.GONE
                    searchAdapter.differ.submitList(it)
                }
            } else {
                binding.noResultsImg.visibility = View.VISIBLE
            }
        })
        searchAdapter = MealAdapter(viewModel)
        binding.searchRv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
        }
        binding.searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.search(query)
                    binding.searchRv.scrollToPosition(0)
                    binding.searchTv.clearFocus()
                    binding.searchTv.setQuery("", false)
                    return true
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("mealId", it.idMeal)
            findNavController().navigate(R.id.action_searchFragment_to_mealDetailsFragment, args)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSearchResults()
        binding.searchRv.visibility = View.GONE
        _binding = null
    }


}