package fragment;


import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.my_travels.br.mytravels.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.VisitedCoutriesListAdapter;
import model.sqlite.CountrySqlite;
import util.Alert;
import util.DatePickerFragment;
import util.IAlertaConfirmacao;
import webservice.util.Country;
import webservice.util.ServiceUrl;


public class VisitedCountriesInfoFragment extends Fragment {

    private static final String TAG = "MY TRAVELS";

    private ImageView ivFlag;
    private TextView txtLongName;
    private EditText edtStartDate, edtEndDate;
    private Button btnSave, btDelete;
    private RatingBar ratingBar;
    private Alert alert;
    private FragmentTransaction fragmentTransaction;

    private VisitedCoutriesListAdapter visitedCoutriesListAdapter;
    private String urlImage = ServiceUrl.getUrlImage();
    private String flag = "/flag";
    private String urlPicasso = "";
    private String rating;

    private List<Country> countries = new ArrayList<Country>();
    private Bundle args = new Bundle();
    private Country country;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_visited_countries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        alert = new Alert(getActivity());

        ivFlag = (ImageView) view.findViewById(R.id.ivFlag);
        txtLongName = (TextView) view.findViewById(R.id.txtLongName);
        edtStartDate = (EditText) view.findViewById(R.id.edtStartDate);
        edtStartDate.setKeyListener(null);
        edtEndDate = (EditText) view.findViewById(R.id.edtEndDate);
        edtEndDate.setKeyListener(null);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btDelete = (Button) view.findViewById(R.id.btDelete);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);


        Bundle bundle = getArguments();
        country = (Country) bundle.getSerializable("country");

        showVisitedCountries();
        actions();


    }

    private void showVisitedCountries() {
        try {
            txtLongName.setText(country.getLongname().toString());

            CountrySqlite countrySqlite = new CountrySqlite(getActivity());

            if (countrySqlite.listCountryRating(country) != null) {
                rating = countrySqlite.listCountryRating(country);
            }

            urlPicasso = urlImage + country.getId() + flag;
            Picasso.with(this.getActivity())
                    .load(urlPicasso)
                    .into(ivFlag);

            if (country.getStartDate() != null) {
                edtStartDate.setText(country.getStartDate().toString());
            }
            if (country.getEndDate() != null) {
                edtEndDate.setText(country.getEndDate().toString());
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
                        getString(R.string.msg_out),
                        getString(R.string.no),
                        getString(R.string.yes),
                        true,
                        new IAlertaConfirmacao() {
                            @Override
                            public void metodoPositivo(DialogInterface dialog,
                                                       int id) {
                                CountrySqlite contrySqliteSqlite = new CountrySqlite(getActivity());

                                contrySqliteSqlite.delete(country);

                                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, new VisitedCountriesFragment());
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

                if (datavalidate()) {

                    rating = String.valueOf(ratingBar.getRating());
                    CountrySqlite contrySqliteSqlite = new CountrySqlite(getActivity());

                    country.setStartDate(edtStartDate.getText().toString());
                    country.setEndDate(edtEndDate.getText().toString());
                    country.setRating(rating);

                    contrySqliteSqlite.update(country);

                    alert.exibirMensagem(getString(R.string.notice), getString(R.string.save_success));
                }

            }
        });
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerStartDate(view);

            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerEndDate(view);

            }
        });


    }

    private boolean datavalidate() {
        boolean dadosValidos = true;

        if (edtStartDate.getText().toString().equals("") || edtStartDate.getText().toString().equals("")) {
            alert.exibirMensagem(getString(R.string.notice), getString(R.string.date_invalid));
            dadosValidos = false;
        }
        return dadosValidos;
    }

    private void showDatePickerStartDate(View v) {
        DatePickerFragment dateFragment = new DatePickerFragment(edtStartDate);
        dateFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    private void showDatePickerEndDate(View v) {
        DatePickerFragment dateFragment = new DatePickerFragment(edtEndDate);
        dateFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


}