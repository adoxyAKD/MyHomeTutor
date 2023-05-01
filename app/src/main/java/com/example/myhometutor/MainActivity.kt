package com.example.myhometutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myhometutor.STUDENTS.DashBoardStudent
import com.example.myhometutor.TUTOR.DashBoardTutor
import com.example.myhometutor.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth


        binding.idTutorDashBoardBtn.setOnClickListener(){
            val intent = Intent(this, DashBoardTutor::class.java)
            startActivity(intent)
        }


        binding.idStudentDashBoardBtn.setOnClickListener(){
            val intent = Intent(this, DashBoardStudent::class.java)
            startActivity(intent)
        }




    }


    // FOR Menu option in main activity:
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_id -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.profile_id -> {
                Toast.makeText(applicationContext, "click on profile", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, proflileActivity::class.java)
//                startActivity(intent)
                return true

            }
            R.id.logout_id -> {

                firebaseAuth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "logout", Toast.LENGTH_LONG).show()
                finish()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}