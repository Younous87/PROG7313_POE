package com.example.prog7313_poe.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.ExpenseWithPhoto

class CategoriesReportAdapter():
    RecyclerView.Adapter<CategoriesReportAdapter.MyViewHolder>(){

    // A list that holds all Category items to be displayed in the RecyclerView
    private var categoriesList = emptyList<CategorySpending>()

    // ViewHolder class that holds the view for each item in the RecyclerView
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }


    // Called when RecyclerView needs a new ViewHolder for an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    // Binds data to each item in the RecyclerView at a specific position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        val categoryName : TextView = view.findViewById(R.id.customCategoryName)
//        val totalAmount : TextView = view.findViewById(R.id.customCategoryTotal)

        val item = categoriesList[position]
//        holder.categoryName.text = item.categoryName
//        holder.totalAmount.text = "${item.totalAmount}"

        holder.itemView.findViewById<TextView>(R.id.customCategoryName).text = item.categoryName.toString()
        holder.itemView.findViewById<TextView>(R.id.customCategoryTotal).text = item.totalAmount.toString()

    }
    override fun getItemCount(): Int = categoriesList.size

    // Updates the data list and notifies the RecyclerView to refresh the UI
    fun setData(category: List<CategorySpending>) {
        this.categoriesList = category
        notifyDataSetChanged()
    }


}

