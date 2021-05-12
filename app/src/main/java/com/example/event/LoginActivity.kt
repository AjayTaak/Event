package com.example.event

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.event.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.btnLogIn.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {
        if (binding.tvUsername.text.toString().isEmpty()) {
            binding.tvUsername.error = "Please enter email"
            binding.tvUsername.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.tvUsername.text.toString()).matches()) {
            binding.tvUsername.error = "Please enter valid email"
            binding.tvUsername.requestFocus()
            return
        }

        if (binding.tvPassword.text.toString().isEmpty()) {
            binding.tvPassword.error = "Please enter password"
            binding.tvPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(binding.tvUsername.text.toString(), binding.tvPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        updateUI(null)
                    }
                }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, Home::class.java))
                finish()
            }else{
                Toast.makeText(
                        baseContext, "Please verify your email address.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                    baseContext, "Login failed.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }


}