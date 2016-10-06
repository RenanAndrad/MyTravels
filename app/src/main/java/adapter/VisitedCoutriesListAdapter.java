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

import com.my_travels.br.mytravels.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import fragment.VisitedCountriesInfoFragment;
import util.Alert;
import webservice.util.Country;
import webservice.util.ServiceUrl;


public class VisitedCoutriesListAdapter extends BaseAdapter {
    private static final String TAG = "MY TRAVELS";
    private String urlImage = ServiceUrl.getUrlImage();
    private String flag = "/flag";
    private String urlPicasso = "";
    private final List<Country> countries;
    private final Activity activity;
    private int mSelectedItem = -1;

    private TextView txtCountryName;
    private Button btnAdd;
    private ImageView ivCountry;
    private FragmentTransaction fragmentTransaction;
    private Alert alert;

    private Bundle args = new Bundle();


    public VisitedCoutriesListAdapter(List<Country> countries, Activity activity) {
        super();
        this.activity = activity;
        alert = new Alert(activity);
        this.countries = countries;

    }


    private void startView(View tabelaLancamentosView) {


        ivCountry = (ImageView) tabelaLancamentosView.findViewById(R.id.ivCountry);
        txtCountryName = (TextView) tabelaLancamentosView.findViewById(R.id.txtCountryName);
        btnAdd = (Button) tabelaLancamentosView.findViewById(R.id.btnAdd);

        btnAdd.setBackgroundResource(R.drawable.ic_info);

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
                        Log.e(TAG, "Press button");
                        ((ListView) parent).performItemClick(v, position, 0);

                        args.putSerializable("country", country);

                        VisitedCountriesInfoFragment mFragment_B = new VisitedCountriesInfoFragment();
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
