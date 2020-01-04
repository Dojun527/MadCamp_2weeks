package com.example.tabapplication.ui.main.fragment

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.R.layout.fragment_gallery
import com.example.tabapplication.ui.main.adapter.GalleryImageAdapter
import com.example.tabapplication.ui.main.adapter.GalleryImageClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.thesimplycoder.imagegallery.adapter.Image
import java.io.File
import java.util.*


private const val PICK_FROM_ALBUM = 1
private var tempFile: File? = null
private var isPermission = true
@Suppress("DEPRECATION")
class GalleryFragment : Fragment(), GalleryImageClickListener {

    private val TAG: String = "Jungbin";
    private var SPAN_COUNT = 3
    private val imageList = java.util.ArrayList<Image>()
    lateinit var galleryAdapter: GalleryImageAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            fragment_gallery,
            container,
            false
        )

        galleryAdapter = GalleryImageAdapter(imageList)
        galleryAdapter.listener = this

        loadImages()
        var recyclerview: RecyclerView =  view.findViewById(R.id.recyclerView)
        //가로모드인 경우 col수 4개로 바꿈
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT = 4
        }
        recyclerview.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerview.adapter = galleryAdapter
        tedPermission()
        val mFab: FloatingActionButton = view.findViewById(R.id.addButton)
        mFab.setOnClickListener{
            if(isPermission) goToAlbum()
            else Toast.makeText(view.context, resources.getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        }

        return view
    }

    override fun onClick(position: Int) {

        val bundle = Bundle()
        bundle.putSerializable("images",imageList)
        bundle.putInt("position",position)

        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val galleryFullFragment = GalleryFullscreenFragment()
        galleryFullFragment.arguments = bundle
        galleryFullFragment.show(fragmentTransaction,"gallery")
    }




    private fun loadImages(){
        imageList.add(Image("Christmas", "https://cdn.pixabay.com/photo/2018/11/24/01/35/christmas-motif-3834860__340.jpg"))
        imageList.add(Image("Gift","https://cdn.pixabay.com/photo/2019/12/03/07/34/gift-4669449__340.jpg"))
        imageList.add(Image("Branch","https://cdn.pixabay.com/photo/2016/10/30/09/30/hot-chocolate-1782623__340.jpg"))
        imageList.add(Image("City","https://cdn.pixabay.com/photo/2013/03/02/02/41/city-89197__340.jpg"))
        imageList.add(Image("Road","https://cdn.pixabay.com/photo/2013/11/28/10/36/road-220058__340.jpg"))
        imageList.add(Image("Snowman","https://cdn.pixabay.com/photo/2019/12/05/11/10/snowman-4674856__340.jpg"))
        imageList.add(Image("Autoradio","https://cdn.pixabay.com/photo/2019/12/04/09/45/autoradio-4672254__340.jpg"))
        imageList.add(Image("Autumn","https://cdn.pixabay.com/photo/2019/10/31/06/58/avenue-4591121__340.jpg"))
        imageList.add(Image("Card","https://cdn.pixabay.com/photo/2014/12/21/07/50/christmas-card-574742__340.jpg"))
        imageList.add(Image("Crocus","https://cdn.pixabay.com/photo/2018/03/15/23/51/crocus-3229861__340.jpg"))
        imageList.add(Image("Fantasy","https://cdn.pixabay.com/photo/2015/04/23/21/59/hot-air-balloon-736879__340.jpg"))
        imageList.add(Image("Fox","https://cdn.pixabay.com/photo/2019/10/30/16/19/fox-4589927__340.jpg"))
        imageList.add(Image("Landscape","https://cdn.pixabay.com/photo/2013/11/28/10/03/autumn-219972__340.jpg"))
        imageList.add(Image("Locomotive","https://cdn.pixabay.com/photo/2016/10/10/14/44/train-1728537__340.jpg"))
        imageList.add(Image("Parrot","https://cdn.pixabay.com/photo/2019/12/16/19/24/parrot-4700043__340.jpg"))
        imageList.add(Image("Switzerland","https://cdn.pixabay.com/photo/2016/07/28/09/01/nature-1547302__340.jpg"))
        imageList.add(Image("France","https://cdn.pixabay.com/photo/2018/04/25/09/26/eiffel-tower-3349075__340.jpg"))
        imageList.add(Image("Town","https://cdn.pixabay.com/photo/2014/08/11/11/06/paris-415476__340.jpg"))
        imageList.add(Image("Cat","https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_960_720.jpg"))
        imageList.add(Image("Winter","https://cdn.pixabay.com/photo/2014/04/10/15/37/snowman-321034__340.jpg"))

        galleryAdapter.notifyDataSetChanged()
    }

    private fun tedPermission(){
        var permissionListener: PermissionListener = object: PermissionListener {
            override fun onPermissionGranted() {
                isPermission = true
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                isPermission = false
            }
        }
        // 마시멜로 이상 권한 체크
    if(Build.VERSION.SDK_INT >= 23) {
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    }

    private fun goToAlbum(){
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        startActivityForResult(intent, PICK_FROM_ALBUM )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK){
            Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_SHORT).show()

            if(tempFile != null){
                if(tempFile!!.delete()){
                    Log.e(TAG, tempFile!!.absolutePath + "삭제 성공")
                    tempFile = null
                }
            }
            return
        }

        if(requestCode == PICK_FROM_ALBUM){

            if (data != null) {
                if(data.clipData== null){
                    //Toast.makeText(context, "다중 선택이 불가한 기기입니다.", Toast.LENGTH_LONG).show()
                    val photoUri: Uri? = data!!.data
                    imageList.add(Image("new",photoUri.toString()))
                    galleryAdapter.notifyDataSetChanged()
                } else{
                    var clipData: ClipData ?= data.clipData
                    Log.i("clipdata", clipData!!.itemCount.toString())

                    if(clipData.itemCount > 9){
                        Toast.makeText(context,"사진은 9장까지 선택 가능합니다", Toast.LENGTH_LONG).show()
                    }
        //                else if(clipData.itemCount == 1){
        //
        //                }
                    else{
                        for(i in 0 until clipData.itemCount){
                            imageList.add(Image("new",clipData.getItemAt(i).uri.toString()))
                        }
                        galleryAdapter.notifyDataSetChanged()
                    }
                }
            }

            //val photoUri: Uri? = data!!.data
            //imageList.add(Image("new",photoUri.toString()))

//            try{
//                val proj =
//                    arrayOf(MediaStore.Images.Media.DATA)
//
//                assert(photoUri != null)
//                var contentResolver: ContentResolver = object: ContentResolver(context){
//
//                }
//                cursor =  contentResolver.query(photoUri!!, proj, null, null, null)
//
//                assert(cursor != null)
//                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//
//                cursor.moveToFirst()
//
//                tempFile = File(cursor.getString(column_index))
//                Log.d(TAG, "tempFile Uri: " + Uri.fromFile(tempFile))
//            }
//            finally {
//                cursor?.close()
//            }
            //addImage();
        }
    }

}









