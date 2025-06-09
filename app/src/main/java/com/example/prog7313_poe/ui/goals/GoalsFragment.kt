package com.example.prog7313_poe.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.databinding.FragmentGoalsBinding

class GoalsFragment : Fragment() {

    // Goal
    private lateinit var  monthlyGoal : TextView
    private lateinit var minGoal : TextView
    private lateinit var  maxGoal: TextView

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
        monthlyGoal  = view.findViewById(R.id.monthlyGoalView)
        minGoal = view.findViewById(R.id.minimumGoalView)
        maxGoal = view.findViewById(R.id.maximumGoalView)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")?: ""

        val goalsViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]

        // Observe goal LiveData
        goalsViewModel.goal.observe(viewLifecycleOwner){goal ->
            if(goal!=null){
                monthlyGoal.text ="${goal.month}"
                minGoal.text = "${goal.minimum}"
                maxGoal.text = "${goal.maximum}"
            }else{
                monthlyGoal.text ="No goal set for this month"
                minGoal.text = ""
                maxGoal.text = ""
            }
        }
        //Trigger fetch
        goalsViewModel.fetchGoal(userID)

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