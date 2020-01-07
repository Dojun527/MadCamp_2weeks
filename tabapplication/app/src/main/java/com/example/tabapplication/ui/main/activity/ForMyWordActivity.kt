package com.example.tabapplication.ui.main.activity

//나만의 단어장에서 추가버튼을 누르면 출력되는 화면에서

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.WordActivity.Companion.wordArrayList

import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.example.tabapplication.ui.main.adapter.Word

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.for_mylist.*


class ForMyWordActivity : AppCompatActivity(){


    var adapter: WordListAdapter = WordListAdapter(wordArrayList)
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.for_mylist)


        recyclerView = findViewById(R.id.my_recycler_view)
        adapter = WordListAdapter(wordArrayList)


        var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager


        val btn_Add: Button = findViewById(R.id.btn_add)
        val btn_SelectAll: Button = findViewById(R.id.btn_selectAll)



//Add버튼
        btn_Add.setOnClickListener {
            val intent = Intent(this, WordQuizActivity::class.java)
            intent.putParcelableArrayListExtra("wordArray", wordArrayList)
            startActivity(intent)
        }

//SelectAll버튼
        btn_SelectAll.setOnClickListener {
            val addintent = Intent(this, WordAddActivity::class.java)
            startActivityForResult(addintent, 0)
        }
    }

    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CODE){
            val vocabulary = data!!.getStringExtra("vocabulary")
            val meaning = data!!.getStringExtra("meaning")

            if(vocabulary == "" || meaning == ""){
                Toast.makeText(this, "Please write something", Toast.LENGTH_SHORT).show()
            }

            else {
                wordArrayList.add(Word(vocabulary, meaning))
                adapter = WordListAdapter(wordArrayList)
                recyclerView!!.adapter = adapter
            }
        }
    }






}