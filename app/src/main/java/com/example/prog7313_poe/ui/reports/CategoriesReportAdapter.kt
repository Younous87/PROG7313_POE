package com.example.prog7313_poe.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending

class CategoriesReportAdapter(private val data: List<CategorySpending>):
    RecyclerView.Adapter<CategoriesReportAdapter.MyViewHolder>(){

    // ViewHolder class that holds the view for each item in the RecyclerView
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val categoryName : TextView = view.findViewById(R.id.customCategoryName)
        val totalAmount : TextView = view.findViewById(R.id.customCategoryTotal)
    }


    // Called when RecyclerView needs a new ViewHolder for an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    // Binds data to each item in the RecyclerView at a specific position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = data[position]
        holder.categoryName.text = item.categoryName
        holder.totalAmount.text = "${item.totalAmount}"

    }
    override fun getItemCount(): Int = data.size

    // Updates the data list and notifies the RecyclerView to refresh the UI
//    fun setData(category: List<Category>) {
//        this.categoriesList = category
//        notifyDataSetChanged()
//    }


}

