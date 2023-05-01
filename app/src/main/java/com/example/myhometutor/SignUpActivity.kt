package com.example.myhometutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calmify.UTILS.LoadingDialog
import com.example.calmify.UTILS.USER
import com.example.myhometutor.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.goToSignInPageId.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUpBtnId.setOnClickListener {
            SignUp()
        }

    }



    private fun SignUp() {

        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        val fname = binding.firstNameId.text.toString()
        val lname = binding.lastNameId.text.toString()
        val pNumber = binding.phoneNoId.text.toString()
        val email = binding.emailId.text.toString()
        val pass = binding.passId.text.toString()
        val confPass = binding.confermPassId.text.toString()

        if (fname.isEmpty()){ binding.firstNameId.error="First name required" }
        if (lname.isEmpty()){ binding.lastNameId.error="Last name required" }
        if (pNumber.isEmpty() ){ binding.phoneNoId.error="Phone no. required" }
        if (email.isEmpty()){ binding.emailId.error="Email required" }
        if (pass.isEmpty()){ binding.passId.error="Password required" }
        if (confPass.isEmpty()){ binding.confermPassId.error="Confirm password required" }


        if(pNumber.length<10 || pNumber.length>10){ binding.phoneNoId.error="Phone no. should be of 10 digits" }

        //val emailPattern = "[a-zA-Z0-9._-]+@[gmail]+\\.+[com]+"
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailId.error="Correct Email formate is required  \ni.e Abc.xyz_123@xyz.xyz"
        }

        if(pass.length<8 ){ binding.passId.error="Min. password length is 8" }
        if(confPass!=pass ){ binding.confermPassId.error="password do not match" }

        if (fname.isNotEmpty() && lname.isNotEmpty() && pNumber.isNotEmpty()
            && email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty()) {
            if (pass == confPass) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            //store data to firebase realtime database:
                            databaseRef= FirebaseDatabase.getInstance().getReference("Users")
                            val User = USER(fname,lname,pNumber,email)          //must be in same sequence as in USER.class

                            val currentUser=firebaseAuth.uid.toString()
                            databaseRef.child(currentUser).setValue(User).addOnSuccessListener {
                                Toast.makeText(this, "Signup successfully", Toast.LENGTH_LONG).show()
                                loading.isdismiss_progressbar()

                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                loading.isdismiss_progressbar()
                                Toast.makeText(this, "failed to store data", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            loading.isdismiss_progressbar()
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                loading.isdismiss_progressbar()
//                Toast.makeText(this, "Password is not matching.", Toast.LENGTH_LONG).show()
            }
        }else{
            loading.isdismiss_progressbar()
            Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_LONG)
                .show()
        }

    }
}