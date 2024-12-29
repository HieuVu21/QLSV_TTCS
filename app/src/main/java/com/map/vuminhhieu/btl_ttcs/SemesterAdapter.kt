package com.map.vuminhhieu.btl_ttcs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// SemesterAdapter.kt

class SemesterAdapter(
    private val semesters: List<Semester>,
    private val onCourseClick: (CourseResult) -> Unit // Callback khi nhấn vào môn học
) : RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester, parent, false)
        return SemesterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        holder.bind(semesters[position], onCourseClick)
    }

    override fun getItemCount(): Int = semesters.size

    class SemesterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val semesterTitleTextView: TextView = itemView.findViewById(R.id.semesterTitleTextView)
        private val semesterGpaTextView: TextView = itemView.findViewById(R.id.semesterGpaTextView)
        private val courseRecyclerView: RecyclerView = itemView.findViewById(R.id.courseRecyclerView)

        fun bind(semester: Semester, onCourseClick: (CourseResult) -> Unit) {
            semesterTitleTextView.text = "Semester ${semester.semester_number} (${semester.academic_year})"
            semesterGpaTextView.text = "GPA: ${semester.gpa}"

            // Initialize the CourseResultAdapter with the courses data and pass the onCourseClick callback
            val courseAdapter = CourseResultAdapter(semester.course_results, onCourseClick)
            courseRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            courseRecyclerView.adapter = courseAdapter
        }
    }
}
