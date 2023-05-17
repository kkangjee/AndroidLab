package com.example.ch7_settings

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // 아래의 코드 한줄 로 설정 출력, 설정 자동화
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 추가 작업, 설정 객체 획득
        val idPreference: EditTextPreference? = findPreference("id")
        val colorPreference: ListPreference? = findPreference("color")

        // 설정 값이 그대로 summary에 출력되면 되는 경우
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()// summary 제공자
        // 설정 값을 받아서 원하는 문자열을 동적으로 출력
        idPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
            // 값 획득
            val text = it.text
            if (TextUtils.isEmpty(text)){
                "ID 설정이 되지 않았습니다."
            }else{
                "설정한 ID는 $text 입니다."
            }
            // 맨 마지막 실행하는 값이 람다함수 내의 리턴값이다.
        }

        // 값 변경 이벤트
        idPreference?.setOnPreferenceChangeListener { preference, newValue->
            Log.d("kkang", "key: ${preference.key}, newValue : $newValue")
            true
        }
        colorPreference?.setOnPreferenceChangeListener { preference, newValue->
            Log.d("kkang", "key: ${preference.key}, newValue : $newValue")
            true
        }


    }
}