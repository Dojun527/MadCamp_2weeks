package com.example.tabapplication.ui.main.fragment

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.PhoneAddActivity
import com.example.tabapplication.ui.main.activity.PhoneSyncActivity
import com.example.tabapplication.ui.main.activity.RESULT_CODE
import com.example.tabapplication.ui.main.activity.RESULT_NUM
import com.example.tabapplication.ui.main.adapter.NumberAdapter
import com.example.tabapplication.ui.main.adapter.SwipeToDeleteCallback
import com.example.tabapplication.ui.main.adapter.Word
import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class NumberFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private  var viewAdapter: RecyclerView.Adapter<*>? = null

//    private lateinit var viewManager: RecyclerView.LayoutManager
//    private lateinit var numberAdapter: NumberAdapter

    companion object{
        private const val READ_CONTACTS_PERMISSIONS_REQUEST = 1
        private const val WRITE_CONTACTS_PERMISSIONS_REQUEST = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissionToReadUserContacts();
        getPermissionToWriteUserContacts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            var viewManager : RecyclerView.LayoutManager = LinearLayoutManager(context)

            layoutManager = viewManager
            if(viewAdapter != null ) {
                adapter = viewAdapter
            }

            val plusFab: FloatingActionButton = view.findViewById(R.id.plusFab)
            val syncFab: FloatingActionButton = view.findViewById(R.id.SyncFab)
            val addFab: FloatingActionButton = view.findViewById(R.id.addFab)
            val syncLayout: LinearLayout = view.findViewById(R.id.SyncLayout)
            val addLayout: LinearLayout = view.findViewById(R.id.addLayout)
            val showButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_button)
            val hideButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_button)
            val showLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_layout)
            val hideLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_layout)

            plusFab.setOnClickListener{
                if(syncLayout.visibility == View.VISIBLE && addLayout.visibility == View.VISIBLE ){
                    syncLayout.visibility = View.GONE
                    addLayout.visibility = View.GONE
                    syncFab.isClickable = false
                    addFab.isClickable = false
                    plusFab.startAnimation(hideButtonAnim)
                    syncLayout.startAnimation(hideLayoutAnim)
                    addLayout.startAnimation(hideLayoutAnim)
                }
                else{
                    syncLayout.visibility = View.VISIBLE
                    addLayout.visibility = View.VISIBLE
                    syncFab.isClickable = true
                    addFab.isClickable = true
                    plusFab.startAnimation(showButtonAnim)
                    syncLayout.startAnimation(showLayoutAnim)
                    addLayout.startAnimation(showLayoutAnim)
                }
            }

            syncFab.setOnClickListener{
                val intent = Intent(activity, PhoneSyncActivity::class.java)
//                intent.putParcelableArrayListExtra("wordArray", wordArrayList)
                startActivity(intent)
            }

            addFab.setOnClickListener{

                //            wordArrayList.add(Word("A", "B"))
//            adapter = ListAdapter(wordArrayList)
//            recyclerView.adapter = adapter
                val addintent = Intent(activity, PhoneAddActivity::class.java)
                startActivityForResult(addintent, 0)
//            Toast.makeText(context, "AAA", Toast.LENGTH_LONG)
//            val main = Intent(activity, WordFragment::class.java)
//            val vocabulary = intent.getStringExtra("vocabulary")
//            val meaning = intent.getStringExtra("meaning")
//            wordArrayList.add(Word(vocabulary, meaning))
//            adapter = ListAdapter(wordArrayList)
//            recyclerView.adapter = adapter

            }
        }


        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewAdapter as NumberAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return view
    }

    data class Contact(val name: String, val phoneNumber: String)

    fun Context.fetchAllContacts(): MutableList<Contact> {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
            .use { cursor ->
                //                if (cursor == null) return MutableList().empty
                val builder = ArrayList<Contact>()
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                ?: "N/A"
                        val phoneNumber =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                ?: "N/A"
                        builder.add(Contact(name, phoneNumber))
                    }
                }
                return builder
            }
    }

    fun getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(READ_CONTACTS),
                READ_CONTACTS_PERMISSIONS_REQUEST
            )
        } else {
            layContactsList()
        }
    }

    fun getPermissionToWriteUserContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(WRITE_CONTACTS),
                WRITE_CONTACTS_PERMISSIONS_REQUEST
            )
        } else {
            layContactsList()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == NumberFragment.READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Read Contacts permission granted", Toast.LENGTH_SHORT).show()
                layContactsList()
            } else {
                Toast.makeText(context, "Read Contacts permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (requestCode == NumberFragment.WRITE_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Write Contacts permission granted", Toast.LENGTH_SHORT).show()
                layContactsList()
            } else {
                Toast.makeText(context, "Write Contacts permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun layContactsList() {
        viewAdapter = NumberAdapter(requireContext().fetchAllContacts()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phoneNumber}")
            startActivity(intent)
        }
    }


    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_NUM){
            val name = data!!.getStringExtra("name")
            val phonenumber = data!!.getStringExtra("phonenumber")
            if(name == "" || phonenumber == ""){
                Toast.makeText(context, "Please write something", Toast.LENGTH_SHORT).show()
            }
            else {
                viewAdapter = NumberAdapter(requireContext().fetchAllContacts()) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${it.phoneNumber}")
                    startActivity(intent)
                }
//                wordArrayList.add(Word(vocabulary, meaning))
//                adapter = WordListAdapter(wordArrayList)
//                recyclerView!!.adapter = adapter
            }
        }
    }
}