package com.example.billwise


import SessionManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billwise.databinding.ActivityHomeUsageBinding
import com.example.billwise.models.Bill
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeUsage : AppCompatActivity() {

    private lateinit var binding: ActivityHomeUsageBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var History : Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseBinding()

        dbRef = FirebaseDatabase.getInstance().getReference("Home_usage")

        // Initialize the sessionManager property
        sessionManager = SessionManager(this)

        // Check if the user is logged in
        if (!::sessionManager.isInitialized || !sessionManager.isLoggedIn()) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding.update.setOnClickListener{
            val intent = Intent(this, CalHistory::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonDisplay.setOnClickListener{
            calculateHomeUsage()
        }
        binding.delete.setOnClickListener{
            delete()
        }
        binding.save.setOnClickListener {
            saveData()
        }
    }

    private fun initialiseBinding() {
        binding = ActivityHomeUsageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun calculateHomeUsage(){
        val wottage = binding.wattage.text.toString()
        val quantity = binding.quantity.text.toString()
        val dailyusage = binding.dailyUsage.text.toString()
        val unitprice = binding.unitPrice.text.toString()

        if (wottage.isEmpty() || quantity.isEmpty() || dailyusage.isEmpty() || unitprice.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_LONG).show()
            return
        }

        val W1 = wottage.toInt()
        val Q2 = quantity.toInt()
        val Hr3 = dailyusage.toFloat()
        val unitPrice = unitprice.toFloat()

        val result = (((Hr3*30)*W1/1000)*unitPrice*Q2)

        val displayString = result.toString()
        binding.resultText.text =displayString
    }

    private fun delete(){
        binding.resultText.text = null
        binding.wattage.text?.clear()
        binding.quantity.text?.clear()
        binding.dailyUsage.text?.clear()
        binding.unitPrice.text?.clear()
    }

    private fun saveData() {
        val wottage = binding.wattage.text.toString()
        val quantity = binding.quantity.text.toString()
        val dailyusage = binding.dailyUsage.text.toString()
        val unitprice = binding.unitPrice.text.toString()

        if (wottage.isEmpty() || quantity.isEmpty() || dailyusage.isEmpty() || unitprice.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_LONG).show()
            return
        }

        val W1 = wottage.toInt()
        val Q2 = quantity.toInt()
        val Hr3 = dailyusage.toFloat()
        val unitPrice = unitprice.toFloat()

        val result = (((Hr3*30)*W1/1000)*unitPrice*Q2)

        val displayString = result.toString()
        binding.resultText.text =displayString

        val id = dbRef.push().key!!
        val email = sessionManager.getEmail()
        val bill = Bill(wottage, quantity, dailyusage, unitprice, email, result.toDouble())

        dbRef.child(id).setValue(bill).addOnCompleteListener {

            delete()
            Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
    fun buttonLogin(v: View){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()//destroy the current activity
    }

}
