package com.example.prog7313_poe.ui.goals

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
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.ui.loginRegister.LoginViewModel

class NewGoalsFragment : Fragment() {
    private lateinit var input_month : EditText
    private lateinit var input_minimum : EditText
    private lateinit var input_maximum : EditText
    private  lateinit var goalButton : Button

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
        val userID = sharedPreferences.getInt("user_id",-1)


        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Save Transaction button click Listener, No logic for ID
        //---------------------------------------------------------------------------------------------------------------------------------------//
//        goalButton.setOnClickListener{
//            val month = input_month.text.toString()
//            val min = input_minimum.text.toString()
//            val max = input_maximum.text.toString()
//
//            if(validateInput(month,max,min)){
//                val goal = Goal()
//                if(goal.createGoal("",month,"",max, min)){
//                    Toast.makeText(requireContext(), "New goal created", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(requireContext(), "Could not create new goal, try again", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }

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