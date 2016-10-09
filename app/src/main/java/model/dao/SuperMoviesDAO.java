package model.dao;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import model.domain.ResponseMovie;
import webservice.SearchMovieWS;
import webservice.util.Movie;


public class SuperMoviesDAO {

    public ResponseMovie searchMovie(Context context, String param) throws IOException, XmlPullParserException {
        SearchMovieWS searchMovieWS = new SearchMovieWS();
        return searchMovieWS.searchMovie(context,param);
    }

}
