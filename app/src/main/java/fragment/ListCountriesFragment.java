package fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.my_travels.br.mytravels.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.CountryListAdapter;
import adapter.VisitedCoutriesListAdapter;
import controller.MyTravelsController;
import model.domain.ResponseCountries;
import model.sqlite.CountrySqlite;
import util.Alert;
import webservice.util.Country;


public class ListCountriesFragment extends Fragment {


    private static final String TAG = "MY TRAVELS";

    private ListView lvCoutries;
    private ProgressDialog progressDialog;
    private Alert alert;

    private VisitedCoutriesListAdapter visitedCoutriesListAdapter;
    private List<Country> countries = new ArrayList<Country>();

    private MyTravelsController myTravelsController;
    private ListCrountriesTask istCrountriesTask;
    private ResponseCountries responseCountries = new ResponseCountries();
    private CountryListAdapter countryListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_countries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        lvCoutries = (ListView) view.findViewById(R.id.lvCountries);
        alert = new Alert(getActivity());
        myTravelsController = new MyTravelsController(getActivity());

        loadList();
        startTask();

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

    private void startTask() {
        istCrountriesTask = new ListCrountriesTask();
        istCrountriesTask.execute();
    }

    private class ListCrountriesTask extends AsyncTask<Void, Void, ResponseCountries> {
        private ResponseCountries countriesResponse = new ResponseCountries();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "aguarde",
                    "carregando");
            progressDialog.show();
        }


        @Override
        protected ResponseCountries doInBackground(Void... params) {
            try {
                return actionList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseCountries result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            try {
                if (result != null) {

                    if (countriesResponse.getCountryList() != null) {
                        countryListAdapter = new CountryListAdapter(countriesResponse.getCountryList(), getActivity());
                        lvCoutries.setAdapter(countryListAdapter);

                    } else {

                        alert.exibirMensagem(getString(R.string.notice), getString(R.string.listEmpty));
                    }
                } else {
                    alert.exibirMensagem(getString(R.string.notice), getString(R.string.erro_servidor));

                }
            } catch (Exception e) {
                alert.exibirMensagem(getString(R.string.notice), getString(R.string.erro_rede));
            }
        }


        private ResponseCountries actionList() throws IOException {
            try {
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                countriesResponse = myTravelsController.listCountries(queue, getActivity());
            } catch (Exception e) {
                Log.e(TAG, "Erro: " + e.getMessage());

            }
            return countriesResponse;
        }
    }


}