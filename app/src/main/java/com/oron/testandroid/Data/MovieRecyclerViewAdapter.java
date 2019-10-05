package com.oron.testandroid.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oron.testandroid.Activities.MovieDetailsActivity;
import com.oron.testandroid.Model.Movie;
import com.oron.testandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        String imageLink = movie.getImage();

        holder.title.setText(movie.getTitle());
        holder.releaseYear.setText(String.valueOf(movie.getYear()));
        holder.rating.setText(String.valueOf(movie.getRating()));

        Picasso.with(context).load(imageLink).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView image;
        TextView releaseYear;
        TextView rating;
        public int id;

        public ViewHolder(@NonNull final View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            title = itemView.findViewById(R.id.movieTitleID);
            image = itemView.findViewById(R.id.movieImageID);
            releaseYear = itemView.findViewById(R.id.movieReleaseID);
            rating = itemView.findViewById(R.id.ratingID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to the next screen
                    int position = getAdapterPosition();
                    Movie movie = movieList.get(position);
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("title", movie.getTitle());
                    intent.putExtra("image", movie.getImage());
                    intent.putExtra("rating", movie.getRating());
                    intent.putExtra("year", movie.getYear());
                    intent.putExtra("genre", movie.getGenre());

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
