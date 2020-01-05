package com.example.tabapplication.ui.main.fragment

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.PhoneAddActivity
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


private var isPermission = true


class NumberFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
//    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var numberAdapter: NumberAdapter

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
            adapter = viewAdapter
        }


        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewAdapter as NumberAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)



        val pFab: FloatingActionButton = view.findViewById(R.id.addphoneButton)
        pFab.setOnClickListener {
            val addintent = Intent(activity, PhoneAddActivity::class.java)

            startActivityForResult(addintent,0)
        }
        return view
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
                /*
                 * 여기에 서버통해서 보내기
                 *
                 */
//                wordArrayList.add(Word(vocabulary, meaning))
//                adapter = WordListAdapter(wordArrayList)
//                recyclerView!!.adapter = adapter
            }
        }
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
}