package com.example.billwise

import SessionManager
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.billwise.databinding.ActivityAccInfomationBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccInfomation : AppCompatActivity() {
    private lateinit var binding: ActivityAccInfomationBinding
    private lateinit var database: DatabaseReference
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityAccInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }



        val userEmail = sessionManager.getEmail()
        database = FirebaseDatabase.getInstance().reference

        val userRef = database.child("Users")
        val query = userRef.orderByChild("email").equalTo(userEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.value as HashMap<String, String>
                        val name = user["name"]
                        val email = user["email"]
                        val phoneNumber = user["phoneNumber"]
                        val id = user["id"]

                        binding.tvNameTop.text = name
                        binding.tvEmailTop.text = email
                        binding.tvNameAcc.text = name
                        binding.tvEmailAc.text = email
                        binding.tvPhone.text = phoneNumber
                        binding.tvId.text = id


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read user details", error.toException())
            }
        })


        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, AccountInformationUpdate::class.java)
            startActivity(intent)
        }

        binding.btnBackAccInfo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}