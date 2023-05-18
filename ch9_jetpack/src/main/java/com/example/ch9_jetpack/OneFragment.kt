package com.example.ch9_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch9_jetpack.databinding.FragmentOneBinding
import com.example.ch9_jetpack.databinding.ItemRecyclerviewBinding


// 분리하는 것이 맞음, 추후 분리
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

// 항목 구성
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<MyViewHolder>() {

    // 항목 갯수 판단하여 자동 호출
    override fun getItemCount(): Int {
        return datas.size
    }

    // 항목 구성을 위한 viewholder 생성 리턴, 내부적 재사용
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // 뷰홀더 객체 하나 생성 후 리턴
        Log.d("kkang", "onCreateViewHolder")
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(
            parent.context
        ), parent, false))
    }

    // 각 항목을 구성하기 위해 호출
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.index.text = (position+1).toString()
        holder.binding.itemData.text = datas[position]
    }
}

class MyItemDecoration(val context: Context): RecyclerView.ItemDecoration(){
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // 뷰의 크기
        val width = parent.width
        val height = parent.height

        // 이미지 크기
        val drawable = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = drawable?.intrinsicWidth
        val drHeight = drawable?.intrinsicHeight

        // 드로잉을 하려면 좌표값이 필요하다
        // 해당 코드 적용 시 가운데에 정렬 됨
        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int

        // 이미지 그리기
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )

    }

    override fun getItemOffsets(
        outRect: Rect,              // 사각형 정보
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 항목의 index, 몇번째 컨텐츠를 사용 할 것인지
        val index = parent.getChildAdapterPosition(view) + 1

//        세번째마다 공백을 줌
        if (index % 3 == 0) outRect.set(10, 10, 10, 60)
        else outRect.set(10, 10, 10, 0)// 공백 안줌

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)// 올라온 것 처럼 나타남
    }
}

class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return FragmentOneBinding.inflate(inflater, container, false).root
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        val datas = mutableListOf<String>()
        for (i in 1 .. 20){
            datas.add("Item $i")
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        return binding.root
    }
}