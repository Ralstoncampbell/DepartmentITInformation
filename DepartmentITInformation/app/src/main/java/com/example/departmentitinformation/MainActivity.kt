package com.example.departmentitinformation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        const val HOD_EMAIL_ADDRESS	= "ithod@ucc.edu.jm "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val facultyCard: MaterialCardView = findViewById(R.id.facultyinformation)
        val courseCard: MaterialCardView = findViewById(R.id.courseinformation)
        val admissionCard: MaterialCardView = findViewById(R.id.admission)
        val socialMediaCard: MaterialCardView = findViewById(R.id.socialMedia)

        facultyCard.setOnClickListener {
            val intent = Intent(this, FacultyInformationActivity::class.java)
            startActivity(intent)
        }

        courseCard.setOnClickListener {
            val intent = Intent(this, CourseInformationActivity::class.java)
            startActivity(intent)
        }

        admissionCard.setOnClickListener {
            val intent = Intent(this, AdmissionActivity::class.java)
            startActivity(intent)
        }

        socialMediaCard.setOnClickListener {
            val intent = Intent(this, SocialMediaActivity::class.java)
            startActivity(intent)
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:$HOD_EMAIL_ADDRESS")
            try {
                startActivity(Intent.createChooser(intent, "Choose Email Client..."))
            } catch (e: Exception) {
                Toast.makeText(this, e.localizedMessage ?: "Unexpected error occurs!", Toast.LENGTH_LONG).show()
            }
        }
    }
}