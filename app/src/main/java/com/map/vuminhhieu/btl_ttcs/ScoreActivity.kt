package com.map.vuminhhieu.btl_ttcs

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScoreActivity : ComponentActivity() {
    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var semesterSpinner: Spinner
    private lateinit var courseAdapter: CourseResultAdapter
    private lateinit var detailLayout: View
    private lateinit var closeDetailButton: ImageButton
    private lateinit var backgroundOverlay: View
    private var semesters: List<Semester> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Nút back
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Quay lại Activity trước đó
        }

        // Initialize views
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView)
        semesterSpinner = findViewById(R.id.semesterSpinner)
        detailLayout = findViewById(R.id.detailsCardView)
        closeDetailButton = findViewById(R.id.closeDetailButton)
        backgroundOverlay = findViewById(R.id.backgroundOverlay)

        closeDetailButton.setOnClickListener {
            hideCourseDetails()
        }

        // Setup RecyclerView
        coursesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        courseAdapter = CourseResultAdapter(emptyList()) { course ->
            showCourseDetails(course) // Hiển thị chi tiết môn học khi nhấn
        }
        coursesRecyclerView.adapter = courseAdapter

        // Get token từ intent
        val token = intent.getStringExtra("token") ?: ""

        // Initialize Retrofit và fetch data
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authApi = retrofit.create(AuthApi::class.java)
        fetchGpaDetails(authApi, token)
    }

    private fun fetchGpaDetails(authApi: AuthApi, token: String) {
        authApi.getGpaDetail("Bearer $token").enqueue(object : Callback<GpaResponse> {
            override fun onResponse(call: Call<GpaResponse>, response: Response<GpaResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { gpaResponse ->
                        semesters = gpaResponse.semesters
                        setupSpinner()
                    }
                } else {
                    Toast.makeText(this@ScoreActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GpaResponse>, t: Throwable) {
                Toast.makeText(this@ScoreActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner() {
        val semesterTitles = semesters.map { "Học kỳ ${it.semester_number} Năm học ${it.academic_year}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, semesterTitles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        semesterSpinner.adapter = adapter
        semesterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                courseAdapter.updateCourses(semesters[position].course_results)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showCourseDetails(course: CourseResult) {
        // Hiển thị layout chi tiết và làm tối nền
        detailLayout.visibility = View.VISIBLE
        backgroundOverlay.visibility = View.VISIBLE

        // Liên kết các TextView với dữ liệu môn học
        val courseNameTextView = detailLayout.findViewById<TextView>(R.id.courseNameTextView)
        val creditCountTextView = detailLayout.findViewById<TextView>(R.id.creditCountTextView)
        val attendanceTextView = detailLayout.findViewById<TextView>(R.id.attendanceScoreTextView)
        val testScoreTextView = detailLayout.findViewById<TextView>(R.id.testScoreTextView)
        val examScoreTextView = detailLayout.findViewById<TextView>(R.id.examScoreTextView)
        val finalScoreH4TextView = detailLayout.findViewById<TextView>(R.id.finalScoreH4TextView)
        val finalScoreH10TextView = detailLayout.findViewById<TextView>(R.id.finalScoreH10TextView)
        val finalScoreTextView = detailLayout.findViewById<TextView>(R.id.finalScoreTextView)

        // Gán dữ liệu
        courseNameTextView.text = course.name
        creditCountTextView.text = "${course.number_of_credits} tín chỉ"
//        attendanceTextView.text = "Chuyên cần: ${course.attendance_score ?: "--"}"
//        testScoreTextView.text = "Trung bình kiểm tra: ${course.test_score ?: "--"}"
//        examScoreTextView.text = "Điểm thi: ${course.exam_score ?: "--"}"
        finalScoreH4TextView.text = "${course.gpa_4_scale ?: "--"}"
        finalScoreH10TextView.text = "${course.gpa_10_scale ?: "--"}"
//        finalScoreTextView.text = "Tổng kết (Dạng chữ): ${course.final_score_text ?: "--"}"
    }

    private fun hideCourseDetails() {
        // Ẩn layout chi tiết và lớp nền mờ
        detailLayout.visibility = View.GONE
        backgroundOverlay.visibility = View.GONE
    }
}
