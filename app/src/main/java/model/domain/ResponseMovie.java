package model.domain;


import webservice.util.Movie;

public class ResponseMovie {

private Movie movie;


    public ResponseMovie(Movie movie) {
        this.movie = movie;

    }

    public ResponseMovie() {

    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
