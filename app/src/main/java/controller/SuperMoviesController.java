package controller;

import android.content.Context;
import android.util.Log;


import model.dao.SuperMoviesDAO;
import model.domain.ResponseMovie;


public class SuperMoviesController {

    private SuperMoviesDAO superMoviesDAO;
    private static final String TAG = "SuperMovies";
    private Context context;


    public SuperMoviesController(Context context) {
        super();
        superMoviesDAO = new SuperMoviesDAO();
        this.context = context;
    }

    public ResponseMovie searchMovie(Context context, String param) {
        ResponseMovie responseMovie = new ResponseMovie();

        try {
            responseMovie = superMoviesDAO.searchMovie(context, param);
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }

        return responseMovie;
    }



}
