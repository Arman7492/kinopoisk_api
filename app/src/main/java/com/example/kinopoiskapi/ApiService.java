package com.example.kinopoiskapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "X-API-KEY: 899b2644-8d58-4ff7-adac-e80be40e1d84",
            "Content-Type: application/json"
    })
    @GET("films/search-by-keyword")
    Call<MovieResponse> searchMovies(
            @Query("keyword") String keyword
    );

    @Headers({
            "X-API-KEY: 899b2644-8d58-4ff7-adac-e80be40e1d84",
            "Content-Type: application/json"
    })
    @GET("films/top")
    Call<MovieResponse> getTopMovies();
}
