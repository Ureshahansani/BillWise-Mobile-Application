package com.example.billwise


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billwise.adaptors.MyAdaptor
import com.example.billwise.databinding.ActivityCategoriesBinding
import com.example.billwise.models.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Categories : AppCompatActivity() {

    private lateinit var itemRecyclerView : RecyclerView
    private lateinit var itemList : ArrayList<Item>
    private lateinit var dbref : DatabaseReference
    private lateinit var binding : ActivityCategoriesBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        itemRecyclerView = findViewById(R.id.rvCat)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)

        itemList = arrayListOf<Item>()
        getItemName()









        lateinit var btn1: Button
        lateinit var btn2: Button

        btn1 = findViewById(R.id.Back)
        btn2 = findViewById(R.id.insert)

        btn1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener{
            val intent = Intent(this, AddItem::class.java)
            startActivity(intent)
        }
    }

    private fun getItemName(){

        dbref = FirebaseDatabase.getInstance().getReference("Items")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (itemSnapshot in snapshot.children){


                        val item = itemSnapshot.getValue(Item::class.java)
                        itemList.add(item!!)
                    }

                    val myAdaptor = MyAdaptor(itemList)
                    itemRecyclerView.adapter = myAdaptor
                    myAdaptor.setOnItemClickListner(object : MyAdaptor.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Categories, EditItem::class.java)
                            intent.putExtra("name" ,itemList[position].name)
                            intent.putExtra("wattage",itemList[position].wattage)
                            intent.putExtra("quantity",itemList[position].quantity)
                            intent.putExtra("id",itemList[position].id)
                            startActivity(intent)




                        }

                    })





                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}
