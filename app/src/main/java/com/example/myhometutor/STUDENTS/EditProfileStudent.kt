package com.example.myhometutor.STUDENTS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhometutor.R
import android.content.Context
import android.widget.Toast

class EditProfileStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_student)

        Toast.makeText(this, "test toast", Toast.LENGTH_SHORT).show();
    }
}
