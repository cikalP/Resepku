package com.example.resepku.CRUD

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.resepku.CRUD.DB.itemDb
import com.example.resepku.R
import com.example.resepku.databinding.ActivityEditResepBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditResep : AppCompatActivity() {
    private lateinit var binding:ActivityEditResepBinding
    var sImage: String? = ""
    var nodeId = ""
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditResepBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun update_data(view: View) {
        val itemName = binding.etJudulResep.text.toString()
        val itemRate = binding.etBahanBahan.text.toString()
        val itemUnit = binding.etLangkah.text.toString()
        db = FirebaseDatabase.getInstance().getReference("items")
        val item = itemDb(itemName, itemRate, itemUnit, sImage)

        db.child(nodeId).setValue(item).addOnSuccessListener {
            binding.etJudulResep.text.clear()
            binding.etBahanBahan.text.clear()
            sImage = ""
            Toast.makeText(this, "Berhasil,di ubah", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Tidak,berhasil di ubah", Toast.LENGTH_SHORT).show()
        }
    }


    fun delete_data(view: View) {
        db = FirebaseDatabase.getInstance().getReference("items")
        db.child(nodeId).removeValue()

    }
}