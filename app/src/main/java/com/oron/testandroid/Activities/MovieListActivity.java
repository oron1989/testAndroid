package com.oron.testandroid.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.oron.testandroid.Data.DatabaseHandler;
import com.oron.testandroid.Data.MovieRecyclerViewAdapter;
import com.oron.testandroid.Model.Movie;
import com.oron.testandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter adapter;
    private List<Movie> movieList;
    private List<Movie> listItems;
    private DatabaseHandler db;

    private ConstraintLayout constraintLayout;

    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        constraintLayout = findViewById(R.id.listViewID);

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
             movie.setGenre(mov.getGenre());

             listItems.add(movie);
        }

        adapter = new MovieRecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Toast.makeText(this, "click", Toast.LENGTH_LONG).show();
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    Movie movie = new Movie();
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to movie obj
                    movie.setTitle(obj.getString("title"));
                    movie.setImage(obj.getString("image"));
                    movie.setRating(obj.getDouble("rating"));
                    movie.setYear(obj.getInt("releaseYear"));

                    if (!db.isExist(movie.getTitle())){
                        db.addMovie(movie);
                        Toast.makeText(this, "movie add", Toast.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(constraintLayout, "Current movie already exist in the Database", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(this, "movie is exist", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(this, movie.getTitle(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
