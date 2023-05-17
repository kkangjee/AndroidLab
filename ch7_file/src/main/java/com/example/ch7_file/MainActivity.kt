package com.example.ch7_file

import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ch7_file.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            // 내장 메모리
            val file = File(filesDir, "test.txt")// 내장 메모리 root 의 test.txt 파일을 사용하겠다
            val writeStream = file.writer()// outputstream
            writeStream.write("hello world - internal")// 문자열 작성
            writeStream.flush()

            val readStream = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = it// 읽은 내용을 T.V에 작성
            }
        }

        binding.button2.setOnClickListener {
            // 외장 앱별 저장소, 내가 가진 데이터이지만 외부 앱에 공유 하는 방법
            val file = File(getExternalFilesDir(null), "test.txt")// 외장 앱별 저장소 root 의 test.txt 파일을 사용하겠다
            val writeStream = file.writer()// outputstream
            writeStream.write("hello world - external")// 문자열 작성
            writeStream.flush()

            val readStream = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = it// 읽은 내용을 T.V에 작성
            }
        }

        // 외장 공용 저장소
        // 퍼미션이 요구됨
        // 퍼미션 요구 다이얼로그를 띄우로 닫혔을 때 사후처리

        val launcher = registerForActivityResult(
            // TODO: RequestMultiplePermissions 메소드를 찾을 수 없음
            ActivityResultContracts.RequestMultiplePermissions()// 요청 처리자, 퍼미션 다이얼로그를 여러개 띄움
            //RequestPermissions() // 한개만 요청
        ){
            //callback
            if (it.all { permission -> permission.value }){
                testFile()// 파일 핸들링 하는 로직
            }else{// 요청한 퍼미션 중 거부 한 것이 있다
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        binding.button3.setOnClickListener {
            // 외장 공용 저장소 퍼미션 체크
            // API Level 33 과 이전 버전의 퍼미션이 다르다
            // 없는 퍼미션을 체크하면 denied
            // 폰 버전을 체크해서 분기 처리 함

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if (ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.READ_MEDIA_IMAGES") == PackageManager.PERMISSION_GRANTED){
                    testFile()
                }
                else{
                    launcher.launch(arrayOf("android.permission.READ_MEDIA_IMAGES"))
                }
            }else{
                if (ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED){
                    testFile()
                }
                else{
                    launcher.launch(arrayOf("android.permission.READ_EXTERNAL_STORAGE"))
                }
            }
        }
    }

    fun testFile(){
        // gallary app 에게 요청 하고자 하는 데이터
        val projection = arrayOf(
            MediaStore.Images.Media._ID,// 사진 데이터 식별갑
            MediaStore.Images.Media.DISPLAY_NAME
        )

        // 데이터 요청
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,null,null
        )
        cursor?.let {
            while (cursor.moveToNext()){
                // 식별자를 받아 그 식별자를 포함한 해당 파일을 지칭하는 Uri
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(0)
                )

                // 우리가 파일을 직업 io 프로그램으로 읽을 수도 있다.
                // gallery 앱에서는 해당 파일의 uri 만 지정해주면 그 파일을 읽으 수 있는 stream도 제공
                applicationContext.contentResolver.openInputStream(uri).use {
                    // 화면 출력
                    // Out Of Memory 고려하여 이미지 사이즈를 리사이징 한다
                    val option = BitmapFactory.Options()
                    option.inSampleSize = 10// 화면 픽셀수와 이미지의 픽셀수를 고려하는 것이 좋은 코드(동적)
                    val bitmap = BitmapFactory.decodeStream(it, null, option)

                    binding.imageView.setImageBitmap(bitmap)
                }
            }
        }
    }
}