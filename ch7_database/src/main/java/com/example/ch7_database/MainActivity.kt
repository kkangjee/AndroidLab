package com.example.ch7_database

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ch7_database.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //db select 데이터 저장
    var datas: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from TODO_DB", null)
        cursor.run{
            while (moveToNext()){
                datas.add(cursor.getString(1))
            }
        }
        db.close()

        var result = ""
        // 람다함수가 매개변수 하나라면, 매개변수 선언할 필요 없이 it 으로 사용가능
        // it 은 람다함수 내에서만 예약어. 매개변수 지칭
        datas.forEach{
            result += "$it \n"
        }
        binding.mainTextView.text = result

        //AddActivity에서 돌아왔을 때
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // callback
            // null safety 연산자
            // !!(data가 null 이라면 exception 발생시켜라)
            // ?.(앞이 null 이면 뒤를 참조하지 마라)
            // ?:(null이면 실행시킨다?)
            it.data!!.getStringExtra("result")?.let{// null 이 아니라면
                datas.add(it)

                var result = ""
                datas.forEach{
                    result += "$it \n"
                }
                binding.mainTextView.text = result
            }
        }

        //AddActivity 로 화면 전환
        binding.mainButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            launcher.launch(intent)
        }
    }
}