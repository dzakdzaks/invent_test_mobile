package com.dzakdzaks.inventmvvm.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.dzakdzaks.inventmvvm.R
import com.dzakdzaks.inventmvvm.data.entity.Product
import com.dzakdzaks.inventmvvm.data.entity.TxProduct
import com.dzakdzaks.inventmvvm.databinding.ActivityMainBinding
import com.dzakdzaks.inventmvvm.util.DelayedSearchTextWatcher
import com.dzakdzaks.inventmvvm.util.Resource
import com.dzakdzaks.inventmvvm.util.autoFitColumns
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupProduct()
        searchTextWatcher()
        clickable()
        observeData()
    }

    private fun clickable() {
        binding.toolbar.tfSearch.setStartIconOnClickListener { viewModel.setSearchKey(keySearch) }
        binding.toolbar.layoutFilter.setOnClickListener { showPopUp() }
    }

    private fun searchTextWatcher() {
        binding.toolbar.tfSearch.editText?.addTextChangedListener(DelayedSearchTextWatcher(object :
            DelayedSearchTextWatcher.OnTextWatcher {
            override fun onAfterTextChanged(s: String) {
                keySearch = s
                viewModel.setSearchKey(keySearch)
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
        viewModel.setSearchKey(keySearch)
    }

    private fun showPopUp() {
        val popupMenu = PopupMenu(this, binding.toolbar.layoutFilter)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.sorting_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sortByNameAsc -> {
                    observeProducts(keySearch, true, TxProduct.TX_NAME)
                }
                R.id.sortByNameDesc -> {
                    observeProducts(keySearch, false, TxProduct.TX_NAME)
                }
                R.id.sortByPriceAsc -> {
                    observeProducts(keySearch, true, TxProduct.TX_PRICE)
                }
                R.id.sortByPriceDesc -> {
                    observeProducts(keySearch, false, TxProduct.TX_PRICE)
                }
            }
            true
        }
    }

    private fun setupProduct() {
        adapter = ProductAdapter(object : ProductAdapter.ProductClickListener {
            override fun onProductClicked(data: Product) {
                Snackbar.make(
                    binding.parentLayout,
                    "${data.msProduct.msName}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).apply {
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
        binding.rvData.autoFitColumns(200)
        binding.rvData.adapter = adapter
    }

    private fun observeData() {
        viewModel.getMsProducts().observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { _ ->
                        viewModel.getTxProducts()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.parentLayout,
                        "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.getTxProducts().observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { _ ->
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.parentLayout,
                        "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.searchKey.observe(this, {
            observeProducts(it)
        })

        observeProducts()

    }

    private fun observeProducts(
        key: String = "",
        isAsc: Boolean = true,
        orderBy: String = TxProduct.TX_NAME
    ) {
        viewModel.getAllProducts(key, isAsc, orderBy).observe(this, { products ->
            products?.let { list ->
                Timber.tag("wakwaw").d(Gson().toJson(list))
                adapter.products?.clear()
                adapter.products?.addAll(list)
                adapter.notifyDataSetChanged()
            }
        })
    }
}