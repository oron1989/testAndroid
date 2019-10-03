package com.oron.testandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oron.testandroid.R;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private TextView rating;
    private TextView year;
    private TextView genre;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        image = findViewById(R.id.detailsImageID);
        title = findViewById(R.id.detailsTitleID);
        rating = findViewById(R.id.detailsRatingID);
        year = findViewById(R.id.detailsYearID);
        genre = findViewById(R.id.detailsGenreID);
        backButton = findViewById(R.id.beckButton);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            title.setText(bundle.getString("title"));
            rating.setText(String.valueOf(bundle.getDouble("rating")));
            year.setText(String.valueOf(bundle.getInt("year")));
            Picasso.with(this).load(bundle.getString("image")).placeholder(android.R.drawable.ic_btn_speak_now).into(image);

        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieDetailsActivity.this, MovieListActivity.class));
                finish();
            }
        });
    }
}
