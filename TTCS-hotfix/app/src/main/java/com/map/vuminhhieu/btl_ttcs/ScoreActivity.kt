package com.map.vuminhhieu.btl_ttcs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var semesters: List<Semester> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Initialize views
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView)
        semesterSpinner = findViewById(R.id.semesterSpinner)

        // Setup RecyclerView
        coursesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        courseAdapter = CourseResultAdapter(emptyList())
        coursesRecyclerView.adapter = courseAdapter

        // Get token from intent
        val token = intent.getStringExtra("token") ?: ""

        // Initialize Retrofit and fetch data
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
}

