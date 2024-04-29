package com.example.billwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.billwise.databinding.ActivityAddItemBinding
import com.example.billwise.models.Item
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddItem : AppCompatActivity() {

        private lateinit var binding :ActivityAddItemBinding
        private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AddSubmit.setOnClickListener{
            var name = binding.iName.text.toString()
            var wattage = binding.iWattage.text.toString()
            var quantity = binding.iQuantity.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Items")
            var id = database.push().key

            val item = Item(name,wattage,quantity,id)
            database.child(id!!).setValue(item).addOnCompleteListener{

                binding.iName.text.clear()
                binding.iWattage.text.clear()
                binding.iQuantity.text.clear()

                Toast.makeText(this,"Successfully Added", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Categories::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()

            }
        }



    }
}
