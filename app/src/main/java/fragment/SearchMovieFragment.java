package fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.super_movies.br.supermovies.R;

import java.io.IOException;

import adapter.MovieListAdapter;
import controller.SuperMoviesController;
import model.domain.ResponseMovie;
import model.sqlite.MovieSqlite;
import util.Alert;
import util.IAlertaConfirmacao;
import webservice.util.Movie;


public class SearchMovieFragment extends Fragment {


    private static final String TAG = "Super Movies";

    private ListView lvMovie;
    private EditText edtTitle;
    private Button btnSearch, btnSave;
    private ImageView ivPoster;
    private TextView txtTitleMovie, txtYear, txtGenre, txtRated;
    private ProgressDialog progressDialog;
    private Alert alert;

    private MovieListAdapter movieListAdapter;
    private String param;
    private Movie movie;
    private String idFacebook;

    private SuperMoviesController superMoviesController;
    private SearchMovieTask searchMovieTask;
    private ResponseMovie responseMovie = new ResponseMovie();

    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        lvMovie = (ListView) view.findViewById(R.id.lvCountries);
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        ivPoster = (ImageView) view.findViewById(R.id.ivPoster);
        txtGenre = (TextView) view.findViewById(R.id.txtGenre);
        txtRated = (TextView) view.findViewById(R.id.txtRated);
        txtTitleMovie = (TextView) view.findViewById(R.id.txtTitleMovie);
        txtYear = (TextView) view.findViewById(R.id.txtYear);
        alert = new Alert(getActivity());
        superMoviesController = new SuperMoviesController(getActivity());

        loadPreferencess();
        actions();

    }

    private void actions() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!edtTitle.getText().toString().equals("")) {
                    startTask();
                } else {
                    alert.exibirMensagem(getString(R.string.notice), getString(R.string.alert_title));
                }


            }
        });

    }


    private void startTask() {
        searchMovieTask = new SearchMovieTask();
        searchMovieTask.execute();
    }

    private class SearchMovieTask extends AsyncTask<Void, Void, ResponseMovie> {
        private ResponseMovie movieResponse = new ResponseMovie();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "aguarde",
                    "carregando");
            progressDialog.show();
        }


        @Override
        protected ResponseMovie doInBackground(Void... params) {
            try {
                return actionList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseMovie result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            try {
                if (result != null) {

                    if (movieResponse.getMovie().isResponse()) {
                        loadFields();
                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                                Alert.exibirMensagemConfirmacao(getActivity(), getActivity().getString(R.string.notice),
                                        getActivity().getString(R.string.msg_add),
                                        getActivity().getString(R.string.no),
                                        getActivity().getString(R.string.yes),
                                        true,
                                        new IAlertaConfirmacao() {
                                            @Override
                                            public void metodoPositivo(DialogInterface dialog,
                                                                       int id) {
                                                saveMovie(movie);

                                            }

                                            @Override
                                            public void metodoNegativo(DialogInterface dialog, int id) {
                                            }
                                        });


                            }
                        });

                    } else {

                        alert.exibirMensagem(getString(R.string.notice), getString(R.string.alert_movie));
                    }

                } else {
                    alert.exibirMensagem(getString(R.string.notice), getString(R.string.erro_servidor));

                }
            } catch (Exception e) {
                alert.exibirMensagem(getString(R.string.notice), getString(R.string.alert_movie));
            }
        }


        private ResponseMovie actionList() throws IOException {
            try {

                param = edtTitle.getText().toString();
                movieResponse = superMoviesController.searchMovie(getActivity(), param);
            } catch (Exception e) {
                Log.e(TAG, "Erro: " + e.getMessage());

            }
            return movieResponse;
        }


        private void loadFields() {


            Picasso.with(getActivity())
                    .load(movieResponse.getMovie().getPoster())
                    .into(ivPoster);


            txtTitleMovie.setText(movieResponse.getMovie().getTitle());
            txtRated.setText(movieResponse.getMovie().getPlot());
            txtYear.setText(movieResponse.getMovie().getYear());
            txtGenre.setText(movieResponse.getMovie().getGenre());

            btnSave.setVisibility(View.VISIBLE);
        }


        private void saveMovie(Movie movie) {
            MovieSqlite movieSqlite = new MovieSqlite(getActivity());

            movie = new Movie();

            try {
                movie.setTitle(movieResponse.getMovie().getTitle());
                movie.setPlot(movieResponse.getMovie().getPlot());
                movie.setYear(movieResponse.getMovie().getYear());
                movie.setRuntime(movieResponse.getMovie().getRuntime());
                movie.setGenre(movieResponse.getMovie().getGenre());
                movie.setActors(movieResponse.getMovie().getActors());
                movie.setDirector(movieResponse.getMovie().getDirector());
                movie.setLanguage(movieResponse.getMovie().getLanguage());
                movie.setPoster(movieResponse.getMovie().getPoster());
                movie.setIdFacebook(idFacebook.toString());

                movieSqlite.save(movie);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage().toString());
            }

        }

    }


    private void loadPreferencess() {
        try {
            sharedPreferences = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            idFacebook = sharedPreferences.getString("idFacebook", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }


}