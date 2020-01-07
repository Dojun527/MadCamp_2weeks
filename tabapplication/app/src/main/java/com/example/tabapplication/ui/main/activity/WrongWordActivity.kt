package com.example.tabapplication.ui.main.activity


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
class WrongWordActivity :AppCompatActivity() {

    var wordArrayList: ArrayList<Word> = ArrayList()
    var adapter: WordListAdapter = WordListAdapter(wordArrayList)
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_word)

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
            wordArrayList.add(Word("A", "B"))
            adapter = WordListAdapter(wordArrayList)  //ListAdapter(wordArrayList)
            recyclerView.adapter = adapter
            val addintent = Intent(this, WordAddActivity::class.java)
            startActivityForResult(addintent, 0)
            Toast.makeText(this, "AAA", Toast.LENGTH_LONG)
            val main = Intent(this, WordFragment::class.java)
            val vocabulary = intent.getStringExtra("vocabulary")
            val meaning = intent.getStringExtra("meaning")
            wordArrayList.add(Word(vocabulary, meaning))
            adapter = WordListAdapter(wordArrayList)
            recyclerView.adapter = adapter
        }


    }
}


