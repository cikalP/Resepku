package com.example.resepku.CRUD

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.resepku.CRUD.DB.itemDb
import com.example.resepku.R
import com.example.resepku.databinding.ActivityDetailResepBinding
import com.squareup.picasso.Picasso

class DetailResep : AppCompatActivity() {
    private lateinit var selectedItem : itemDb
    private lateinit var binding: ActivityDetailResepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResepBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        selectedItem = intent.getSerializableExtra("items") as itemDb
//
//        val tvJudul = selectedItem.judul
//        val bahan = selectedItem.bahan2
//        val langkah = selectedItem.langkah2
//
//        binding.tvNamamasak.text = selectedItem.judul
//        binding.tvBahan.text = selectedItem.bahan2
//        binding.tvLangkah.text = selectedItem.langkah2
//
//        Picasso.get().load("image/*").into(binding.imageView)

    }
}