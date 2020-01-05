package com.example.tabapplication

//import android.telecom.Call
import com.example.tabapplication.ui.main.fragment.NumberFragment
import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null)
data class User(var image: String, var email: String, var contact_list: MutableList<NumberFragment.Contact>) {
    override fun toString() : String {
        return email
    }
}

interface RetrofitService {

    //    Get all users
    @GET("/api/users")
    fun allUsers(
    ): Call<User>
//
//    //Get personal users
//    @GET("/api/users/{email}")
//    fun login(@Path("email") email: String
//        ): Call<>
//
//    //Get all contacts
//    @GET("/api/users/email/{email}/id")
//    fun contactList(@Path("email") email: String
//        ): Call<>
//
//    //Get personal contact
//    @GET("/api/users/email/{email}/id/{id}")
//    fun personalContact(@Path("email") email: String,
//                        @Path("id") id: String
//        ): Call<>

    //Create account
    @POST("/api/users")
    fun createAccount(@Body email: String
    ): Call<ResponseDTO>

//    //Create personal contact
//    @POST("/api/users/{email}")
//    fun createContact(@Path("email") email: String,
//                      @Body contact: NumberFragment.Contact
//        ): Call<>
//
//    //Add new image 'name'..?
//    @POST("/api/users/image/{email}")
//    fun newImage(@Path("email") email: String,
//                 @Body image: String
//        ): Call<>
//
//    //Update the 'user'?
//    @PUT("/api/users/{email}")
//    fun updateUser(@Path("email") email: String,
//                   @Body contact_list: MutableList<NumberFragment.Contact>
//        ): Call<>
//
//
//    // Modify personal contact
//    @PUT("/api/users/email/{email}")
//    fun modifyContact(@Path("email") email: String,
//                      @Body contact: NumberFragment.Contact
//        ): Call<>
//
//    // Delete user(회원탈퇴)
//    @DELETE("/api/users/{email}")
//    fun deleteUser(@Path("email") email: String
//        ): Call<>

}