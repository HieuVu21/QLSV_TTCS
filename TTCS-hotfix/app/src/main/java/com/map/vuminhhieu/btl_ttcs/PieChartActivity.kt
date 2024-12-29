//package com.map.vuminhhieu.btl_ttcs
//
//import android.annotation.SuppressLint
//import android.graphics.Color
//import android.os.Bundle
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import com.github.mikephil.charting.animation.Easing
//import com.github.mikephil.charting.charts.PieChart
//import com.github.mikephil.charting.data.PieData
//import com.github.mikephil.charting.data.PieDataSet
//import com.github.mikephil.charting.data.PieEntry
//import com.github.mikephil.charting.utils.ColorTemplate
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class PieChartActivity : ComponentActivity() {
//    private lateinit var totalCreditsTextView: TextView
//    private lateinit var pieChart: PieChart
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pie_chart)
//        totalCreditsTextView = findViewById(R.id.totalCreditsTextView)
//        // Initialize PieChart
//        pieChart = findViewById(R.id.pieChart)
//
//        // Get token from Intent (assuming it's passed from another Activity)
//        val token = intent.getStringExtra("token") ?: ""
//
//        // Initialize Retrofit and fetch data
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080/")  // Replace with your API base URL
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val authApi = retrofit.create(AuthApi::class.java)
//        fetchGpaDetails(authApi, token)
//    }
//
//    private fun fetchGpaDetails(authApi: AuthApi, token: String) {
//        authApi.getGpaDetail("Bearer $token").enqueue(object : Callback<GpaResponse> {
//            override fun onResponse(call: Call<GpaResponse>, response: Response<GpaResponse>) {
//                if (response.isSuccessful) {
//                    response.body()?.let { gpaResponse ->
//                        // Use GPA data to update PieChart
//                        setupPieChart(gpaResponse)
//
//                        val creditsEarned = gpaResponse.semesters.sumBy { it.total_number_of_credits }
//
//                        // Cập nhật TextView với tổng số tín chỉ đã học
//                        totalCreditsTextView.text = "Tín chỉ đã học: $creditsEarned"
//                    }
//                } else {
//                    Toast.makeText(
//                        this@PieChartActivity,
//                        "Failed to retrieve data",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<GpaResponse>, t: Throwable) {
//                Toast.makeText(this@PieChartActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }
////
////    private fun setupPieChart(gpaResponse: GpaResponse) {
////        val entries = mutableListOf<PieEntry>()
////
////        // Add data to PieChart (current and predicted GPA)
////        entries.add(PieEntry(gpaResponse.current_gpa.toFloat(), "Current GPA"))
////        entries.add(PieEntry(gpaResponse.predicted_next_semester_gpa.toFloat(), "Predicted GPA"))
////
////        // Customize PieChart
////        val dataSet = PieDataSet(entries, "GPA Overview")
////        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()  // Set colors
////        val pieData = PieData(dataSet)
////
////        pieChart.data = pieData
////        pieChart.invalidate()  // Refresh the chart
////
////        // Customize PieChart appearance
////        pieChart.setUsePercentValues(true)
////        pieChart.description.isEnabled = false
////        pieChart.isRotationEnabled = true
////        pieChart.isHighlightPerTapEnabled = true
////    }
//
////    private fun setupPieChart(gpaResponse: GpaResponse) {
////        val entries = mutableListOf<PieEntry>()
////
////        // Tổng số tín chỉ
////        val totalCredits = 150
////
////        // Tính số tín chỉ đã học
////        val creditsEarned = gpaResponse.semesters.sumBy { it.total_number_of_credits }
////
////        // Tính phần trăm tín chỉ đã học
////        val percentageEarned = (creditsEarned.toFloat() / totalCredits) * 100
////
////        // Thêm dữ liệu vào PieChart (Tín chỉ đã học và Tín chỉ còn lại)
////        entries.add(PieEntry(percentageEarned, "Tín chỉ đã học"))
////        entries.add(PieEntry(100 - percentageEarned, "Tín chỉ chưa học"))
////
////        // Tùy chỉnh PieChart
////        val dataSet = PieDataSet(entries, "Tín chỉ đã học")
////        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()  // Chọn màu sắc
////        val pieData = PieData(dataSet)
////
////        pieChart.data = pieData
////        pieChart.invalidate()  // Cập nhật lại PieChart
////
////        // Tùy chỉnh PieChart
////        pieChart.setUsePercentValues(true)
////        pieChart.description.isEnabled = false;
////        pieChart.isRotationEnabled = true
////        pieChart.isHighlightPerTapEnabled = true
////
////    }
//private fun setupPieChart(gpaResponse: GpaResponse) {
//    val entries = mutableListOf<PieEntry>()
//
//    // Tổng số tín chỉ
//    val totalCredits = 150
//
//    // Tính số tín chỉ đã học
//    val creditsEarned = gpaResponse.semesters.sumBy { it.total_number_of_credits }
//
//    // Tính phần trăm tín chỉ đã học
//    val percentageEarned = (creditsEarned.toFloat() / totalCredits) * 100
//
//    // Thêm dữ liệu vào PieChart (Tín chỉ đã học và Tín chỉ chưa học)
//    entries.add(PieEntry(percentageEarned, "Tín chỉ đã học"))
//    entries.add(PieEntry(100 - percentageEarned, "Tín chỉ chưa học"))
//
//    // Tùy chỉnh PieChart
//    val dataSet = PieDataSet(entries, "Tín chỉ đã học")
//    dataSet.colors = listOf(Color.GREEN, Color.RED)  // Chọn màu sắc đẹp hơn cho các phần trong PieChart
//    dataSet.valueTextSize = 16f  // Kích thước chữ cho giá trị phần trăm
//    dataSet.setDrawValues(true)  // Hiển thị giá trị phần trăm trên biểu đồ
//    dataSet.sliceSpace = 3f  // Khoảng cách giữa các phần của biểu đồ
//    dataSet.setValueTextColor(Color.WHITE)  // Màu chữ cho các giá trị phần trăm
//
//    // Tạo PieData từ PieDataSet
//    val pieData = PieData(dataSet)
//
//    // Cập nhật dữ liệu vào PieChart
//    pieChart.data = pieData
//    pieChart.invalidate()  // Cập nhật lại PieChart
//
//    // Tùy chỉnh PieChart
//    pieChart.setUsePercentValues(true)  // Sử dụng phần trăm để hiển thị
//    pieChart.description.isEnabled = false  // Tắt mô tả mặc định
//    pieChart.isRotationEnabled = true  // Bật tính năng xoay biểu đồ
//    pieChart.isHighlightPerTapEnabled = true  // Bật tính năng highlight khi chạm vào biểu đồ
//
//    // Tùy chỉnh thêm các yếu tố khác để đẹp hơn
//    pieChart.setExtraOffsets(20f, 20f, 20f, 20f)  // Thêm khoảng cách giữa các phần của biểu đồ và viền
//    pieChart.animateY(1400, Easing.EaseInOutQuad)  // Hiệu ứng hoạt hình khi tải biểu đồ
//
//    // Tùy chỉnh chi tiết hiển thị của các phần tử
//    pieChart.legend.isEnabled = false // Tắt legend nếu không muốn hiển thị
//    pieChart.setEntryLabelTextSize(14f)  // Kích thước chữ của các nhãn phần tử
//    pieChart.setEntryLabelColor(Color.BLACK)  // Màu chữ cho các nhãn phần tử
//
//    // Cấu hình thêm cho trung tâm của biểu đồ
////    val centerText = "Tín chỉ đã học\n$creditsEarned / $totalCredits"
////    pieChart.centerText = centerText  // Hiển thị text ở giữa biểu đồ
////    pieChart.setCenterTextSize(18f)   // Kích thước chữ cho text ở giữa biểu đồ
////    pieChart.setCenterTextColor(Color.BLACK)   // Màu chữ cho text ở giữa
//}
//
//}
//

