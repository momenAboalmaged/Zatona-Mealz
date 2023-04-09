package com.zatona.zatona.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zatona.zatona.R
import com.zatona.zatona.adapters.MealAdapter
import com.zatona.zatona.databinding.FragmentMealsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MealsFragment : Fragment(R.layout.fragment_meals) {
    private val viewModel : HomeViewModel by activityViewModels<HomeViewModel>()
    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!
   private val args: MealsFragmentArgs by navArgs()
    private lateinit var mealAdapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealsBinding.bind(view)
        val categoryName = args.categoryName
        viewModel.getMeals(categoryName)
        viewModel.mealData.observe(viewLifecycleOwner, Observer {
            mealAdapter.differ.submitList(it)
        })
        mealAdapter = MealAdapter(viewModel)
        binding.mealsRv.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mealAdapter
        }
       mealAdapter.onItemClickListener = {
           val args = Bundle()
           args.putString("mealId", it.idMeal)
        findNavController().navigate(R.id.action_mealsFragment_to_mealDetailsFragment, args)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}