package com.example.empowermentlabs.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empowermentlabs.*
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Status
import com.example.empowermentlabs.databinding.FragmentRecipesBinding
import com.example.empowermentlabs.ui.extensions.onQueryTextChange
import com.example.empowermentlabs.ui.interfaces.OnActionClickListener
import com.example.empowermentlabs.utils.EMPTY_STRING
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment(), OnActionClickListener {

    private val viewModel: RecipesViewModel by viewModels()

    private lateinit var binding: FragmentRecipesBinding

    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onScrollListener: RecyclerView.OnScrollListener

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val recyclerAdapter by lazy { GroupieAdapter() }

    private var firstVisibleItem: Int = 0
    private var lastVisibleItem: Int = 0

    private var isFavoritesVisible: Boolean = false

    private var search: String = EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuProvider()
        subscribeToObservables()
        initListeners()
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalRecipes()
    }

    private fun menuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_favorites -> {
                        recyclerAdapter.clear()
                        when {
                            isFavoritesVisible -> {
                                isFavoritesVisible = false
                                viewModel.getLocalRecipes()
                            }
                            else -> {
                                isFavoritesVisible = true
                                viewModel.findByFavorites()
                            }
                        }
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initListeners() {
        binding.searchView.onQueryTextChange {
            search = it
            initSearch()
        }
        binding.searchView.setOnCloseListener {
            recyclerAdapter.clear()
            viewModel.getLocalRecipes()
            return@setOnCloseListener false
        }
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            recyclerAdapter.clear()
            viewModel.getLocalRecipes()
        }
        onItemClickListener = OnItemClickListener { item, _ ->
            if (item is RecipeViewItem) {
                onClickItemListener(item.getRecipe())
            }
        }
        onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
                    firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

                    if (firstVisibleItem > RecyclerView.NO_POSITION
                        && lastVisibleItem > RecyclerView.NO_POSITION
                        && lastVisibleItem >= layoutManager.itemCount - 1
                        && !isFavoritesVisible
                    ) {
                        context?.let { m ->
                            when {
                                isInternetAvailable(m) -> {
                                    viewModel.offset += when {
                                        layoutManager.itemCount > 0 -> {
                                            layoutManager.itemCount
                                        }
                                        else -> viewModel.pageSize
                                    }
                                    viewModel.getRemoteRecipes(
                                        getString(R.string.api_key),
                                        search,
                                        viewModel.offset.toString()
                                    )
                                }
                                else -> showMessage(getString(R.string.connection_not_available))
                            }
                        }
                    }
                }
            }
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
                is List<*> -> {
                    when {
                        it.isNotEmpty() -> {
                            viewModel.saveData(
                                it.map { m ->
                                    val recipe = m as RecipeItem
                                    updateDataRecycler(recipe)
                                    recipe
                                }
                            )
                        }
                        else -> {
                            when {
                                isFavoritesVisible -> {
                                    isFavoritesVisible = false
                                    viewModel.getLocalRecipes()
                                    showMessage(getString(R.string.favorites_not_found))
                                }
                                else -> {
                                    context?.let { m ->
                                        when {
                                            isInternetAvailable(m) -> {
                                                viewModel.offset = 0
                                                viewModel.getRemoteRecipes(
                                                    getString(R.string.api_key),
                                                    search,
                                                    viewModel.offset.toString()
                                                )
                                            }
                                            else -> showMessage(getString(R.string.connection_not_available))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun updateDataRecycler(recipe: RecipeItem) {
        recyclerAdapter.add(RecipeViewItem(recipe, this))
    }

    private fun setupRecycler() {
        recyclerAdapter.setOnItemClickListener(onItemClickListener)
        binding.recycler.apply {
            adapter = recyclerAdapter
            addOnScrollListener(onScrollListener)
        }
        recyclerAdapter.clear()
    }

    private fun initSearch() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            recyclerAdapter.clear()
            viewModel.findByTitle("%$search%")
        }, 800)
    }

    private fun onClickItemListener(recipe: RecipeItem) {
        findNavController().navigate(RecipesFragmentDirections.actionRecipesToDetail(recipe.id))
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.progress.isVisible = show
    }

    override fun onClick(data: Any) {
        if (data is RecipeItem) {
            viewModel.switchFavorite(data)
        }
    }
}