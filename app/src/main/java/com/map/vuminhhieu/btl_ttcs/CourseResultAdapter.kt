package com.map.vuminhhieu.btl_ttcs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseResultAdapter(
    private var courses: List<CourseResult>,
    private val onCourseClick: (CourseResult) -> Unit // Callback khi nhấn vào môn học
) : RecyclerView.Adapter<CourseResultAdapter.CourseViewHolder>() {

    // ViewHolder class để quản lý các item trong RecyclerView
    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val courseNameTextView: TextView = view.findViewById(R.id.courseNameTextView)
        private val creditCountTextView: TextView = view.findViewById(R.id.creditCountTextView)
        private val courseItemLayout: LinearLayout = view.findViewById(R.id.courseItemLayout)

        // Hàm bind dữ liệu vào ViewHolder
        fun bind(course: CourseResult, onClick: (CourseResult) -> Unit) {
            courseNameTextView.text = course.name
            creditCountTextView.text = "${course.number_of_credits} tín chỉ"

            // Thêm sự kiện nhấn vào toàn bộ layout của item
            courseItemLayout.setOnClickListener {
                onClick(course) // Gọi callback khi item được nhấn
            }
        }
    }

    // Hàm tạo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false) // Inflate layout cho từng item
        return CourseViewHolder(view)
    }

    // Hàm gán dữ liệu vào từng item
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position], onCourseClick)
    }

    // Trả về số lượng item
    override fun getItemCount() = courses.size

    // Hàm cập nhật danh sách courses và refresh RecyclerView
    fun updateCourses(newCourses: List<CourseResult>) {
        courses = newCourses
        notifyDataSetChanged() // Refresh dữ liệu
    }
}
