package model.dao;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import model.domain.ResponseCountries;
import webservice.ListCountriesWs;


public class MyTravelsDAO {

    public ResponseCountries listCountries(RequestQueue queue,Context context) throws IOException, XmlPullParserException {
        ListCountriesWs listCountriesWs = new ListCountriesWs();
        return listCountriesWs.listCountries(queue,context);
    }

}
