package com.example.myhometutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calmify.UTILS.LoadingDialog
import com.example.myhometutor.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.goToSignupPageBtnId.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
//            finish()
        }
        binding.signInBtnId.setOnClickListener {
            SignIn()
        }
    }




    //for already logined users...it will directly enter in app if logined once
    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    //for custom email:
    private fun SignIn() {
        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        val email = binding.emailId.text.toString()
        val pass = binding.passId.text.toString()

        if (email.isEmpty()) {
            binding.emailId.error = "Email required"
        }
        if (pass.isEmpty()) {
            binding.passId.error = "Password required"
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailId.error = "Email formate is incorrect \ni.e Abc.xyz_123@xyz.xyz"
//            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show()
        }

        if (pass.length < 8) {
            binding.passId.error = "Min. password length should be 8"
        }


        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {

                    Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG)
                        .show()
                    loading.isdismiss_progressbar()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //for clearing all stacks of activity opened
                    startActivity(intent)
                    finish()

                } else {
//                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    loading.isdismiss_progressbar()
                    Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

        } else {
            loading.isdismiss_progressbar()
            Toast.makeText(this, "Empty fields are not allowed.", Toast.LENGTH_LONG).show()

        }

    }

}