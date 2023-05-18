package com.example.ch8_menu

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 화면 노출됨
        menuInflater.inflate(R.menu.menu_main, menu)

        // menuItem 객체 획득하고 그 객체에 포함된 actionview를 획득한다.
        val menuItem = menu?.findItem(R.id.menu3)
        val searchView = menuItem?.actionView as SearchView// 명시적 캐스팅 연산자

        // ui 적인 것을 설정 가능
        // 검색 기능
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                // 키 입력 순간마다
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // searchview 를 위해 키보드가 올라오면 우측 하단 키가 검색버튼
                // 키보드의 검색 퍼튼을 누른 순간

                // 익명함수 내에서는 mainactivity를 지정하기 위해 사용, 라벨(2중 루프문에서 사용하기도 함)
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.setQuery("",false)// 입력한 검색어 지우기
                searchView.isIconified = true// 아이콘으로 복귀함
                return true
            }/**/

        })// 추상함수가 하나가 아니기 때문에 중괄호 사용 불가, 이 인터페이스를 구현한 클래스 사용.

        return super.onCreateOptionsMenu(menu)
    }

    // 메뉴 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1 -> Log.d("kkang", "menu1 clickd")
            R.id.menu2 -> Log.d("kkang", "menu2 clickd")
        }


        return super.onOptionsItemSelected(item)
    }
}