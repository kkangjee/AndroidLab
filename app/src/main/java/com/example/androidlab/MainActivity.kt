package com.example.androidlab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 화면출력
        setContentView(R.layout.activity_main)

        // xml 대신 코드로 작성하기(비효율적)
//        val button = Button(this)
//        button.text = "헬로~"
//        setContentView(button)
    }
}