package com.example.prog7313_poe.ui.goals

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.ui.loginRegister.LoginViewModel

class NewGoalsFragment : Fragment() {
    private lateinit var input_month : EditText
    private lateinit var input_minimum : EditText
    private lateinit var input_maximum : EditText
    private  lateinit var goalButton : Button

    companion object {
        fun newInstance() = NewGoalsFragment()
    }

    //private val viewModel: NewGoalsViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        input_month = view.findViewById(R.id.monthNewGoalInput )
        input_minimum = view.findViewById(R.id.minimumNewGoalInput)
        input_maximum = view.findViewById(R.id.maximumNewGoalInput)
        goalButton = view.findViewById(R.id.newGoalSaveButton)

        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")


        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Save Transaction button click Listener, No logic for ID
        //---------------------------------------------------------------------------------------------------------------------------------------//
        goalButton.setOnClickListener{
            val month = input_month.text.toString()
            val min = input_minimum.text.toString()
            val max = input_maximum.text.toString()

            // validate input
//            if(validateInput(month,max,min)){
//
//                val goal = Goal(userID =  userID, month = month, minimum = min, maximum =  max)
//                viewModel.insertBudgetGoal(goal)
//                // Validate goal
//                viewModel.validateGoal(userID.toString(),month, max).observe(viewLifecycleOwner){ goal ->
//                    if(goal != null){
//                        Toast.makeText(context, "Goal created", Toast.LENGTH_SHORT).show()
//
//                    }else{
//                        Toast.makeText( context, "Please try again", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }

        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Goal Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun validateInput(month: String, minimum: String, maximum: String): Boolean {
        if(month.isEmpty()){
            input_month.error = "Month cannot be empty"
            return false
        }
        if (minimum.isEmpty()) {
            input_minimum.error = "Minimum Goal cannot be empty"
            return false
        }
        if(maximum.isEmpty()){
            input_maximum.error = "Maximum Goal cannot be empty"
        }


        return true
    }
}