package com.example.prog7313_poe.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.databinding.FragmentGoalsBinding

class GoalsFragment : Fragment() {

    lateinit var newGoalButton : Button
    private var _binding: FragmentGoalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val goalsViewModel =
            ViewModelProvider(this).get(GoalsViewModel::class.java)

        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        newGoalButton = view.findViewById(R.id.button)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Goals button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        newGoalButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_goals_to_newGoalsFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}