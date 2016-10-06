package webservice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.domain.ResponseCountries;
import webservice.util.Country;
import webservice.util.ServiceUrl;


public class ListCountriesWs {

    private static final String METHOD_NAME = "";
    private static final String TAG = "MY TRAVELS";
    private static final String url = ServiceUrl.getUrlWsdl();
    private Activity activity;
    private String urlGet = url + METHOD_NAME;
    private int TimeOut = 60000;
    private JSONObject returnObj;
    private List<Country> countries = new ArrayList<Country>();
    private boolean finished = false;

    public ListCountriesWs() {

    }

    public ListCountriesWs(RequestQueue queue, Activity activity) {
        super();
        this.activity = activity;
    }


    public ResponseCountries listCountries(RequestQueue queue, Context context) throws IOException {

        final ResponseCountries responseCountries = new ResponseCountries();
        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject itemCountry = response.getJSONObject(i);

                                Country country = new Country();

                                if (itemCountry.getString("id") != null) {
                                    country.setId(Integer.parseInt(itemCountry.getString("id")));
                                }
                                if (itemCountry.getString("iso") != null) {
                                    country.setIso(itemCountry.getString("iso"));
                                }
                                if (itemCountry.getString("shortname") != null) {
                                    country.setShortname(itemCountry.getString("shortname"));
                                }
                                if (itemCountry.getString("longname") != null) {
                                    country.setLongname(itemCountry.getString("longname"));
                                }
                                if (itemCountry.getString("callingCode") != null) {
                                    country.setCallingCode(itemCountry.getString("callingCode"));
                                }
                                if (itemCountry.getString("status") != null) {
                                    country.setStatus(Integer.parseInt(itemCountry.getString("status")));
                                }
                                if (itemCountry.getString("culture") != null) {
                                    country.setCulture(itemCountry.getString("culture"));
                                }

                                countries.add(country);


                                responseCountries.setCountryList(countries);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        finished = true;

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
        return responseCountries;
    }


}
