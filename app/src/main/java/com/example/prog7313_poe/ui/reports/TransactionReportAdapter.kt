package com.example.prog7313_poe.ui.reports

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.example.prog7313_poe.classes.Expense
import com.bumptech.glide.Glide


class TransactionReportAdapter: RecyclerView.Adapter<TransactionReportAdapter.MyViewHolder>() {
    // A list that holds all Category items to be displayed in the RecyclerView
    private var transactionList = emptyList<ExpenseWithPhoto>()

    // ViewHolder class that holds the view for each item in the RecyclerView
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTransaction = transactionList[position]

        holder.itemView.findViewById<TextView>(R.id.cr_transaction_description).text = currentTransaction.description.toString()
        holder.itemView.findViewById<TextView>(R.id.cr_transaction_category).text = currentTransaction.categoryID.toString()
        holder.itemView.findViewById<TextView>(R.id.cr_transaction_date).text = currentTransaction.date.toString()
        holder.itemView.findViewById<TextView>(R.id.cr_transaction_amount).text = currentTransaction.amount.toString()

        val imageView = holder.itemView.findViewById<ImageView>(R.id.cr_transaction_photo)
        Log.d("TransactionAdapter","Photo URI: ${currentTransaction.fileUri}")

        val photoUri = currentTransaction.fileUri
        Glide.with(holder.itemView.context)
            .load(photoUri)
            .into(imageView)
    }

    // Updates the data list and notifies the RecyclerView to refresh the UI
    fun setData(expenseWithPhoto: List<ExpenseWithPhoto>) {
        this.transactionList = expenseWithPhoto
        notifyDataSetChanged()
    }
}