package com.oron.testandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.oron.testandroid.Data.DatabaseHandler;
import com.oron.testandroid.Data.MovieRecyclerViewAdapter;
import com.oron.testandroid.Model.Movie;
import com.oron.testandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter adapter;
    private List<Movie> movieList;
    private List<Movie> listItems;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        listItems = new ArrayList<>();

        //get items from database
        movieList = db.getAllMovies();

        for (Movie mov : movieList ) {
             Movie movie = new Movie();
             movie.setTitle(mov.getTitle());
             movie.setImage(mov.getImage());
             movie.setRating(mov.getRating());
             movie.setYear(mov.getYear());

             listItems.add(movie);
        }

        adapter = new MovieRecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
