package fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.my_travels.br.mytravels.R;

import java.util.ArrayList;
import java.util.List;

import adapter.VisitedCoutriesListAdapter;
import model.sqlite.CountrySqlite;
import webservice.util.Country;


public class VisitedCountriesFragment extends Fragment {

    private static final String TAG = "MY TRAVELS";

    private ListView lvCoutries;
    private VisitedCoutriesListAdapter visitedCoutriesListAdapter;
    private List<Country> countries = new ArrayList<Country>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visited_countries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        lvCoutries = (ListView) view.findViewById(R.id.lvCountries);
        loadList();
        actions();

    }

    private void loadList() {
        try {
            CountrySqlite countrySqlite = new CountrySqlite(this.getActivity());
            countries = countrySqlite.list();

            visitedCoutriesListAdapter = new VisitedCoutriesListAdapter(countries, this.getActivity());
            lvCoutries.setAdapter(visitedCoutriesListAdapter);
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