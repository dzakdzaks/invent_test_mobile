package com.dzakdzaks.inventmvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakdzaks.inventmvvm.R
import com.dzakdzaks.inventmvvm.data.entity.Product
import com.dzakdzaks.inventmvvm.util.SimpleDiffUtil
import com.dzakdzaks.inventmvvm.util.formatCurrency
import kotlinx.android.synthetic.main.item_product.view.*
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

class ProductAdapter(
    private val onProductClickListener: ProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(), SimpleDiffUtil {

    var products: MutableList<Product>? by Delegates.observable(mutableListOf()) { _, old, new ->
        autoNotify(old!!, new!!) { o, n -> o.txProduct.txID == n.txProduct.txID }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products?.get(position)

        holder.tvName.text = product?.msProduct?.msName
        holder.tvPrice.text = product?.txProduct?.txPrice.toString().formatCurrency()
        holder.tvBranch.text = product?.txProduct?.txBranch

        Glide.with(holder.imgProduk.context)
            .load(product?.msProduct?.msImage)
            .into(holder.imgProduk)

        holder.cardView.setOnClickListener {
            onProductClickListener.onProductClicked(product!!)
        }
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.tvName
        val tvPrice: TextView = view.tvPrice
        val tvBranch: TextView = view.tvBranch
        val imgProduk: ImageView = view.ivProduct
        val cardView: CardView = view.cardView

    }


    interface ProductClickListener {
        fun onProductClicked(data: Product)
    }

}
