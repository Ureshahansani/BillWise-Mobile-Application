package com.example.billwise

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.billwise.databinding.ActivityRegistrationBinding
import com.example.billwise.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth

        //onClickListener for the register button
        binding.btnCreateAcc.setOnClickListener {

            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val phoneNumber = binding.edtPhone.text.toString()
            val password = binding.edtPwd.text.toString()
            val rePassword = binding.edtRePwd.text.toString()

            if (name.isEmpty()) {
                binding.edtName.error = "Name is Empty"
            }else if (!isValidName(name)) {
                binding.edtName.error = "Invalid name"
            }else if (email.isEmpty()) {
                binding.edtEmail.error = "Email is Empty"
            }else if (!isValidEmail(email)) {
                binding.edtEmail.error = "Invalid email"
            } else if (phoneNumber.isEmpty()) {
                binding.edtPhone.error = "Phone Number is Empty"
            }else if (password.isEmpty()) {
                binding.edtPwd.error = "Password is Empty"
            } else if (!isValidPassword(password)) {
                binding.edtPwd.error = "Password is Invalid"
            } else if (rePassword.isEmpty()) {
                binding.edtRePwd.error = "Password is Empty"
            } else if (password != rePassword) {
                binding.edtRePwd.error = "Password not Matching"
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User registered successfully in Firebase Authentication
                            val user = auth.currentUser
                            val Id = user!!.uid
                            val userData = User(name, email, phoneNumber, password, rePassword, Id)

                            database = FirebaseDatabase.getInstance().getReference("Users")
                            database.child(Id).setValue(userData).addOnSuccessListener {
                                binding.edtName.text.clear()
                                binding.edtEmail.text.clear()
                                binding.edtPhone.text.clear()
                                binding.edtPwd.text.clear()
                                binding.edtRePwd.text.clear()

                                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, Login::class.java)
                                finish()
                                startActivity(intent)
                            }.addOnFailureListener {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // User registration failed
                            Toast.makeText(baseContext, "Registration failed, please try again later.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    //validation functions
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidName(name: String): Boolean {
        return name.isNotEmpty() && name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\$@!%*?&])[A-Za-z\\d\$@!%*?&]{8,}$".toRegex()
        return password.isNotEmpty() && password.matches(pattern)
    }

    fun buttonLogin(v: View){

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

}