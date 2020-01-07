package com.example.tabapplication.ui.main.activity

//단어장 눌렀을 때

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tabapplication.R

import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.example.tabapplication.ui.main.adapter.Word
import com.example.tabapplication.ui.main.fragment.WordFragment

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */

class WordActivity :AppCompatActivity() {



    companion object{
        var wordArrayList: ArrayList<Word> = ArrayList() //틀린단어들의 list
    }

    var adapter: WordListAdapter = WordListAdapter(wordArrayList)
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_word)

        wordArrayList=
            arrayListOf<Word> (
            Word("secretary", "비서"),
            Word("identification", "신분증"),
               Word("clerk", "점원"),
                Word("cliff", "절벽"),
                Word("flexable", "유연한"),
                Word("retailer", "소매상인"),
                Word("hedge", "울타리"),
                Word("apple", "사과"),
                Word("orange", "오렌지"),
                Word("grape", "포도"),
                Word("ideal", "이상적인"),
                Word("impartial", "공평한"),
                Word("accomodation", "수용"),
                Word("refrigerator", "냉장고"),
                Word("replica", "복제품")
        )

        recyclerView = findViewById(R.id.my_recycler_view)
        adapter = WordListAdapter(wordArrayList)

        var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager


        val plusFab: FloatingActionButton = findViewById(R.id.plusFab)
        val quizFab: FloatingActionButton = findViewById(R.id.quizFab)
        val addFab: FloatingActionButton = findViewById(R.id.addFab)
        val quizLayout: LinearLayout = findViewById(R.id.quizLayout)
        val addLayout: LinearLayout = findViewById(R.id.addLayout)
        val showButtonAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.show_button)
        val hideButtonAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.hide_button)
        val showLayoutAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.show_layout)
        val hideLayoutAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.hide_layout)

        plusFab.setOnClickListener {
            if (quizLayout.visibility == View.VISIBLE && addLayout.visibility == View.VISIBLE) {
                quizLayout.visibility = View.GONE
                addLayout.visibility = View.GONE
                quizFab.isClickable = false
                addFab.isClickable = false
                plusFab.startAnimation(hideButtonAnim)
                quizLayout.startAnimation(hideLayoutAnim)
                addLayout.startAnimation(hideLayoutAnim)
            } else {
                quizLayout.visibility = View.VISIBLE
                addLayout.visibility = View.VISIBLE
                quizFab.isClickable = true
                addFab.isClickable = true
                plusFab.startAnimation(showButtonAnim)
                quizLayout.startAnimation(showLayoutAnim)
                addLayout.startAnimation(showLayoutAnim)
            }
        }

//quiz버튼
        quizFab.setOnClickListener {
            val intent = Intent(this, WordQuizActivity::class.java)
            intent.putParcelableArrayListExtra("wordArray", wordArrayList)
            startActivity(intent)
        }

//add버튼
        addFab.setOnClickListener {
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


