package com.example.departmentitinformation.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.departmentitinformation.R
import com.example.departmentitinformation.Models.ModelCourse

class CourseRecyclerViewAdapter(private val courses : List<ModelCourse>) : RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtCode = view.findViewById<TextView>(R.id.txtCode)
        private val txtCredits = view.findViewById<TextView>(R.id.txtCredits)
        private val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        private val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
        private val txtPreRequisites = view.findViewById<TextView>(R.id.txtPreRequisites)
        private val btnExpand = view.findViewById<AppCompatImageButton>(R.id.btnExpand)

        private var isExpanded = false

        fun bind(course: ModelCourse) {
            txtCode.text = course.courseCode
            txtCredits.text = course.courseCredits
            txtTitle.text = course.courseName
            txtDescription.text = course.courseDescription
            txtPreRequisites.text = course.coursePreRequisites

            // Set initial state
            txtDescription.maxLines = if (isExpanded) Int.MAX_VALUE else 3
            btnExpand.setImageResource(if (isExpanded) R.drawable.baseline_keyboard_arrow_up_24 else R.drawable.baseline_keyboard_arrow_down_24)

            btnExpand.setOnClickListener {
                isExpanded = !isExpanded
                txtDescription.maxLines = if (isExpanded) Int.MAX_VALUE else 3
                btnExpand.setImageResource(if (isExpanded) R.drawable.baseline_keyboard_arrow_up_24 else R.drawable.baseline_keyboard_arrow_down_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}