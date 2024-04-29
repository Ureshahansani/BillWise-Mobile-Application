package com.example.billwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadItems : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var wattage: TextView
    private lateinit var qty: TextView
    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_items)

        name = findViewById(R.id.r_name)
        wattage = findViewById(R.id.r_wattage)
        qty = findViewById(R.id.r_qty)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Items").limitToLast(1)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lastchild = dataSnapshot.children.last() // get the first child node

                recordId = lastchild.key // store the record ID as a class-level variable
                val rname = lastchild.child("name").value?.toString()
                val rwattage = lastchild.child( "wattage").value?.toString()
                val rqty = lastchild.child("quantity").value?.toString()

                name.text = rname
                wattage.text = rwattage
                qty.text = rqty


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ReadItem", "Failed to read value.", error.toException())
            }
        })

        val deleteButton: Button = findViewById(R.id.delete_btn)
        deleteButton.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("Items")
            val recordReference = databaseReference.child(recordId ?: "")

            Log.d("DeleteIncome", "Deleting record with ID: $recordId")

            // Remove the record from Firebase
            recordReference.removeValue()

            val intent = Intent(this@ReadItems, ReadItems::class.java)
            startActivity(intent)
            finish()
        }

        val editButton: Button = findViewById(R.id.edit_btn)
        editButton.setOnClickListener {
            val intent = Intent(this@ReadItems, EditItem::class.java)
            intent.putExtra("recordId", recordId)
            startActivity(intent)
        }
    }
}
