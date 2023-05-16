package com.example.ch3_view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.ch3_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding
        val binding = ActivityMainBinding.inflate(layoutInflater)// 클래스 명 자체가 xml 레이아웃 파일
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val data = binding.edit.text.toString()
            Log.d("kkang", data)
        }
    }

    // 터치 이벤트
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("kkang", "touch down event")
            }

            MotionEvent.ACTION_UP -> {
                Log.d("kkang", "touch up event")
            }
//            MotionEvent.ACTION_MOVE ->{
//                Log.d("kkang", "touch up event")
//            }
        }
        return super.onTouchEvent(event)
    }

    // 키보드 이벤트
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> Log.d("kkang", "back key")
        }
        return super.onKeyDown(keyCode, event)
//        when(keyCode){
//            KeyEvent.KEYCODE_0 -> Log.d("kkang", "0")
//        }
//        return super.onKeyDown(keyCode, event)
    }
}
// 아래 코드는 뷰바인딩 사용하지 않은 코드
//class MainActivity : AppCompatActivity() {
//
//    // view 객체 멤버변수로 선언
//    // 절대 null일 수는 없는데 값을 못 줄 때, 나중에 초기화 할 것
//    lateinit var button: Button
//    lateinit var editView: EditText
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // 화면출력, 뷰 객체 생성 완료
//        setContentView(R.layout.activity_main)
//
//        // 필요한 뷰 객체 획득
//        button = findViewById(R.id.button)
//        editView = findViewById(R.id.edit)
//
//        // 이벤트 등록
//        button.setOnClickListener {
//            // event callback..
//            val data = editView.text.toString()
//            Log.d("kkang", data)
//        }
//    }
//}