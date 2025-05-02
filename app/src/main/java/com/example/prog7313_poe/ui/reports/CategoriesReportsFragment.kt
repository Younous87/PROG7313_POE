package com.example.prog7313_poe.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R

class CategoriesReportsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_categories_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startInput   = view.findViewById<EditText>(R.id.dateStartInput)
        val endInput     = view.findViewById<EditText>(R.id.dateEndInput)
        val searchButton = view.findViewById<Button>(R.id.newGoalSaveButton)

        searchButton.setOnClickListener {
            val start = startInput.text.toString().trim()
            val end   = endInput.text.toString().trim()

            val action = CategoriesReportsFragmentDirections
                .actionCategoriesReportsFragmentToNavigationCategories(start, end)

            findNavController().navigate(action)
        }
    }
}
