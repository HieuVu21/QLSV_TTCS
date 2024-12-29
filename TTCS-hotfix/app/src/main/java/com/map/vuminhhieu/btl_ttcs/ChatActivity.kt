package com.map.vuminhhieu.btl_ttcs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : ComponentActivity() {
    private lateinit var chatInput: EditText
    private lateinit var chatSendButton: Button
    private lateinit var chatMessagesContainer: LinearLayout
    private lateinit var chatScrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatInput = findViewById(R.id.chat_input)
        chatSendButton = findViewById(R.id.chat_send_button)
        chatMessagesContainer = findViewById(R.id.chat_messages_container)
        chatScrollView = findViewById(R.id.chat_scroll_view)

        chatSendButton.setOnClickListener {
            val question = chatInput.text.toString()
            if (question.isNotEmpty()) {
                sendMessage(question)
            } else {
                Toast.makeText(this, "Vui lòng nhập câu hỏi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMessage(question: String) {
        // Hiển thị tin nhắn người dùng
        addMessage("Bạn: $question", true)

        // Gửi yêu cầu tới API chatbot
        val apiService = ApiClient.instance.create(AuthApi::class.java)
        val requestBody = mapOf("question" to question)

        apiService.askQuestion(requestBody).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    chatResponse?.let {
                        addMessage("Chatbot: ${it.answer}", false)
                    }
                } else {
                    addMessage("Chatbot không thể trả lời. Thử lại sau.", false)
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                addMessage("Lỗi kết nối: ${t.message}", false)
            }
        })
    }

    private fun addMessage(message: String, isUserMessage: Boolean) {
        val textView = TextView(this)
        textView.text = message
        textView.setPadding(8, 8, 8, 8)
        textView.textSize = 16f
        if (isUserMessage) {
            textView.setBackgroundResource(R.drawable.bubble_user)
        } else {
            textView.setBackgroundResource(R.drawable.bubble_bot)
        }
        chatMessagesContainer.addView(textView)
        chatScrollView.post { chatScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
