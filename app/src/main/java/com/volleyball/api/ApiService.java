package com.volleyball.api;



import com.volleyball.models.GetAllManagersPojo;
import com.volleyball.models.GetAllNewsPojo;
import com.volleyball.models.GetAllSchedulePojo;
import com.volleyball.models.GetAllSeasonsPojo;
import com.volleyball.models.GetAllTeamsPojo;
import com.volleyball.models.PlayerPojo;
import com.volleyball.models.PlayerScoreModel;
import com.volleyball.models.ResponseData;
import com.volleyball.models.TeamsPojo;
import com.volleyball.models.TmProfilePojo;

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

    @Multipart
    @POST("Volleyball/editplayer.php")
    Call<ResponseData> editplayer(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("Volleyball/editseason.php")
    Call<ResponseData> editseason(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @Multipart
    @POST("Volleyball/addplayer.php?")
    Call<ResponseData> addplayer(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("Volleyball/addnews.php?")
    Call<ResponseData> addnews(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );


    @GET("/Volleyball/getplayer.php?")
    Call<List<PlayerPojo>> getplayer();

    @GET("/Volleyball/getseasons.php?")
    Call<List<GetAllSeasonsPojo>> getseasons();

    @GET("/Volleyball/getnews.php?")
    Call<List<GetAllNewsPojo>> getnews();

    @GET("/Volleyball/getteams.php?")
    Call<List<GetAllTeamsPojo>> getteams();

    @GET("/Volleyball/getteams.php")
    Call<List<TeamsPojo>> getteamsspinner();


    @GET("/Volleyball/deleteseason.php?")
    Call<ResponseData> deleteseason(
            @Query("id") String id

    );
    @Multipart
    @POST("Volleyball/addteams.php?")
    Call<ResponseData> addteams(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @Multipart
    @POST("Volleyball/editTeam.php?")
    Call<ResponseData> editTeam(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @Multipart
    @POST("Volleyball/addingmanager.php?")
    Call<ResponseData> addingmanager(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @GET("Volleyball/deleteTeam.php")
    Call<ResponseData> deleteTeam(
            @Query("id") String id
    );
    @GET("/Volleyball/gettestschedule.php?")
    Call<List<GetAllSchedulePojo>> gettestschedule();

    @GET("/Volleyball/getmanagers.php?")
    Call<List<GetAllManagersPojo>> getmanagers();

    @GET("/Volleyball/deletemanager.php?")
    Call<ResponseData> deletemanager(
            @Query("id") String id

    );

    @GET("/Volleyball/deletenews.php?")
    Call<ResponseData> deletenews(
            @Query("id") String id

    );

    @GET("/Volleyball/deleteplayer.php?")
    Call<ResponseData> deleteplayer(
            @Query("id") String id

    );

    @Multipart
    @POST("Volleyball/editmanager.php?")
    Call<ResponseData> editmanager(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @GET("/Volleyball/getMyTeamPlayers.php?")
    Call<List<PlayerPojo>> getMyTeamPlayers(
            @Query("team_id") String team_id

    );

    @GET("/Volleyball/getteamsformanagers.php")
    Call<List<TeamsPojo>> getavailableteamsspinner();

    @GET("/Volleyball/getmymanagerprofile.php?")
    Call<List<TmProfilePojo>> getmymanagerprofile(@Query("email") String email);


    @Multipart
    @POST("Volleyball/updatemanagerprofile.php?")
    Call<ResponseData> updatemanagerprofile(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @GET("Volleyball/addtestschedule.php?")
    Call<ResponseData> addSchedule(
            @Query("t1_id") String t1_id,
            @Query("t1_name") String t1_name,
            @Query("t1_logo") String t1_logo,
            @Query("t2_id") String t2_id,
            @Query("t2_name") String t2_name,
            @Query("t2_logo") String t2_logo,
            @Query("schedule_date") String schedule_date,
            @Query("season") String season
    );

    @GET("/Volleyball/addmatchresult.php")
    Call<ResponseData> addTeamResultScore(
            @Query("s_id") String s_id,
            @Query("team1_score") String team1_score,
            @Query("team2_score") String team2_score,
            @Query("result") String result);

    @GET("/Volleyball/getteams.php")
    Call<List<TeamsPojo>> getmatchteams();

    @GET("/Volleyball/gettestschedule.php?")
    Call<List<GetAllSchedulePojo>> getMatchResults();

    @GET("/Volleyball/getschedulebyseason.php?")
    Call<List<GetAllSchedulePojo>> getschedulebyseason(@Query("sid") String sid);


    @GET("/Volleyball/getPlayersScore.php")
    Call<List<PlayerScoreModel>> getPlayersScore(@Query("sid") String sid, @Query("team_id") String team_id);

    @GET("/Volleyball/getPlayersByTeam.php")
    Call<List<PlayerPojo>> getPlayersByTeam(@Query("team_id") String team_id);

    @GET("/Volleyball/addPlayerScore.php")
    Call<ResponseData> addPlayerScore
            (@Query("sid") String sid,
             @Query("p_name") String p_name,
             @Query("score") String score,
             @Query("team_id") String team_id,
             @Query("totalscore") String totalscore);



}
