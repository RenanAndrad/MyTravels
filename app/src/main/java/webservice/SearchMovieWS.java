package webservice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import model.domain.ResponseMovie;
import webservice.util.Movie;
import webservice.util.ServiceUrl;


public class SearchMovieWS {

    private static final String TAG = "SuperMovies";
    private String title = "";
    private String year = "";
    private String plot = "full";
    private String r = "json";
    private static final String url = ServiceUrl.getUrlWsdl();
    private Activity activity;
    private String urlGet;
    private int TimeOut = 60000;
    private JSONObject returnObj;
    private boolean finished = false;

    public SearchMovieWS() {

    }

    public SearchMovieWS(RequestQueue queue, Activity activity, String param) {
        super();
        this.activity = activity;
    }


    public ResponseMovie searchMovie(Context context, final String param) throws IOException {


        final String paramFinal = param.replaceAll(" ", "+");

        urlGet = String.format(url + "t=%1$s&y=%2$s&plot=%3$s&r=%4$s",
                paramFinal,
                year,
                plot,
                r);


        final ResponseMovie responseMovie = new ResponseMovie();
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urlGet, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            Movie movie = new Movie();

                            if (response.getString("Title") != null) {
                                movie.setTitle(response.getString("Title"));
                            }
                            if (response.getString("Year") != null) {
                                movie.setYear(response.getString("Year"));
                            }
                            if (response.getString("Rated") != null) {
                                movie.setRated(response.getString("Rated"));
                            }
                            if (response.getString("Released") != null) {
                                movie.setReleased(response.getString("Released"));
                            }
                            if (response.getString("Runtime") != null) {
                                movie.setRuntime(response.getString("Runtime"));
                            }
                            if (response.getString("Genre") != null) {
                                movie.setGenre(response.getString("Genre"));
                            }
                            if (response.getString("Director") != null) {
                                movie.setDirector(response.getString("Director"));
                            }
                            if (response.getString("Writer") != null) {
                                movie.setWriter(response.getString("Writer"));
                            }
                            if (response.getString("Actors") != null) {
                                movie.setActors(response.getString("Actors"));
                            }
                            if (response.getString("Plot") != null) {
                                movie.setPlot(response.getString("Plot"));
                            }
                            if (response.getString("Language") != null) {
                                movie.setLanguage(response.getString("Language"));
                            }
                            if (response.getString("Country") != null) {
                                movie.setCountry(response.getString("Country"));
                            }
                            if (response.getString("Awards") != null) {
                                movie.setAwards(response.getString("Awards"));
                            }
                            if (response.getString("Poster") != null) {
                                movie.setPoster(response.getString("Poster"));
                            }
                            if (response.getString("Metascore") != null) {
                                movie.setMetascore(response.getString("Metascore"));
                            }
                            if (response.getString("imdbRating") != null) {
                                movie.setImdbRating(response.getString("imdbRating"));
                            }
                            if (response.getString("imdbVotes") != null) {
                                movie.setImdbVotes(response.getString("imdbVotes"));
                            }
                            if (response.getString("imdbID") != null) {
                                movie.setImdbID(response.getString("imdbID"));
                            }
                            if (response.getString("Type") != null) {
                                movie.setType(response.getString("Type"));
                            }
                            if (response.getString("Response") != null) {
                                movie.setResponse(response.getBoolean("Response"));
                            }

                            finished = true;
                            responseMovie.setMovie(movie);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            finished = true;
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finished = true;
                Log.d("ResponseErro", error.toString());
            }
        });


        queue.add(getRequest);

        while (!finished) {
            Log.e(TAG, "Aguardando resposta Volley");
        }
        return responseMovie;
    }


}
