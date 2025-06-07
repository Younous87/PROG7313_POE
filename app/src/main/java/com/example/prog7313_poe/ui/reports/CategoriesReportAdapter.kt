package com.example.prog7313_poe.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.CategorySpending

class CategoriesReportAdapter(
    private var items: List<CategorySpending>
):  RecyclerView.Adapter<CategoriesReportAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val categoryName: TextView  = itemView.findViewById(R.id.customCategoryName)
        val totalAmount: TextView   = itemView.findViewById(R.id.customCategoryTotal)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)
        return CategoryViewHolder(itemView)
    }
        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.categoryName.text  = item.categoryName
        holder.totalAmount.text = "%.2f".format(item.totalAmount)
        }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<CategorySpending>){
        items = newItems
        notifyDataSetChanged()
    }

}
