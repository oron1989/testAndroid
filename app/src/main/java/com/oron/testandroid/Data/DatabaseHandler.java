package com.oron.testandroid.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.oron.testandroid.Model.Movie;
import com.oron.testandroid.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_MOVIE_TITLE + " TEXT,"
                + Constants.KEY_MOVIE_IMAGE + " TEXT,"
                + Constants.KEY_MOVIE_RATING + " TEXT,"
                + Constants.KEY_RELEASE_YEAR + " TEXT);";

        db.execSQL(CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    //add Movie
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_MOVIE_TITLE, movie.getTitle());
        values.put(Constants.KEY_MOVIE_IMAGE, movie.getImage());
        values.put(Constants.KEY_MOVIE_RATING, String.valueOf(movie.getRating()));
        values.put(Constants.KEY_RELEASE_YEAR, String.valueOf(movie.getYear()));

        //insert the row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");
    }

    //get a Movie
    public Movie getMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Movie movie = new Movie();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_MOVIE_TITLE, Constants.KEY_MOVIE_IMAGE,
                        Constants.KEY_MOVIE_RATING, Constants.KEY_RELEASE_YEAR}, Constants.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            movie.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_TITLE)));
            movie.setImage(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_IMAGE)));
            movie.setRating(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_RATING))));
            movie.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RELEASE_YEAR))));

        }
        return movie;
    }

    //get all Movies
    public List<Movie> getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Movie> movieList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_MOVIE_TITLE, Constants.KEY_MOVIE_IMAGE,
                Constants.KEY_MOVIE_RATING, Constants.KEY_RELEASE_YEAR}, null, null, null, null,
                Constants.KEY_RELEASE_YEAR + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_TITLE)));
                movie.setImage(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_IMAGE)));
                movie.setRating(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOVIE_RATING))));
                movie.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_RELEASE_YEAR))));

                //add to the movieList
                movieList.add(movie);

            } while (cursor.moveToNext());
        }
        return movieList;
    }

    //updated Movie
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_MOVIE_TITLE, movie.getTitle());
        values.put(Constants.KEY_MOVIE_IMAGE, movie.getImage());
        values.put(Constants.KEY_MOVIE_RATING, String.valueOf(movie.getRating()));
        values.put(Constants.KEY_RELEASE_YEAR, String.valueOf(movie.getYear()));

        //updated row
        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[] {String.valueOf(movie.getId())});
    }

    //delete Movie
    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " =?", new  String[]{String.valueOf(id)});

        db.close();
    }

    //get count
    public int getMoviesCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
