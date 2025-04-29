package com.example.prog7313_poe.ui.goals

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prog7313_poe.R

class NewGoalsFragment : Fragment() {

    companion object {
        fun newInstance() = NewGoalsFragment()
    }

    private val viewModel: NewGoalsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_goals, container, false)
    }
}