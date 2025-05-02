package com.example.prog7313_poe.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category

class CategoriesReportAdapter: RecyclerView.Adapter<CategoriesReportAdapter.MyViewHolder>(){
    // A list that holds all Category items to be displayed in the RecyclerView
    private var categoriesList = emptyList<Category>()

    // ViewHolder class that holds the view for each item in the RecyclerView
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    // Called when RecyclerView needs a new ViewHolder for an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    // Returns the number of items in the data list
    override fun getItemCount(): Int {
        return categoriesList.size
    }

    // Binds data to each item in the RecyclerView at a specific position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentCategory = categoriesList[position]


        holder.itemView.findViewById<TextView>(R.id.customCategoryName).text = currentCategory.categoryName.toString()

        holder.itemView.findViewById<TextView>(R.id.customCategoryTotal).text = currentCategory.description.toString()
    }

    // Updates the data list and notifies the RecyclerView to refresh the UI
    fun setData(category: List<Category>) {
        this.categoriesList = category
        notifyDataSetChanged()
    }


}

