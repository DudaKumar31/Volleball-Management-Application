package com.volleyball.api;



import com.volleyball.models.GetAllSeasonsPojo;
import com.volleyball.models.GetAllTeamsPojo;
import com.volleyball.models.ResponseData;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("Volleyball/addseasons.php?")
    Call<ResponseData> addseasons(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

    @GET("/Volleyball/getseasons.php?")
    Call<List<GetAllSeasonsPojo>> getseasons();
    @GET("/Volleyball/getteams.php?")
    Call<List<GetAllTeamsPojo>> getteams();

    @GET("/Volleyball/deleteseason.php?")
    Call<ResponseData> deleteseason(
            @Query("id") String id

    );
    @GET("Volleyball/deleteTeam.php")
    Call<ResponseData> deleteTeam(
            @Query("id") String id
    );

}
