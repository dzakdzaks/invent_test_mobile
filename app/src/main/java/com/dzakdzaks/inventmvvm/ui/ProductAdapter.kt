package com.dzakdzaks.inventmvvm.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakdzaks.inventmvvm.R
import com.dzakdzaks.inventmvvm.data.entity.Product
import com.dzakdzaks.inventmvvm.databinding.ItemProductBinding
import com.dzakdzaks.inventmvvm.util.SimpleDiffUtil
import kotlin.properties.Delegates

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created on Tuesday, 22 September 2020 at 8:29 AM.
 * Project Name => InventMVVM
 * Package Name => com.dzakdzaks.inventmvvm.ui
 * ==================================//==================================
 * ==================================//==================================
 */

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>(), SimpleDiffUtil {

    private var products: MutableList<Product>? by Delegates.observable(mutableListOf()) { _, old, new ->
        autoNotify(old!!, new!!) { o, n -> o.msProduct.msCode == n.msProduct.msCode }
    }

    fun setItems(list: List<Product>) {
        products?.clear()
        products?.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProductBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products!![position], position)
        holder.setupTxAdapter(products!![position])
        /*holder.setupTxProduct()*/
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    inner class ViewHolder(
        private val itemBinding: ItemProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var adapter: TxProductAdapter
        private var isAsc: Int = 1

        fun bind(product: Product, position: Int) {
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            if (position % 2 == 0) {
                params.gravity = Gravity.END
                itemBinding.cardView.layoutParams = params
            } else {
                params.gravity = Gravity.START
                itemBinding.cardView.layoutParams = params
            }

            itemBinding.tvName.text = product.msProduct.msName

            Glide.with(itemBinding.ivProduct.context)
                .load(product.msProduct.msImage)
                .into(itemBinding.ivProduct)

        }

        fun setupTxAdapter(product: Product) {
            val descList = product.txProducts.sortedByDescending { it.txPrice }
            val ascList = product.txProducts.sortedBy { it.txPrice }

            adapter = TxProductAdapter()

            itemBinding.rvProductTx.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = this@ViewHolder.adapter
            }

            itemBinding.btnSwitchSort.setOnClickListener {
                if (isAsc == 1) {
                    isAsc = 0
                    adapter.setItems(descList)
                    itemBinding.btnSwitchSort.isClickable = true
                    itemBinding.btnSwitchSort.text =
                        itemBinding.btnSwitchSort.context.getString(R.string.sort_by_price_desc)
                } else {
                    isAsc = 1
                    adapter.setItems(ascList)
                    itemBinding.btnSwitchSort.isClickable = true
                    itemBinding.btnSwitchSort.text =
                        itemBinding.btnSwitchSort.context.getString(R.string.sort_by_price_asc)
                }
            }

            initFilterData(product)
        }

        private fun initFilterData(product: Product) {
            if (!product.txProducts.isNullOrEmpty()) {
                val ascList = product.txProducts.sortedBy { it.txPrice }
                isAsc = 1
                adapter.setItems(ascList)
                itemBinding.btnSwitchSort.isClickable = true
                itemBinding.btnSwitchSort.text =
                    itemBinding.btnSwitchSort.context.getString(R.string.sort_by_price_asc)

            } else {
                adapter.setItems(emptyList())
                itemBinding.btnSwitchSort.isClickable = false
                itemBinding.btnSwitchSort.text =
                    itemBinding.btnSwitchSort.context.getString(R.string.no_price)
            }
        }
    }

}
