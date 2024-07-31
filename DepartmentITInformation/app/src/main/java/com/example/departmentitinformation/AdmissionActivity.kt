package com.example.departmentitinformation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class AdmissionActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admission)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: MaterialToolbar = findViewById(R.id.toolBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Replace placeholders with actual text
        findViewById<TextView>(R.id.txtAdmission1).text = "1. To be unconditionally admitted to complete UCC undergraduate programmes, individuals should possess a minimum of five (5) subjects at the GCE or CSEC level (including the mandatory English Language and Mathematics) at grades A, B, C or 1, 2, 3 respectively. A CSEC pass at level 3 must have been obtained since 1998."
        findViewById<TextView>(R.id.txtAdmission2).text = "2. Candidates who have a minimum of 4 CXCs can also apply pending the outstanding CXC subjects or can opt to do UCC replacement courses Core Mathematics, English for Academic Purpose and Fundamentals of Accounting."
        findViewById<TextView>(R.id.txtAdmission3).text = "3. Candidates can opt to apply under the Mature Entry option. To be accepted, applicants must be working for 5 years or more and be at a minimum age of 25 years. Academic qualifications, a detailed resume, a job letter and 3 professional references will be required."

        val internationalContent = """
            - An application fee (non-refundable) of US$100
            - Application for Admission
            - High school diploma, GED, or Certificate of Equivalency / Proficiency (with minimum Grade Point Average of 2.5 from high school)
            - Passes in 5 GCE O Level passes at A, B or C including Mathematics and English or International Baccalaureate (IB) or other international qualification deemed equivalent (subject to UCC approval)
            - One electronic passport size photo
            - Copy of passport information page
            - Financial documentation demonstrating that the student can support him/herself during his/her stay in Jamaica (to be sent along with completed UCC Affidavit of Support Form)
            - Official Transcripts of all secondary school (and other universities) coursework and grades (translated in English). “Official” means that the applicant must request that his school send transcripts directly to the UCC Registry (registry@ucc.edu.jm OR uccadmissions@ucc.edu.jm). These transcripts must be evaluated by one of the following organizations:
            http://www.acei1.com
            http://www.ierf.org
            http://www.wes.org
        """.trimIndent()

        findViewById<TextView>(R.id.txtAdmissionIn1).text = internationalContent

        val btnApply: Button = findViewById(R.id.btnApply)
        btnApply.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://ucc.edu.jm/apply/undergraduate/preform")
            )
            startActivity(intent)
        }
    }
}