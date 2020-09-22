package com.dzakdzaks.inventmvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dzakdzaks.inventmvvm.R
import com.dzakdzaks.inventmvvm.data.entity.TxProduct
import com.dzakdzaks.inventmvvm.databinding.ItemTxProductBinding
import com.dzakdzaks.inventmvvm.util.SimpleDiffUtil
import com.dzakdzaks.inventmvvm.util.formatCurrency
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

class TxProductAdapter : RecyclerView.Adapter<TxProductAdapter.ViewHolder>(), SimpleDiffUtil {

    private var txProduct: MutableList<TxProduct>? by Delegates.observable(mutableListOf()) { _, old, new ->
        autoNotify(old!!, new!!) { o, n -> o.txID == n.txID }
    }

    fun setItems(list: List<TxProduct>) {
        txProduct?.clear()
        txProduct?.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTxProductBinding =
            ItemTxProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(txProduct!![position])
    }

    override fun getItemCount(): Int {
        return txProduct?.size ?: 0
    }

    inner class ViewHolder(
        private val itemBinding: ItemTxProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(product: TxProduct) {
            itemBinding.tvBranch.text =
                String.format(itemBinding.root.context.getString(R.string.branch), product.txBranch)
            itemBinding.tvPrice.text = product.txPrice.toString().formatCurrency()
        }
    }
}
