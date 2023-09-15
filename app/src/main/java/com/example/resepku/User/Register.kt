package com.example.resepku.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.resepku.R
import com.example.resepku.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //auth untuk menghubungkan firebase
        auth = FirebaseAuth.getInstance()

        //btn login
//        binding.tvSignIn.setOnClickListener {
//            startActivity(Intent(this,Login::class.java))
//        }

        binding.btnDaftar.setOnClickListener {
            val email = binding.itEmailDaftar.text.toString()
            val password = binding.itPasswordDaftar.text.toString()

            //Validasi Email
            if (email.isEmpty()){
                binding.itEmailDaftar.error = "Email Harus Diisi"
                binding.itEmailDaftar.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email Tidak Sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.itEmailDaftar.error = "Email Tidak Valid"
                binding.itEmailDaftar.requestFocus()
                return@setOnClickListener
            }

            //Validasi Panjang Password
            if (password.length < 6){
                binding.itPasswordDaftar.error = "Password Minimal 6 Karakter"
                binding.itPasswordDaftar.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email,password)
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    //intent untuk pindah ke tempat selanjutnya
                    startActivity(Intent(this,Login::class.java))
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}