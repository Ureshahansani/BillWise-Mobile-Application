package com.example.billwise

import SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billwise.adaptors.MyAdaptorS
import com.example.billwise.databinding.ActivityCalHistoryBinding
import com.example.billwise.models.Bill
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CalHistory : AppCompatActivity() {

    private lateinit var sessionManager : SessionManager
    private lateinit var binding : ActivityCalHistoryBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var billRecyclerView : RecyclerView
    private lateinit var billArrayList : ArrayList<Bill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        billRecyclerView = findViewById(R.id.rvH)
        billRecyclerView.layoutManager = LinearLayoutManager(this)
        billRecyclerView.setHasFixedSize(true)

        billArrayList = arrayListOf<Bill>()
        getBillData()
    }

    private fun getBillData() {
        val userEmail = sessionManager.getEmail()
        dbref = FirebaseDatabase.getInstance().getReference("Home_usage")
        dbref.orderByChild("email").equalTo(userEmail).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billArrayList.clear()
                if(snapshot.exists()){
                    for (billSnapshot in snapshot.children){
                        val bill = billSnapshot.getValue(Bill::class.java)
                        billArrayList.add(bill!!)
                    }

                    val myAdaptorS = MyAdaptorS(billArrayList)
                    billRecyclerView.adapter = myAdaptorS
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("CalHistory", "Error: ${error.message}")
            }
        })
    }
    fun buttonLogin(v: View){

        val intent = Intent(this, HomeUsage::class.java)
        startActivity(intent)
        finish()//destroy the current activity
    }
}
