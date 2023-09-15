package com.example.resepku.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Patterns
import android.widget.Toast
import com.example.resepku.MainActivity
import com.example.resepku.R
import com.example.resepku.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPrefernces: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        sharedPrefernces = PreferenceManager.getDefaultSharedPreferences(this)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isLoggedIn = sharedPrefernces.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

//        binding.tvForgotPassword.setOnClickListener{
//            startActivity(Intent(this,Register::class.java))
//        }
//
        binding.tvDaftar.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
        }

        binding.btnMasuk.setOnClickListener{
            val email = binding.itEmail.text.toString()
            val password = binding.itPassword.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.itEmail.error = "Email Harus Diisi"
                binding.itEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.itEmail.error = "Email Tidak Valid"
                binding.itEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.itPassword.error = "Password Harus Diisi"
                binding.itPassword.requestFocus()
                return@setOnClickListener
            }
            LoginFirebase(email,password)
        }

    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()

                    val editor = sharedPrefernces.edit()
                    editor.putBoolean("isLoggedIn",true)
                    editor.apply()

                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}