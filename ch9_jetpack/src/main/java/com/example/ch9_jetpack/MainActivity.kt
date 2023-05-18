package com.example.ch9_jetpack

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch9_jetpack.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    // ViewPager Adapter 항목 구성
    // 항목이 fragment 로 준비되었다면 FragmentStateAdapter 상송
    class MyPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        // 항목 갯수 판단하기 위해 호출
        override fun getItemCount(): Int {
            return fragments.size
        }

        // 각 항목을 결정하기 위해 호출
        override fun createFragment(position: Int): Fragment {
            Log.d("kkang", "$position")
            return fragments[position]
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar 내용이 toolbar에 적용되게
        setSupportActionBar(binding.toolbar)

        // viewpager
        binding.viewpager.adapter = MyPagerAdapter(this)

        // toggle
        toggle = ActionBarDrawerToggle(
            this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // TabLayout 과 Viewpage 연동.
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            // 화면 갯수 만큼 반복 호출
            tab.text = "Tab${position + 1}"
        }.attach()
    }

    // 툴바가 나온다
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // toggle 이 내부적으로는 메뉴이다. 클릭 시 이 함수 호출 됨
        // 일반 이벤트 타지 않도록
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}