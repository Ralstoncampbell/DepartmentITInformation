package com.example.departmentitinformation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.departmentitinformation.Adapters.FacultyRecyclerViewAdapter
import com.example.departmentitinformation.Models.ModelFaculty

class FacultyInformationActivity : AppCompatActivity() {

    companion object {
        const val HOD_EMAIL_ADDRESS = "ithod@ucc.edu.jm "
    }

    private lateinit var facultyAdapter: FacultyRecyclerViewAdapter
    private lateinit var database: DatabaseReference
    private val facultyList = mutableListOf<ModelFaculty>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_faculty_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: MaterialToolbar = findViewById(R.id.toolBar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        facultyAdapter = FacultyRecyclerViewAdapter(facultyList)
        recyclerView.adapter = facultyAdapter

        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { fetchFaculty() }

        database = FirebaseDatabase.getInstance().getReference("Faculty")
        fetchFaculty()

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

    private fun fetchFaculty() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                facultyList.clear()
                for (facultySnapshot in snapshot.children) {
                    val faculty = facultySnapshot.getValue(ModelFaculty::class.java)
                    faculty?.let { facultyList.add(it) }
                }
                facultyAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

    }

    private fun saveFacultyToFirebase() {
        val facultyList = listOf(
            ModelFaculty(
                FacultyName = "Natalie Rose",
                FacultyEmail = "ithod@ucc.edu.jm",
                FacultyPhone = "+1 876 906-3000",
                FacultyPhoto = "https://jamaica-gleaner.com/sites/default/files/styles/jg_article_image/public/media/article_images/2018/11/22/NatalieRoseA20181120LR.jpg?itok=40nQ29hP"
            ),
            ModelFaculty(
                FacultyName = "Otis Osbourne",
                FacultyEmail = "itfaculty@ucc.edu.jm",
                FacultyPhone = "+1 876 906-3000",
                FacultyPhoto = "https://pbs.twimg.com/profile_images/1389979286854115329/p8KFeFpb_400x400.jpg"
            ),
            ModelFaculty(
                FacultyName = "Neil Williams",
                FacultyEmail = "itlecturer@ucc.edu.jm",
                FacultyPhone = "+1 876 906-3000",
                FacultyPhoto = "https://img1.wsimg.com/isteam/ip/fde9f60c-773a-4e67-acd2-19344071b213/Neil%20Williams.jpg/:/rs=w:365,h:365,cg:true,m/cr=w:365,h:365"
            ),
            ModelFaculty(
                FacultyName = "Dr. Sajjad Rizvi",
                FacultyEmail = "srizvi@faculty.ucc.edu.jm",
                FacultyPhone = "+1 876 906-3000",
                FacultyPhoto = "https://d2cax41o7ahm5l.cloudfront.net/mi/ocm/appliedphysics-mathematics-dr-syed-sajjad-hussain-rizvi-450678932.jpg"
            ),
            ModelFaculty(
                FacultyName = "Henry Osborne",
                FacultyEmail = "hosbourne@ucc.edu.jm",
                FacultyPhone = "+1 876 906-3000",
                FacultyPhoto = "https://pbs.twimg.com/profile_images/1277350205873061888/e372ox-5_400x400.jpg"
            )
        )

        facultyList.forEach { faculty ->
            val key = database.push().key ?: return
            faculty.FacultyKey = key
            database.child(key).setValue(faculty)
        }


    }

}
