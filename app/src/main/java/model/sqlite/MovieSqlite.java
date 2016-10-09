package model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import webservice.util.Movie;

public class MovieSqlite {

    private static final String TAG = "SuperMovies";
    private SQLiteDatabase database;
    private ManagerSqlite managerSqlite;
    private Context context;
    private SharedPreferences sharedPreferences;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private String idFacebook;

    private String[] colunTableMovie = {
            managerSqlite.TABLE_ID_MOVIE,
            managerSqlite.TABLE_TITLE,
            managerSqlite.TABLE_PLOT,
            managerSqlite.TABLE_YEAR,
            managerSqlite.TABLE_RUNTIME,
            managerSqlite.TABLE_GENRE,
            managerSqlite.TABLE_DIRECTOR,
            managerSqlite.TABLE_ACTORS,
            managerSqlite.TABLE_LANGUAGE,
            managerSqlite.TABLE_POSTER,
            managerSqlite.TABLE_ID_FACEBOOK,
            managerSqlite.TABLE_RATING};


    public MovieSqlite(Context context) {
        managerSqlite = new ManagerSqlite(context);
        this.context = context;
        loadPreferencess();
    }

    public void open() {
        database = managerSqlite.getWritableDatabase();
    }

    public void close() {
        managerSqlite.close();
    }


    public List<Movie> list() {
        List<Movie> movies = new ArrayList<Movie>();

        String selectQuery = "select * from "
                + managerSqlite.TABLE_MOVIE + " where " + colunTableMovie[10] + " = " + idFacebook;

        open();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                Movie movie = new Movie();

                movie.setId(cursor.getInt(cursor.getColumnIndex(managerSqlite.TABLE_ID_MOVIE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_TITLE)));
                movie.setPlot(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_PLOT)));
                movie.setYear(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_YEAR)));
                movie.setRuntime(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RUNTIME)));
                movie.setGenre(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_GENRE)));
                movie.setDirector(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_DIRECTOR)));
                movie.setActors(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_ACTORS)));
                movie.setLanguage(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_LANGUAGE)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_POSTER)));
                movie.setIdFacebook(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_ID_FACEBOOK)));

                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)) != null) {
                    movie.setRating(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)));
                }

                movies.add(movie);
            }
        }


        cursor.close();

        return movies;
    }

    public String listMovieRating(Movie movie) {

        String selectQuery = "select * from "
                + managerSqlite.TABLE_MOVIE + " where " + colunTableMovie[10] + " = " + idFacebook + " and " + colunTableMovie[0] + " = " + movie.getId();

        open();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)) != null) {
                    movie.setRating(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)));
                }

            }
        }

        cursor.close();

        return movie.getRating();
    }

    public long save(Movie movie) {
        open();
        ContentValues values = new ContentValues();


        values.put(colunTableMovie[1], movie.getTitle().toString());
        values.put(colunTableMovie[2], movie.getPlot().toString());
        values.put(colunTableMovie[3], movie.getYear().toString());
        values.put(colunTableMovie[4], movie.getRuntime().toString());
        values.put(colunTableMovie[5], movie.getGenre().toString());
        values.put(colunTableMovie[6], movie.getDirector().toString());
        values.put(colunTableMovie[7], movie.getActors().toString());
        values.put(colunTableMovie[8], movie.getLanguage().toString());
        values.put(colunTableMovie[9], movie.getPoster().toString());
        values.put(colunTableMovie[10], movie.getIdFacebook().toString());


        long result = -1;
        try {
            result = database.insert(managerSqlite.TABLE_MOVIE,
                    null, values);
        } catch (SQLiteConstraintException e) {
            result = -1;
        } catch (Exception e) {
            result = -1;
        }

        close();
        return result;
    }

    public long update(Movie movie) {
        ContentValues values = new ContentValues();

        if (movie.getRating() != null) {
            values.put(colunTableMovie[11], movie.getRating().toString());
        }

        String where = colunTableMovie[0] + " = " + movie.getId() + " and " + colunTableMovie[10] + " = " + idFacebook;
        database = managerSqlite.getReadableDatabase();

        long result = database.update(managerSqlite.TABLE_MOVIE, values, where, null);
        Log.d("Update Result:", "=" + result);
        return result;

    }


    public long delete(Movie movie) {


        String where = colunTableMovie[0] + "=" + movie.getId() + " and " + colunTableMovie[10] + " = " + idFacebook;
        database = managerSqlite.getReadableDatabase();
        long result = database.delete(managerSqlite.TABLE_MOVIE, where, null);
        close();

        return result;
    }

    private void loadPreferencess() {
        try {
            sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            idFacebook = sharedPreferences.getString("idFacebook", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }
}
