package com.map.vuminhhieu.btl_ttcs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseResultAdapter(private var courses: List<CourseResult>) :
    RecyclerView.Adapter<CourseResultAdapter.CourseViewHolder>() {

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val courseNameTextView: TextView = view.findViewById(R.id.courseNameTextView)
        private val creditCountTextView: TextView = view.findViewById(R.id.creditCountTextView)

        fun bind(course: CourseResult) {
            courseNameTextView.text = course.name
            creditCountTextView.text = "${course.number_of_credits} tín chỉ"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount() = courses.size

    fun updateCourses(newCourses: List<CourseResult>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
