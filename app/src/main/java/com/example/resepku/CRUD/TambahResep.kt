package com.example.resepku.CRUD

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.resepku.CRUD.DB.itemDb
import com.example.resepku.MainActivity
import com.example.resepku.R
import com.example.resepku.databinding.ActivityTambahResepBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.lang.Exception

class TambahResep : AppCompatActivity() {
    var sImage: String? = ""
    var nodeId = ""
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityTambahResepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahResepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackTambah.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun insertData(view: View){
        val judul = binding.etJudulResep.text.toString()
        val bahan = binding.etBahanBahan.text.toString()
        val langkah = binding.etLangkah.text.toString()

        // Check if any of the fields is empty
        if (judul.isEmpty() || bahan.isEmpty() || langkah.isEmpty()) {
            Toast.makeText(this, "Mohon,diisi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        db = FirebaseDatabase.getInstance().getReference("items")
        val item = itemDb(judul,bahan,langkah, sImage)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.child(id.toString()).setValue(item).addOnSuccessListener{
            binding.etJudulResep.text.clear()
            binding.etBahanBahan.text.clear()
            binding.etLangkah.text.clear()
            sImage = ""
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Berhasil,menambahkan Resep", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Resep tidak berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
//        val img = binding.etJudulResep.text.toString()
    }

    fun insertImg(view : View){
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)
    }
    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){  result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                val img = findViewById<ImageView>(R.id.cv_btn_img);

                if (sImage.isNullOrEmpty() ) {
                    Toast.makeText(this, "Mohon,diisi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                img.setImageBitmap(myBitmap)
                inputStream!!.close()
                Toast.makeText(this,"Gambar dipilih", Toast.LENGTH_SHORT).show()
            }catch (ex: Exception){
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
//        var i: Intent
//        i = Intent(this, MainActivity::class.java)
//        itemResultLauncher.launch(i)
    }

    private val itemResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == 2) {
            val intent = result.data
            if (intent != null){
                nodeId = intent.getStringExtra("itm_id").toString()
            }
            db = FirebaseDatabase.getInstance().getReference("items")
            db.child(nodeId).get().addOnSuccessListener {
                if (it.exists()){
                    binding.etJudulResep.setText(it.child("itemName").value.toString())
                    binding.etBahanBahan.setText(it.child("itemRate").value.toString())
                    binding.etLangkah.setText(it.child("itemUnit").value.toString())
                    sImage = it.child("itemImg").value.toString()
                    val bytes = Base64.decode(sImage, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                    val img = findViewById<ImageView>(R.id.cv_btn_img);
                    img.setImageBitmap(bitmap)

                }else{
                    Toast.makeText(this, "item tidak di temukan", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

//    fun showList(view: View) {
//        var i: Intent
//        i = Intent(this, MainActivity::class.java)
//        itemResultLauncher.launch(i)
//    }
}