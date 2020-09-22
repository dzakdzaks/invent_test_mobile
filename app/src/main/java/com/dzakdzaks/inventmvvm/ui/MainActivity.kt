package com.dzakdzaks.inventmvvm.ui

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dzakdzaks.inventmvvm.R
import com.dzakdzaks.inventmvvm.databinding.ActivityMainBinding
import com.dzakdzaks.inventmvvm.util.DelayedSearchTextWatcher
import com.dzakdzaks.inventmvvm.util.Resource
import com.dzakdzaks.inventmvvm.util.closeKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: MainViewModel by viewModels()
    private var keySearch: String = ""
    private lateinit var filterProduct: FilterProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.tag("wawaw").d("oncreate")
        binding.parentLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setupProduct()
        searchTextWatcher()
        clickable()
        observeData()
    }

    private fun clickable() {
        binding.toolbar.layoutFilter.setOnClickListener { showPopUp() }
    }

    private fun searchTextWatcher() {
        binding.toolbar.tfSearch.editText?.addTextChangedListener(DelayedSearchTextWatcher(object :
            DelayedSearchTextWatcher.OnTextWatcher {
            override fun onAfterTextChanged(s: String) {
                keySearch = s
                initFilterData()
            }
        }))
        binding.toolbar.tfSearch.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            false
        }
    }

    private fun performSearch() {
        binding.toolbar.tfSearch.editText?.clearFocus()
        closeKeyboard(this, binding.toolbar.tfSearch)
        initFilterData()
    }

    private fun initFilterData() {
        if (::filterProduct.isInitialized)
            viewModel.setFilter(keySearch, filterProduct.isAsc)
        else
            viewModel.setFilter()
    }

    private fun showPopUp() {
        val popupMenu = PopupMenu(this, binding.toolbar.layoutFilter)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.sorting_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sortByNameAsc -> {
                    viewModel.setFilter(keySearch, true)
                }
                R.id.sortByNameDesc -> {
                    viewModel.setFilter(keySearch, false)
                }
            }
            true
        }
    }

    private fun setupProduct() {
        adapter = ProductAdapter().apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    if (this@apply.itemCount == 0) {
                        val msgIsEmpty: String =
                            if (keySearch.isEmpty())
                                getString(R.string.no_data_available)
                            else
                                String.format(getString(R.string.not_found_data_search), keySearch)
                        Snackbar.make(
                            binding.parentLayout,
                            msgIsEmpty,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
        binding.rvData.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeData() {
        initFilterData()
        viewModel.getMsProducts.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { _ ->

                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Timber.tag("errorWoy").d(it.message)
                }
                Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.getTxProducts.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { _ ->
                        binding.progressBar.visibility = View.GONE
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Timber.tag("errorWoy").d(it.message)
                }
                Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.getFilter.observe(this, {
            filterProduct = it
        })

        viewModel.prods.observe(this, { products ->
            products?.let { list ->
                Timber.tag("wawaw").d(Gson().toJson(list))
                adapter.setItems(list)
            }
        })
    }
}