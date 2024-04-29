package com.example.billwise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.billwise.models.Item
import com.google.firebase.database.FirebaseDatabase

class EditItem : AppCompatActivity() {
    private lateinit var nameEditText: TextView
    private lateinit var wattageEditText: TextView
    private lateinit var qtyEditText: TextView
    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        // Initialize the TextViews
        nameEditText = findViewById(R.id.NameItem)
        wattageEditText = findViewById(R.id.wattageItem)
        qtyEditText = findViewById(R.id.QuantityItem)

        // Initialize the Buttons
        val submitButton = findViewById<Button>(R.id.btnEditItem)
        val deleteButton = findViewById<Button>(R.id.DeleteItem)

        setValuesInView()

        submitButton.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("name") ?: "",
                intent.getStringExtra("wattage") ?: "",
                intent.getStringExtra("quantity") ?: "",
                intent.getStringExtra("id") ?: ""
            )
        }

        deleteButton.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("id") ?: ""
            )
        }
    }

    private fun deleteRecord(id: String) {
        val dbref = FirebaseDatabase.getInstance().getReference("Items").child(id)
        val mTask = dbref.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Category Deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Categories::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValuesInView() {
        nameEditText.text = intent.getStringExtra("name") ?: ""
        wattageEditText.text = intent.getStringExtra("wattage") ?: ""
        qtyEditText.text = intent.getStringExtra("quantity") ?: ""
    }

    private fun openUpdateDialog(
        name: String,
        wattage: String,
        quantity: String,
        id: String,
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.item_update_dialog, null)

        mDialog.setView(mDialogView)

        val edtItemName = mDialogView.findViewById<EditText>(R.id.edtItemName)
        val edtItemWattage = mDialogView.findViewById<EditText>(R.id.edtItemWattage)
        val edtItemQuantity = mDialogView.findViewById<EditText>(R.id.edtItemQuantity)
        val btnUpCat = mDialogView.findViewById<Button>(R.id.btnUpItem)

        edtItemName.setText(intent.getStringExtra("name").toString())
        edtItemWattage.setText(intent.getStringExtra("wattage").toString())
        edtItemQuantity.setText(intent.getStringExtra("quantity").toString())

        mDialog.setTitle("Updating $name Record ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpCat.setOnClickListener {
            updateItem(
                edtItemName.text.toString(),
                edtItemWattage.text.toString(),
                edtItemQuantity.text.toString(),
                id
            )

            Toast.makeText(applicationContext, "Item Data Updated", Toast.LENGTH_SHORT).show()

            nameEditText.text = edtItemName.text.toString()
            wattageEditText.text = edtItemWattage.text.toString()
            qtyEditText.text = edtItemQuantity.text.toString()

            alertDialog.dismiss()

            val intent = Intent(this, Categories::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateItem(

        name : String,
        wattage: String,
        quantity: String,
        id: String

    ){
        val dbref = FirebaseDatabase.getInstance().getReference("Items").child(id)
        val ItemInfo = Item(name,wattage,quantity,id)
        dbref.setValue(ItemInfo)
    }


}




