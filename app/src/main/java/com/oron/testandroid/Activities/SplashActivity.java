package com.oron.testandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.oron.testandroid.Data.DatabaseHandler;
import com.oron.testandroid.Model.Movie;
import com.oron.testandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private Button startApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        db = new DatabaseHandler(this);

//        byPassActivity();

        if (db.getMoviesCount() < 1) {
            saveMovieToDB();
        }

        startApp = findViewById(R.id.startApp);

        startApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("start new activity:", "starting");
                startActivity(new Intent(SplashActivity.this, MovieListActivity.class));
                finish();
            }
        });


    }

    private void saveMovieToDB () {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.androidhive.info/json/movies.json";

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject movieObj = response.getJSONObject(i);

                                //Parse the JSON
                                Movie movie = new Movie();
                                movie.setTitle(movieObj.getString("title"));
                                movie.setImage(movieObj.getString("image"));
                                movie.setRating(movieObj.getDouble("rating"));
                                movie.setYear(movieObj.getInt("releaseYear"));
                                movie.setGenre(movieObj.getString("genre"));
                                Log.i("json:", movie.getGenre());

                                //save to db
                                db.addMovie(movie);
                                //Log.d("Item Added ID:", String.valueOf(db.getMoviesCount()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

    }

}
