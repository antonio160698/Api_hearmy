package com.example.mirest;


import com.example.mirest.modals.PlanetaryApod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostService{
    @GET("posts")
   Call<List<Posts>> getPosts();

    @GET("planetary/apod")
    Call<PlanetaryApod> getDayPlanetary(@Query("api_key") String api_key);
}



