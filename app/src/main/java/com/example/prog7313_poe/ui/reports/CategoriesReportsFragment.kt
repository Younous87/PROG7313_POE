package com.example.prog7313_poe.ui.reports

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.prog7313_poe.R
import com.example.prog7313_poe.ui.goals.GoalsViewModel

class CategoriesReportsFragment : Fragment() {

    //goalViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]
    //val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
    //val userID = sharedPreferences.getString("user_id",null)

    companion object {
        fun newInstance() = CategoriesReportsFragment()
    }

    private val viewModel: CategoriesReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //goalViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_categories_reports, container, false)
    }
}