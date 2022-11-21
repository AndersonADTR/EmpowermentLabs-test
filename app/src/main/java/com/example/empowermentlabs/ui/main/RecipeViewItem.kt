package com.example.empowermentlabs.ui.main

import android.view.View
import com.example.empowermentlabs.ui.interfaces.OnActionClickListener
import com.example.empowermentlabs.R
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.databinding.ItemRecipeBinding
import com.example.empowermentlabs.ui.extensions.glideLoad
import com.xwray.groupie.viewbinding.BindableItem

class RecipeViewItem(
    private val recipeItem: RecipeItem,
    private val clickListener: OnActionClickListener
) : BindableItem<ItemRecipeBinding>() {

    override fun bind(binding: ItemRecipeBinding, p1: Int) {
        binding.thumb.glideLoad(recipeItem.image.orEmpty())
        binding.title.text = recipeItem.title.orEmpty()


        binding.favorite.setImageResource(if (recipeItem.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
        binding.favorite.setOnClickListener {
            binding.favorite.setImageResource(if (!recipeItem.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
            clickListener.onClick(recipeItem)
        }
    }

    override fun getLayout(): Int = R.layout.item_recipe

    override fun initializeViewBinding(p0: View): ItemRecipeBinding = ItemRecipeBinding.bind(p0)

    fun getRecipe() = recipeItem
}