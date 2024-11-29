package com.example.kinopoiskapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {
    private ListView listView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listView = findViewById(R.id.movie_list);
        Button openSearchButton = findViewById(R.id.open_search_button);

        openSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MovieListActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.getTopMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getFilms();
                    adapter = new MovieAdapter(MovieListActivity.this, movies);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(MovieListActivity.this, "Ошибка загрузки фильмов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieListActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
