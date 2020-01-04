package com.example.tabapplication.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.RESULT_CODE
import com.example.tabapplication.ui.main.activity.WordAddActivity

import com.example.tabapplication.ui.main.activity.WordQuizActivity
import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.example.tabapplication.ui.main.adapter.Word

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.DataOutputStream
import java.lang.AssertionError

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {

    var wordArrayList: ArrayList<Word> = ArrayList()
    var adapter: WordListAdapter = WordListAdapter(wordArrayList)
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View? = inflater.inflate(R.layout.fragment_word, container, false)

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

        recyclerView = view!!.findViewById(R.id.my_recycler_view)
        adapter = WordListAdapter(wordArrayList)

        var viewManager : RecyclerView.LayoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager



        val plusFab: FloatingActionButton = view.findViewById(R.id.plusFab)
        val quizFab: FloatingActionButton = view.findViewById(R.id.quizFab)
        val addFab: FloatingActionButton = view.findViewById(R.id.addFab)
        val quizLayout: LinearLayout = view.findViewById(R.id.quizLayout)
        val addLayout: LinearLayout = view.findViewById(R.id.addLayout)
        val showButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_button)
        val hideButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_button)
        val showLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_layout)
        val hideLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_layout)

        plusFab.setOnClickListener{
            if(quizLayout.visibility == View.VISIBLE && addLayout.visibility == View.VISIBLE ){
                quizLayout.visibility = View.GONE
                addLayout.visibility = View.GONE
                quizFab.isClickable = false
                addFab.isClickable = false
                plusFab.startAnimation(hideButtonAnim)
                quizLayout.startAnimation(hideLayoutAnim)
                addLayout.startAnimation(hideLayoutAnim)
            }
            else{
                quizLayout.visibility = View.VISIBLE
                addLayout.visibility = View.VISIBLE
                quizFab.isClickable = true
                addFab.isClickable = true
                plusFab.startAnimation(showButtonAnim)
                quizLayout.startAnimation(showLayoutAnim)
                addLayout.startAnimation(showLayoutAnim)
            }
        }

        quizFab.setOnClickListener{
            val intent = Intent(activity, WordQuizActivity::class.java)
            intent.putParcelableArrayListExtra("wordArray", wordArrayList)
            startActivity(intent)
        }

        addFab.setOnClickListener{

//            wordArrayList.add(Word("A", "B"))
//            adapter = ListAdapter(wordArrayList)
//            recyclerView.adapter = adapter
            val addintent = Intent(activity, WordAddActivity::class.java)
            startActivityForResult(addintent, 0)
//            Toast.makeText(context, "AAA", Toast.LENGTH_LONG)
//            val main = Intent(activity, WordFragment::class.java)
//            val vocabulary = intent.getStringExtra("vocabulary")
//            val meaning = intent.getStringExtra("meaning")
//            wordArrayList.add(Word(vocabulary, meaning))
//            adapter = ListAdapter(wordArrayList)
//            recyclerView.adapter = adapter

        }


        return view
    }

    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CODE){
            val vocabulary = data!!.getStringExtra("vocabulary")
            val meaning = data!!.getStringExtra("meaning")
            if(vocabulary == "" || meaning == ""){
                Toast.makeText(context, "Please write something", Toast.LENGTH_SHORT).show()
            }
            else {
                wordArrayList.add(Word(vocabulary, meaning))
                adapter = WordListAdapter(wordArrayList)
                recyclerView!!.adapter = adapter
            }
        }
    }


}


