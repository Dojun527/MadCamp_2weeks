package com.example.tabapplication.ui.main.activity


//오답 노트

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
import com.example.tabapplication.ui.main.activity.WordQuizActivity.Companion.wrongwordArrayList

import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.example.tabapplication.ui.main.adapter.Word
import com.example.tabapplication.ui.main.fragment.WordFragment

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */

class WrongWordActivity :AppCompatActivity() {

    var adapter: WordListAdapter = WordListAdapter(wrongwordArrayList)
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wrong_word)


        recyclerView = findViewById(R.id.my_recycler_view)
        adapter = WordListAdapter(wrongwordArrayList)

        var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager


        val plusFab: FloatingActionButton = findViewById(R.id.plusFab)
        val quizFab: FloatingActionButton = findViewById(R.id.quizFab)
        val quizLayout: LinearLayout = findViewById(R.id.quizLayout)
        val showButtonAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.show_button)
        val hideButtonAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.hide_button)
        val showLayoutAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.show_layout)
        val hideLayoutAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.hide_layout)

        plusFab.setOnClickListener {
            if (quizLayout.visibility == View.VISIBLE) {
                quizLayout.visibility = View.GONE
                quizFab.isClickable = false
                plusFab.startAnimation(hideButtonAnim)
                quizLayout.startAnimation(hideLayoutAnim)
            } else {
                quizLayout.visibility = View.VISIBLE
                quizFab.isClickable = true
                plusFab.startAnimation(showButtonAnim)
                quizLayout.startAnimation(showLayoutAnim)
            }
        }

//quiz버튼
        quizFab.setOnClickListener {
            val intent = Intent(this, WordQuizActivity::class.java)
            intent.putParcelableArrayListExtra("wordArray", wrongwordArrayList)
        }

    }

}


