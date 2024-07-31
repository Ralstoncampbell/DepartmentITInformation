package com.example.departmentitinformation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SocialMediaActivity : AppCompatActivity() {

    companion object {
        const val HOD_EMAIL_ADDRESS = "ithod@ucc.edu.jm "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_social_media)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: MaterialToolbar = findViewById(R.id.toolBar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Set up button click listeners
        findViewById<AppCompatImageButton>(R.id.btnFacebook).setOnClickListener {
            openUrl("https://www.facebook.com/uccjamaica")
        }
        findViewById<AppCompatImageButton>(R.id.btnTwitter).setOnClickListener {
            openUrl("https://twitter.com/UCCjamaica")
        }
        findViewById<AppCompatImageButton>(R.id.btnInstagram).setOnClickListener {
            openUrl("https://www.instagram.com/uccjamaica")
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:$HOD_EMAIL_ADDRESS")
            try {
                startActivity(Intent.createChooser(intent, "Choose Email Client..."))
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    e.localizedMessage ?: "Unexpected error occurs!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}