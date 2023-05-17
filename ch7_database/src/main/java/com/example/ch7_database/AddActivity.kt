package com.example.ch7_database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch7_database.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val inputData = binding.addEditView.text.toString()
            val db = DBHelper(this).writableDatabase
            // 배열은 Array 타입 객체로 표현
            db.execSQL("insert into TODO_DB (todo) values (?)", arrayOf(inputData))
            db.close()

            //main activity 로 화면 전환
            intent.putExtra("result", inputData)
            setResult(RESULT_OK, intent)

            // 자신을 종료시키고 이전 화면으로 넘어가게 함
            finish()
        }
    }
}