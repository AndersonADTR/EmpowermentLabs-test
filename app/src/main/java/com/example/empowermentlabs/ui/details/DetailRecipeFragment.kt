package com.example.empowermentlabs.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.empowermentlabs.R
import com.example.empowermentlabs.data.models.Recipe
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Status
import com.example.empowermentlabs.databinding.FragmentDetailRecipeBinding
import com.example.empowermentlabs.ui.extensions.glideLoad
import com.example.empowermentlabs.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRecipeFragment : Fragment() {

    private val viewModel: DetailRecipeViewModel by viewModels()

    private val args by navArgs<DetailRecipeFragmentArgs>()

    private lateinit var binding: FragmentDetailRecipeBinding

    private lateinit var recipeItem: RecipeItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_recipe, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservables()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalRecipeItem(args.recipeId)
    }

    private fun initListeners() {
        binding.fab.setOnClickListener {
            viewModel.switchFavorite(recipeItem)
            binding.fab.setImageResource(if (!recipeItem.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun subscribeToObservables() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    handlerResponse(it.data)
                }
                Status.ERROR -> {
                    showLoading(false)
                    showMessage(it.message.orEmpty())
                }
            }
        }
    }

    private fun handlerResponse(data: Any?) {
        data?.let {
            when (it) {
                is Recipe -> {
                    viewModel.saveData(it)
                    printView(it)
                }
                is RecipeItem -> {
                    recipeItem = it
                    viewModel.getLocalRecipe(args.recipeId)
                }
            }
        } ?: run {
            context?.let { m ->
                when {
                    isInternetAvailable(m) -> viewModel.getRemoteRecipe(
                        getString(R.string.api_key),
                        args.recipeId
                    )
                    else -> showMessage(getString(R.string.connection_not_available))
                }
            }
        }
    }

    private fun printView(recipe: Recipe) {
        binding.apply {
            fab.setImageResource(if (recipeItem.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
            thumb.glideLoad(recipe.image.orEmpty())

            title.text = recipe.title.orEmpty()
            credits.text = recipe.creditsText
            readyInMinutes.text = getString(R.string.ready_in_minutes, recipe.readyInMinutes)

            summary.text = HtmlCompat.fromHtml(recipe.summary.orEmpty(), 0)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.progress.isVisible = show
    }
}