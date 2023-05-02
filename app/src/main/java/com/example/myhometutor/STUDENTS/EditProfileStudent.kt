package com.example.myhometutor.STUDENTS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhometutor.R
import com.example.myhometutor.UTILS.showToast

class EditProfileStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_student)

        showToast("Test Toast")
    }
}