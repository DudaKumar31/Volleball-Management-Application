package com.volleyball.api;



import com.volleyball.models.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET("Volleyball/adminlogin.php?")
    Call<ResponseData> adminLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("Volleyball/managerlogin.php?")
    Call<ResponseData> managerlogin(
            @Query("email") String email,
            @Query("password") String password
    );


}
