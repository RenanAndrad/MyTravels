package fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.super_movies.br.supermovies.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieListAdapter;
import model.sqlite.MovieSqlite;
import webservice.util.Movie;


public class SavedMoviesFragment extends Fragment {

    private static final String TAG = "Super Movies";

    private ListView lvCoutries;
    private MovieListAdapter movieListAdapter;
    private List<Movie> countries = new ArrayList<Movie>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        lvCoutries = (ListView) view.findViewById(R.id.lvCountries);
        loadList();
        actions();

    }

    private void loadList() {
        try {
            MovieSqlite movieSqlite = new MovieSqlite(this.getActivity());
            countries = movieSqlite.list();

            movieListAdapter = new MovieListAdapter(countries, this.getActivity());
            lvCoutries.setAdapter(movieListAdapter);
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

    private void actions() {

        lvCoutries.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }


}