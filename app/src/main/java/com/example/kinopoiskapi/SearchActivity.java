package com.example.kinopoiskapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText keywordInput, genreInput;
    private Button searchButton, backToListButton;
    private ListView searchResults;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordInput = findViewById(R.id.keyword_input);
        genreInput = findViewById(R.id.genre_input);
        searchButton = findViewById(R.id.search_button);
        backToListButton = findViewById(R.id.back_to_list_button);
        searchResults = findViewById(R.id.search_results);


        backToListButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MovieListActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(v -> searchMovies());
    }

    private void searchMovies() {
        String keyword = keywordInput.getText().toString().trim();
        String genreStr = genreInput.getText().toString().trim();
        int genre = genreStr.isEmpty() ? 0 : Integer.parseInt(genreStr);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.searchMovies(keyword);

        Log.d("SearchRequest", "URL: " + call.request().url());

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getFilms();
                    if (movies != null && !movies.isEmpty()) {
                        adapter = new MovieAdapter(SearchActivity.this, movies);
                        searchResults.setAdapter(adapter);
                    } else {
                        Toast.makeText(SearchActivity.this, "Фильмы не найдены.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchActivity.this, "Ошибка API: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
