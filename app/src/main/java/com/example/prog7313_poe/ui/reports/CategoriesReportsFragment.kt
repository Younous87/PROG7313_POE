package com.example.prog7313_poe.ui.reports

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prog7313_poe.R

class CategoriesReportsFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesReportsFragment()
    }

    private val viewModel: CategoriesReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_categories_reports, container, false)
    }
}