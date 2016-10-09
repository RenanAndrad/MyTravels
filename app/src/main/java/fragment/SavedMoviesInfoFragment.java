package fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.super_movies.br.supermovies.R;

import java.util.ArrayList;
import java.util.List;

import model.sqlite.MovieSqlite;
import util.Alert;
import util.IAlertaConfirmacao;
import webservice.util.Movie;


public class SavedMoviesInfoFragment extends Fragment {

    private static final String TAG = "Super Movies";

    private ImageView ivFlag;
    private TextView txtTitle, txtPlot, txtActores, txtDirectores, txtRuntime, txtLanguage, txtYear, txtGenre;
    private Button btnSave, btDelete;
    private RatingBar ratingBar;
    private Alert alert;
    private FragmentTransaction fragmentTransaction;

    private String urlPicasso = "";
    private String rating;
    private Movie movie;

    private List<Movie> movies = new ArrayList<Movie>();
    private Bundle args = new Bundle();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_saved_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        alert = new Alert(getActivity());

        ivFlag = (ImageView) view.findViewById(R.id.ivFlag);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtPlot = (TextView) view.findViewById(R.id.txtPlot);
        txtActores = (TextView) view.findViewById(R.id.txtActores);
        txtDirectores = (TextView) view.findViewById(R.id.txtDirectores);
        txtRuntime = (TextView) view.findViewById(R.id.txtRuntime);
        txtLanguage = (TextView) view.findViewById(R.id.txtLanguage);
        txtYear = (TextView) view.findViewById(R.id.txtYear);
        txtGenre = (TextView) view.findViewById(R.id.txtGenre);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btDelete = (Button) view.findViewById(R.id.btnDelete);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);


        Bundle bundle = getArguments();
        movie = (Movie) bundle.getSerializable("movie");

        showVisitedCountries();
        actions();


    }

    private void showVisitedCountries() {
        try {
            txtTitle.setText(movie.getTitle().toString());

            txtPlot.setText("Sinopse: " + movie.getPlot().toString());
            txtActores.setText("Atores: " + movie.getActors().toString());
            txtDirectores.setText("Direção: " + movie.getDirector().toString());
            txtRuntime.setText("Duração: " + movie.getRuntime().toString());
            txtLanguage.setText("Linguagens: " + movie.getLanguage().toString());
            txtYear.setText("Ano: " + movie.getYear().toString());
            txtGenre.setText("Gênero: " + movie.getGenre().toString());

            urlPicasso = movie.getPoster().toString();
            Picasso.with(this.getActivity())
                    .load(urlPicasso)
                    .into(ivFlag);


            MovieSqlite movieSqlite = new MovieSqlite(getActivity());

            if (movieSqlite.listMovieRating(movie) != null) {
                rating = movieSqlite.listMovieRating(movie);
            }

            if (rating != null) {
                ratingBar.setRating(Float.parseFloat(rating));
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

    private void actions() {


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                alert.exibirMensagemConfirmacao(getActivity(), getString(R.string.notice),
                        getString(R.string.msg_delete),
                        getString(R.string.no),
                        getString(R.string.yes),
                        true,
                        new IAlertaConfirmacao() {
                            @Override
                            public void metodoPositivo(DialogInterface dialog,
                                                       int id) {
                                MovieSqlite movieSqlite = new MovieSqlite(getActivity());

                                movieSqlite.delete(movie);

                                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, new SavedMoviesFragment());
                                fragmentTransaction.commit();
                            }

                            @Override
                            public void metodoNegativo(DialogInterface dialog, int id) {
                            }
                        });


            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                rating = String.valueOf(ratingBar.getRating());
                MovieSqlite movieSqlite = new MovieSqlite(getActivity());

                movie.setRating(rating);

                movieSqlite.update(movie);

                alert.exibirMensagem(getString(R.string.notice), getString(R.string.save_success));

            }
        });


    }


}