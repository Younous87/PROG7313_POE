package com.example.prog7313_poe.ui.transactions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*
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
import androidx.lifecycle.lifecycleScope
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.Photo
import com.example.prog7313_poe.databinding.FragmentTransactionsBinding
import kotlinx.coroutines.launch



class TransactionsFragment : Fragment() {
    private lateinit var input_date: EditText
    private lateinit var input_time: EditText
    private lateinit var input_description: EditText
    private lateinit var input_amount: EditText
    private lateinit var transactionButton: Button
    private lateinit var input_category: AutoCompleteTextView
    private lateinit var addPhotoButton: ImageButton
    private lateinit var label_photo: TextView
    private var selectedImageUri: Uri? = null
    private var savedPhoto: Photo? = null
    private var type: RadioGroup? = null
    private lateinit var selectedRadioButton: RadioButton
    private lateinit var transactionsViewModel : TransactionsViewModel
    //private lateinit var photoViewModel: PhotoViewModel

    private val calendar = Calendar.getInstance()
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionsViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        //photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_date = view.findViewById(R.id.transactionDateInput)
        input_time = view.findViewById(R.id.transactionTimeInput)
        input_description = view.findViewById(R.id.transactionNameInput)
        input_amount = view.findViewById(R.id.transactionAmountInput)
        input_category = view.findViewById(R.id.autoCompleteCategoryInput)
        addPhotoButton = view.findViewById(R.id.transactionAddPhotoButton)
        label_photo = view.findViewById(R.id.transactionPhotoInput)
        transactionButton = view.findViewById(R.id.transactionSaveButton)
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "") ?: ""

        setupDateTimePickers()

        addPhotoButton.setOnClickListener {
            pickFileLauncher.launch("image/*")
        }

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Save Transaction button click Listener, No logic for ID
        //---------------------------------------------------------------------------------------------------------------------------------------//
        transactionButton.setOnClickListener {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val date: Date? = dateFormat.parse(input_date.text.toString())
            val time: Date? = timeFormat.parse(input_time.text.toString())

            val description = input_description.text.toString()
            val amount = input_amount.text.toString()
            val category = input_category.text.toString()
            val photoName = label_photo.text.toString()
            type = view.findViewById(R.id.transactionGroup)
            var selectedValue = ""

            type?.let {
                val selectedTypeID = it.checkedRadioButtonId
                if(selectedTypeID != -1){
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedTypeID)
                    selectedValue = selectedRadioButton.text.toString()
                }
            } ?: run {
                Toast.makeText(requireContext(), "RadioGroup not found", Toast.LENGTH_SHORT).show()
            }
            // Sends data to the validation method
//            if(validateInput(date,time,description,amount,category)){
//                var photoID : Int? = null
//                if(savedPhoto!= null){
//                    var photo = Photo(0, filename = savedPhoto!!.filename, fileUri = savedPhoto!!.fileUri)
//                    photoViewModel.insertPhoto(photo)
//                    lifecycleScope.launch {
//                        photoID = photoViewModel.getPhotoIdByFilenameAndUri(
//                            filename = savedPhoto!!.filename.toString(),
//                            fileUri = savedPhoto!!.fileUri.toString())
//                    }
//                }
//
//
//                val expense = Expense(
//                    expenseID =  0,
//                    time =  time,
//                    date =  date,
//                    categoryID = 0,
//                    description =  description,
//                    amount = amount.toDouble(),
//                    photoID = photoID?.toString(),
//                    transactionType = selectedValue,
//                    userID = userID
//                )
//
//                transactionsViewModel.insertTransaction(
//                    expense,
//                    onSuccess = { rowId ->
//                        if (rowId != -1L) {
//                            Toast.makeText(requireContext(), "Transaction created", Toast.LENGTH_SHORT).show()
//                            clearForm()
//                        }
//                    },
//                    onError = { error ->
//                        Toast.makeText(requireContext(), "Could not create transaction, try again", Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }
        }
    }
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Get transaction type name
        //---------------------------------------------------------------------------------------------------------------------------------------//

    private fun setupDateTimePickers() {
        input_date.isFocusable = false
        input_date.isClickable = true
        input_time.isFocusable = false
        input_time.isClickable = true

        updateDateDisplay()
        updateTimeDisplay()

        input_date.setOnClickListener { showDatePicker() }
        input_time.setOnClickListener { showTimePicker() }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateDisplay()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateTimeDisplay()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun updateDateDisplay() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        input_date.setText(dateFormat.format(calendar.time))
    }

    private fun updateTimeDisplay() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        input_time.setText(timeFormat.format(calendar.time))
    }

    private fun clearForm() {
        input_description.setText("")
        input_amount.setText("")
        input_category.setText("")
        label_photo.text = "No photo selected"
        savedPhoto = null
        selectedImageUri = null

        calendar.time = Date()
        updateDateDisplay()
        updateTimeDisplay()
        type?.clearCheck()
    }

    private val pickFileLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){ uri ->
        if(uri != null){
            //Save file into var
            selectedImageUri = uri
            val fileName = uri.lastPathSegment?.split("/")?.last()
            label_photo.text = fileName
            //savedPhoto = Photo(0,fileName,uri)
        }
    }

    private fun validateInput(date: Date?, time: Date?, description: String, amount: String, category: String): Boolean {
        if (date == null) {
            input_date.error = "Date is invalid"
            return false
        }
        if (time == null) {
            input_time.error = "Time is invalid"
            return false
        }
        if (amount.isEmpty()) {
            input_amount.error = "Amount cannot be empty"
            return false
        }
        if (description.isEmpty()) {
            input_description.error = "Description cannot be empty"
            return false
        }
        if (category.isEmpty()) {
            input_category.error = "Category cannot be empty"
            return false
        }
        return true
    }
}

