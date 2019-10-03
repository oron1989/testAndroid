package com.oron.testandroid.Model;

public class Movie {

    int id;
    String title;
    String image;
    double rating;
    int year;

    public Movie() {
    }

    public Movie(String title, String image, double rating, int year) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
