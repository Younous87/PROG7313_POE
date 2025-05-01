package com.example.prog7313_poe.ui.transactions

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.Photo
import com.example.prog7313_poe.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {
    private lateinit var input_date : EditText
    private lateinit var input_time : EditText
    private lateinit var input_description : EditText
    private lateinit var input_amount: EditText
    private lateinit var transactionButton : Button
    private lateinit var input_category : AutoCompleteTextView
    private lateinit var addPhotoButton : ImageButton
    private lateinit var label_photo : TextView
    private var selectedImageUri: Uri? = null
    private var savedPhoto : Photo? = null
    private var type : RadioGroup? = null
    private lateinit var selectedRadioButton: RadioButton

    private var _binding: FragmentTransactionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        transactionsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        input_date = view.findViewById(R.id.transactionDateInput)
        input_time = view.findViewById(R.id.transactionTimeInput)
        input_description = view.findViewById(R.id.transactionNameInput)
        input_amount = view.findViewById(R.id.transactionAmountInput)
        input_category = view.findViewById(R.id.autoCompleteCategoryInput)
        addPhotoButton = view.findViewById(R.id.transactionAddPhotoButton)
        label_photo = view.findViewById(R.id.transactionPhotoInput)
        transactionButton = view.findViewById(R.id.transactionSaveButton)
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getInt("user_id",-1)



        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Add Photo button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
//        addPhotoButton.setOnClickListener {
//            pickFileLauncher.launch("image/*")
//        }
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Save Transaction button click Listener, No logic for ID
        //---------------------------------------------------------------------------------------------------------------------------------------//
        transactionButton.setOnClickListener{
            val date = input_date.text.toString()
            val time = input_time.text.toString()
            val description = input_description.text.toString()
            val amount = input_amount.text.toString()
            val category = input_category.text.toString()
            val photoName = label_photo.text.toString()
            type = view.findViewById(R.id.transactionGroup)
            var selectedValue = ""

            type?.let {
                // Get Radio Button Name or The selected Transaction type
                val selectedTypeID = it.checkedRadioButtonId
                if(selectedTypeID != -1){
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedTypeID)
                    selectedValue = selectedRadioButton.text.toString()
                }
            } ?: run{
                Toast.makeText(requireContext(), "RadioGroup not found", Toast.LENGTH_SHORT).show()
            }
            // Sends data to the validation method
//            if(validateInput(date,time,description,amount,category)){
//
//                if(savedPhoto!= null){
//                    savedPhoto?.createPhoto(savedPhoto!!.fileUri!!, savedPhoto!!.filename)
//                }
//                val photoID = savedPhoto?.photoID?: ""
//
//                // Check if a photo was selected to add it to database
//                // Create expense
//                val expense = Expense()
//                if(expense.createExpense("",date, time,"",description,amount,photoID,"",selectedValue)){
//                    Toast.makeText(requireContext(), "Expense created", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(requireContext(), "Could not create expense, try again", Toast.LENGTH_SHORT).show()
//                }
//
//
//            }

        }
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Get transaction type name
        //---------------------------------------------------------------------------------------------------------------------------------------//


    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Select image and set values into photoLabel and SavedPhoto
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    private val pickFileLauncher = registerForActivityResult(
//        ActivityResultContracts.GetContent()
//    ){ uri ->
//        if(uri != null){
//            //Save file into var
//            selectedImageUri = uri
//            // Extract file name
//            val fileName = uri.lastPathSegment?.split("/")?.last()
//            label_photo.text = fileName
//            savedPhoto = Photo("",fileName,uri)
//        }
//
//    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Save Image
    //---------------------------------------------------------------------------------------------------------------------------------------//


    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Expense Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun validateInput(date: String, time: String, description: String,amount: String, category: String): Boolean {
        if(date.isEmpty()){
            input_date.error = "Date cannot be empty"
            return false
        }
        if (time.isEmpty()) {
            input_time.error = "Time cannot be empty"
            return false
        }
        if(amount.isEmpty()){
            input_amount.error = "Amount cannot be empty"
        }

        if (description.isEmpty()) {
            input_description.error = "Description cannot be empty"
            return false
        }

        if (category.isEmpty()) {
            input_category.error = "Description cannot be empty"
            return false
        }

        return true
    }




}