package com.map.vuminhhieu.btl_ttcs

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PieChartActivity : ComponentActivity() {
    private lateinit var totalCreditsTextView: TextView
    private lateinit var pieChart: PieChart
    private lateinit var subjectPieChart: PieChart
    private lateinit var semesterSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        // Khởi tạo các View
        totalCreditsTextView = findViewById(R.id.totalCreditsTextView)
        pieChart = findViewById(R.id.pieChart)
        subjectPieChart = findViewById(R.id.subjectPieChart)
        semesterSpinner = findViewById(R.id.semesterSpinner)

        // Lấy token từ Intent
        val token = intent.getStringExtra("token") ?: ""

        // Khởi tạo Retrofit và lấy dữ liệu
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // Thay bằng URL của bạn
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
                        // Lưu trữ GpaResponse để sử dụng sau
                        // Cập nhật Spinner với danh sách các kỳ học
                        val semesterNames = gpaResponse.semesters.map {
                            "Học kỳ ${it.semester_number} Năm học ${it.academic_year}"
                        }
                        val semesterAdapter = ArrayAdapter(
                            this@PieChartActivity,
                            android.R.layout.simple_spinner_item,
                            semesterNames
                        )
                        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        semesterSpinner.adapter = semesterAdapter

                        // Lắng nghe sự kiện chọn kỳ học từ Spinner
                        semesterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parentView: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val selectedSemester = gpaResponse.semesters[position]
                                setupSubjectGpaChart(selectedSemester)  // Cập nhật biểu đồ GPA của từng môn học
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>) {
                                // Xử lý khi không có kỳ học nào được chọn
                            }
                        }

                        // Cập nhật biểu đồ tổng GPA
                        setupPieChart(gpaResponse)

                        // Cập nhật tổng số tín chỉ đã học
                        val creditsEarned = gpaResponse.semesters.sumBy { it.total_number_of_credits }
                        totalCreditsTextView.text = "Tín chỉ đã học: $creditsEarned"
                    }
                } else {
                    Toast.makeText(this@PieChartActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GpaResponse>, t: Throwable) {
                Toast.makeText(this@PieChartActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupPieChart(gpaResponse: GpaResponse) {
        val entries = mutableListOf<PieEntry>()
        val totalCredits = 150  // Giả sử tổng tín chỉ là 150
        val creditsEarned = gpaResponse.semesters.sumBy { it.total_number_of_credits }
        val percentageEarned = (creditsEarned.toFloat() / totalCredits) * 100
        entries.add(PieEntry(percentageEarned, "Tín chỉ đã học"))
        entries.add(PieEntry(100 - percentageEarned, "Tín chỉ chưa học"))

        val dataSet = PieDataSet(entries, "Tín chỉ đã học")
        dataSet.colors = listOf(Color.GREEN, Color.RED)
        dataSet.valueTextSize = 16f
        dataSet.setDrawValues(true)
        dataSet.sliceSpace = 3f
        dataSet.setValueTextColor(Color.WHITE)

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun setupSubjectGpaChart(semester: Semester) {
        val entries = mutableListOf<PieEntry>()

        // Tổng GPA của kỳ học
        val totalGpa = semester.gpa.toFloat()

        // Duyệt qua các môn học trong kỳ và tính tỷ lệ GPA cho từng môn
        semester.course_results.forEach { course ->
            // Tính tỷ lệ phần trăm GPA của môn học
            val percentage = (course.gpa_4_scale.toFloat() / totalGpa) * 100
            entries.add(PieEntry(percentage, course.name))  // Thêm dữ liệu vào PieChart
        }

        // Tùy chỉnh PieChart
        val dataSet = PieDataSet(entries, "Môn học trong kỳ ${semester.semester_number}")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()  // Chọn màu sắc đẹp hơn cho các phần trong PieChart
        dataSet.valueTextSize = 16f  // Kích thước chữ cho giá trị phần trăm
        dataSet.setDrawValues(true)  // Hiển thị giá trị phần trăm trên biểu đồ
        dataSet.sliceSpace = 3f  // Khoảng cách giữa các phần của biểu đồ
        dataSet.setValueTextColor(Color.WHITE)  // Màu chữ cho các giá trị phần trăm

        // Tạo PieData từ PieDataSet
        val pieData = PieData(dataSet)

        // Cập nhật dữ liệu vào PieChart
        pieChart.data = pieData
        pieChart.invalidate()  // Cập nhật lại PieChart

        // Tùy chỉnh PieChart
        pieChart.setUsePercentValues(true)  // Sử dụng phần trăm để hiển thị
        pieChart.description.isEnabled = false  // Tắt mô tả mặc định
        pieChart.isRotationEnabled = true  // Bật tính năng xoay biểu đồ
        pieChart.isHighlightPerTapEnabled = true  // Bật tính năng highlight khi chạm vào biểu đồ

        // Tùy chỉnh thêm các yếu tố khác để đẹp hơn
        pieChart.setExtraOffsets(20f, 20f, 20f, 20f)  // Thêm khoảng cách giữa các phần của biểu đồ và viền
        pieChart.animateY(1400, Easing.EaseInOutQuad)  // Hiệu ứng hoạt hình khi tải biểu đồ

        // Tùy chỉnh chi tiết hiển thị của các phần tử
        pieChart.legend.isEnabled = false // Tắt legend nếu không muốn hiển thị
        pieChart.setEntryLabelTextSize(14f)  // Kích thước chữ của các nhãn phần tử
        pieChart.setEntryLabelColor(Color.BLACK)  // Màu chữ cho các nhãn phần tử
    }

}
