package com.example.resepku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resepku.CRUD.Adapter.itemAdapter
import com.example.resepku.CRUD.DB.itemDb
import com.example.resepku.CRUD.DetailResep
import com.example.resepku.CRUD.Favorite
import com.example.resepku.CRUD.TambahResep
import com.example.resepku.User.profile
import com.example.resepku.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<itemDb>
    private val nodeList = ArrayList<String>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemRecyclerView =findViewById(R.id.rv_utama)
        itemRecyclerView.layoutManager = GridLayoutManager(this,2)
        itemRecyclerView.hasFixedSize()
        itemArrayList = arrayListOf<itemDb>()
        getItemData()
        binding.fabTambah.setOnClickListener{
            val intent = Intent(this,TambahResep::class.java)
            startActivity(intent)
        }
        binding.ivUsers.setOnClickListener{
            val intent = Intent(this,profile::class.java)
            startActivity(intent)
        }
        binding.ivLove.setOnClickListener{
            val intent = Intent(this,Favorite::class.java)
            startActivity(intent)
        }

    }

    private fun getItemData() {
        db = FirebaseDatabase.getInstance().getReference("items")
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (itmsnapshot in snapshot.children){
                        var item = itmsnapshot.getValue(itemDb::class.java)
                        itemArrayList.add(item!!)
                        nodeList.add(itmsnapshot.key.toString())
                    }
                    var adapter = itemAdapter(itemArrayList)
                    itemRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : itemAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val nodePath:String = nodeList[position]
                            val intent = Intent(this@MainActivity, DetailResep::class.java)
                            intent.putExtra("itm_id", nodePath)
                            startActivity(intent)
//                            setResult(2,intent)
//                            finish()
                        }

                    })
                    itemRecyclerView.adapter = adapter

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}