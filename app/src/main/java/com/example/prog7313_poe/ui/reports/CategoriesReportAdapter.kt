//package com.example.prog7313_poe.ui.reports
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.prog7313_poe.R
//
//class CategoriesReportAdapter :
//    RecyclerView.Adapter<CategoriesReportAdapter.MyViewHolder>() {
//
//    // start with an empty list
//    private var data: List<CategorySpending> = emptyList()
//
//    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val categoryName: TextView  = view.findViewById(R.id.customCategoryName)
//        val totalAmount: TextView   = view.findViewById(R.id.customCategoryTotal)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.custom_row, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = data[position]
//        holder.categoryName.text  = item.categoryName
//        holder.totalAmount.text   = "R ${"%.2f".format(item.totalAmount)}"
//    }
//
//    override fun getItemCount(): Int = data.size
//
//    /** Replace the adapter’s data and refresh the list */
//    fun setData(newData: List<CategorySpending>) {
//        data = newData
//        notifyDataSetChanged()
//    }
//}
