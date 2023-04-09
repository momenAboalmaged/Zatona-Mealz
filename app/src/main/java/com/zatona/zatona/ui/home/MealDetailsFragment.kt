package com.zatona.zatona.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zatona.zatona.R
import com.zatona.zatona.databinding.FragmentMealDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MealDetailsFragment : Fragment(R.layout.fragment_meal_details) {
    private val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MealDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealDetailsBinding.bind(view)
        val mealId = args.mealId
        viewModel.getMealDetails(mealId)

        viewModel.mealDetails.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it != null) {
                    mealName.text = it.strMeal
                    mealOrigin.text = it.strArea
                    mealTags.text = it.strTags
                    mealRecipe.text = it.strInstructions
                    quantity1.text = it.strMeasure1
                    quantity2.text = it.strMeasure2
                    quantity3.text = it.strMeasure3
                    quantity4.text = it.strMeasure4
                    ingredient1.text = it.strIngredient1
                    ingredient2.text = it.strIngredient2
                    ingredient3.text = it.strIngredient3
                    ingredient4.text = it.strIngredient4
                    Glide.with(view).load(it.strMealThumb)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_wifi_broken).transform(CenterCrop())
                        .placeholder(R.drawable.zatona).transform(CenterCrop())
                        .into(mealIv)

                }
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}