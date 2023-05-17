package com.example.ch7_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 코틀린은 생성자가 주생성자, 보조생성자로 구분된다.
// 주생성자는 선언영역에 하나만
// 보조생성자는 클래스 바디에. construtor 예약어로 어려개
// 상위 클래스의 함수 오버라이드 해야 함
class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1){
    // 추상함수 만들어줌
    // install 후 최초에 한번 호출
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table TODO_DB(" +
                        "_id integer primary key autoincrement," +
                        "todo not null)")
    }

    // db 버전마다 호출
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}