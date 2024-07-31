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
import com.example.departmentitinformation.Adapters.CourseRecyclerViewAdapter
import com.example.departmentitinformation.Models.ModelCourse

class CourseInformationActivity : AppCompatActivity() {

    companion object {
        const val HOD_EMAIL_ADDRESS = "ithod@ucc.edu.jm "
    }

    private lateinit var courseAdapter: CourseRecyclerViewAdapter
    private lateinit var database: DatabaseReference
    private val courseList = mutableListOf<ModelCourse>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: MaterialToolbar = findViewById(R.id.toolBar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        courseAdapter = CourseRecyclerViewAdapter(courseList)
        recyclerView.adapter = courseAdapter

        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { fetchCourses() }

        database = FirebaseDatabase.getInstance().getReference("Courses")
        fetchCourses()

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

    private fun fetchCourses() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                for (courseSnapshot in snapshot.children) {
                    val course = courseSnapshot.getValue(ModelCourse::class.java)
                    course?.let { courseList.add(it) }
                }
                courseAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

    }

    private fun saveCoursesToFirebase() {
        val courses = listOf(
            ModelCourse(
                courseCode = "ITT101",
                courseName = "Computer Information Systems",
                courseCredits = "3",
                coursePreRequisites = "None",
                courseDescription = "This introductory course aimed to provide a foundation level understanding of Information Technology.\n" +
                        "The main concepts covered the identification and explanation of basic computer components, set up a basic workstation, " +
                        "conduct of basic software installation, establishment of basic network connectivity, " +
                        "the identification of compatibility issues and the prevention of basic security risks.\n" +
                        "In the practical section of the course students will get hands-on experience using office productivity tools and setup a basic workstation."
            ),
            ModelCourse(
                courseCode = "ITT103",
                courseName = "Programming Techniques",
                courseCredits = "3",
                coursePreRequisites = "Computer Information Systems",
                courseDescription = "This course will introduce students to programming concepts. Students will learn proper programming design techniques and " +
                        "principles. The focus is on developing the logic and thought-processes required for problem solving, rather than on learning a programming language.\n" +
                        "This course assumes no prior knowledge of programming; however successful students will be those with an aptitude for problem-solving.\n" +
                        "Programming Techniques serves as the foundation course for all other programming courses in the programme."
            ),
            ModelCourse(
                courseCode = "ITT200",
                courseName = "Object Oriented Programming using C++",
                courseCredits = "3",
                coursePreRequisites = "Programming Techniques",
                courseDescription = "This course aims to broaden the student's knowledge of concepts and features of an object-oriented programming language.\n" +
                        "Students will be required to use these concepts to design solutions for real world problems."
            ),
            ModelCourse(
                courseCode = "ITT300",
                courseName = "Discrete Mathematics II",
                courseCredits = "3",
                coursePreRequisites = "Discrete Mathematics I",
                courseDescription = "This course builds on the fundamentals of discrete mathematics covered in Discrete Structures I.\n" +
                        "The main focus will be on developing a sound theoretical foundation for further work in computer science and information science.\n" +
                        "The topics covered in this course will not be exhaustive to discrete structures but will provide the basis for " +
                        "pursuing other advanced courses in discrete structures and mathematics."
            ),
            ModelCourse(
                courseCode = "ITT304",
                courseName = "Database Management Systems II",
                courseCredits = "3",
                coursePreRequisites = "Database Management Systems I",
                courseDescription = "This course is aimed at providing upper level undergraduate students with intermediate to " +
                        "advanced concepts in data modelling design and database administration.\n" +
                        "The course explores the variety of options available in database development and administration for " +
                        "current and future use on particular software platform technologies."
            ),
            ModelCourse(
                courseCode = "ITT308",
                courseName = "Internet Authoring II",
                courseCredits = "3",
                coursePreRequisites = "Internet Authoring I",
                courseDescription = "This course continues from Internet Authoring I, covering some of the same topics in more depth.\n" +
                        "This course includes coverage of topics in networking technologies for the web, web " +
                        "UI design and site design, client-server architecture and client-side and server-side programming.\n" +
                        "It covers relevant topics in ecommerce, web security, and engineering concepts such as the three-tier architecture and frameworks for the web.\n" +
                        "It provides an introduction to mobile web issues and web multimedia."
            ),
            ModelCourse(
                courseCode = "ITT309",
                courseName = "Computer Security",
                courseCredits = "3",
                coursePreRequisites = "Data Communication & Network II",
                courseDescription = "This course is designed to provide an understanding of computer security and " +
                        "prepare students for the CompTIA Security+ exam.\n" +
                        "The course covers material related to general computer security concepts, communications security, infrastructure " +
                        "security, basics of cryptography and operational/organizational security.\n" +
                        "Students gain knowledge in basic cryptography, fundamentals of computer and network " +
                        "security, risks faced by computers and networks, security mechanisms, operating " +
                        "system security and secure systems design principles."
            ),
            ModelCourse(
                courseCode = "ITT323",
                courseName = "IT Capstone Project II",
                courseCredits = "3",
                coursePreRequisites = "IT Capstone Project I",
                courseDescription = "A supervised group assignment in the development of information technology infrastructure for an organization.\n" +
                        "Students select an organization whose IT needs are not well-addressed, and design a completely " +
                        "integrated system including IT administration structure, hardware, software, and technology needs."
            ),
            ModelCourse(
                courseCode = "ITT401",
                courseName = "Intelligent Systems",
                courseCredits = "3",
                coursePreRequisites = "Discrete Mathematics II",
                courseDescription = "The course focuses on the basic concepts and methods in artificial \"societies\" and complex systems.\n" +
                        "It will concentrate on artificial intelligence, cognitive science, and the social context of intelligent systems.\n" +
                        "It will provide an understanding of the application of intelligent systems in the industry and organization.\n" +
                        "In particular, it will focus on how these systems may be used to assist in the " +
                        "decision-making process within the organization."
            ),
            ModelCourse(
                courseCode = "ITT405",
                courseName = "Human Computer Interaction & Interface Design",
                courseCredits = "3",
                coursePreRequisites = "Introduction to Psychology, Systems Analysis & Designs I",
                courseDescription = "This course provides an introduction to the field of human-computer interaction (HCI), an " +
                        "interdisciplinary field that integrates cognitive psychology, design, computer science and others.\n" +
                        "Examining the human factors associated with information systems provides the students with knowledge to understand " +
                        "what influences usability and acceptance of Information Systems (IS). This course will examine human performance, components of " +
                        "technology, methods and techniques used in design and evaluation of IS.\n" +
                        "Societal impacts of HCI such as accessibility will also be discussed.\n" +
                        "User-centered design methods will be introduced and evaluated.\n" +
                        "This course will also introduce students to the contemporary technologies used in empirical evaluation methods."
            )
        )

        courses.forEach { course ->
            val key = database.push().key ?: return
            course.courseKey = key
            database.child(key).setValue(course)
        }
    }

}