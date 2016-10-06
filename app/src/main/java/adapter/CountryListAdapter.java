package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.BaseAdapter;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.my_travels.br.mytravels.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.sqlite.CountrySqlite;
import util.Alert;
import util.IAlertaConfirmacao;
import webservice.util.Country;
import webservice.util.ServiceUrl;


public class CountryListAdapter extends BaseAdapter {
    private static final String TAG = "MY TRAVELS";
    private String urlImage = ServiceUrl.getUrlImage();
    private String flag = "/flag";
    private String urlPicasso = "";
    private List<Country> countries;
    private int mSelectedItem = -1;
    private String idFacebook;

    private LinearLayout layoutGeral;
    private TextView txtCountryName;
    private Button btnAdd;
    private ImageView ivCountry;
    private LinearLayout linearLayout;
    private Alert alert;
    private final Activity activity;


    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences sharedPreferences;


    public CountryListAdapter(List<Country> countries, Activity activity) {
        super();
        this.activity = activity;
        alert = new Alert(activity);
        this.countries = countries;


    }


    private void startView(View tabelaLancamentosView) {


        ivCountry = (ImageView) tabelaLancamentosView.findViewById(R.id.ivCountry);
        txtCountryName = (TextView) tabelaLancamentosView.findViewById(R.id.txtCountryName);
        btnAdd = (Button) tabelaLancamentosView.findViewById(R.id.btnAdd);


        loadPreferencess();

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final View view = activity.getLayoutInflater().inflate(R.layout.country_item, null);
        final Country country = countries.get(position);

        try {
            if (!countries.isEmpty()) {

                startView(view);
                urlPicasso = urlImage + country.getId() + flag;

                Picasso.with(activity)
                        .load(urlPicasso)
                        .into(ivCountry);

                txtCountryName.setText(country.getShortname().toString());


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ListView) parent).performItemClick(v, position, 0);
                        Alert.exibirMensagemConfirmacao(activity, activity.getString(R.string.notice),
                                activity.getString(R.string.msg_add),
                                activity.getString(R.string.no),
                                activity.getString(R.string.yes),
                                true,
                                new IAlertaConfirmacao() {
                                    @Override
                                    public void metodoPositivo(DialogInterface dialog,
                                                               int id) {
                                        saveCountry(country);

                                    }

                                    @Override
                                    public void metodoNegativo(DialogInterface dialog, int id) {
                                    }
                                });
                    }
                });

            } else {
                alert.exibirMensagem(activity.getString(R.string.notice), activity.getString(R.string.data));
            }
        } catch (Exception e) {
            Log.e(TAG, "ERRO: " + e.getMessage());
        }

        return view;
    }

    private void loadPreferencess() {
        try {
            sharedPreferences = activity.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            idFacebook = sharedPreferences.getString("idFacebook", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

    private void saveCountry(Country country) {
        CountrySqlite contrySqliteSqlite = new CountrySqlite(activity);

        country.setId(country.getId());
        country.setShortname(country.getShortname().toString());
        country.setLongname(country.getLongname().toString());
        country.setIdFacebook(idFacebook.toLowerCase());
        country.setImageUrl(urlPicasso.toString());

        contrySqliteSqlite.save(country);
    }


    @Override
    public int getCount() {

        return countries.size();
    }

    @Override
    public Country getItem(int posicao) {
        return countries.get(posicao);
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
