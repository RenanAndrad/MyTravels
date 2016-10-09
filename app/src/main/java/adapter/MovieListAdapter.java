package adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.super_movies.br.supermovies.R;

import java.util.List;

import fragment.SavedMoviesInfoFragment;
import util.Alert;
import webservice.util.Movie;


public class MovieListAdapter extends BaseAdapter {
    private static final String TAG = "Super Movies";
    private String urlPicasso = "";
    private final List<Movie> movies;
    private final Activity activity;
    private int mSelectedItem = -1;

    private TextView txtMovieName;
    private Button btnAdd;
    private ImageView ivMovie;
    private FragmentTransaction fragmentTransaction;
    private Alert alert;

    private Bundle args = new Bundle();


    public MovieListAdapter(List<Movie> movies, Activity activity) {
        super();
        this.activity = activity;
        alert = new Alert(activity);
        this.movies = movies;

    }


    private void startView(View tavleView) {


        ivMovie = (ImageView) tavleView.findViewById(R.id.ivMovie);
        txtMovieName = (TextView) tavleView.findViewById(R.id.txtMovieName);
        btnAdd = (Button) tavleView.findViewById(R.id.btnAdd);

        btnAdd.setBackgroundResource(R.drawable.ic_info);

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final View view = activity.getLayoutInflater().inflate(R.layout.movie_item, null);
        final Movie movie = movies.get(position);

        try {
            if (!movies.isEmpty()) {

                startView(view);

                urlPicasso = movie.getPoster().toString();

                Picasso.with(activity)
                        .load(urlPicasso)
                        .into(ivMovie);

                txtMovieName.setText(movie.getTitle().toString());


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ListView) parent).performItemClick(v, position, 0);

                        args.putSerializable("movie", movie);

                        SavedMoviesInfoFragment mFragment_B = new SavedMoviesInfoFragment();
                        mFragment_B.setArguments(args);

                        fragmentTransaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, mFragment_B);
                        fragmentTransaction.commit();

                    }
                });

            } else {
                alert.exibirMensagem(activity.getString(R.string.notice), activity.getString(R.string.notice_visited_country));
            }
        } catch (Exception e) {
            Log.e(TAG, "ERRO: " + e.getMessage());
        }

        return view;
    }


    @Override
    public int getCount() {

        return movies.size();
    }

    @Override
    public Movie getItem(int posicao) {
        return movies.get(posicao);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public int getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }
}
