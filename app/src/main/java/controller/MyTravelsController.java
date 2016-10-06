package controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;

import model.dao.MyTravelsDAO;
import model.domain.ResponseCountries;


public class MyTravelsController {

    private MyTravelsDAO myTravelsDAO;
    private static final String TAG = "MY TRAVELS";
    private Context context;


    public MyTravelsController(Context context) {
        super();
        myTravelsDAO = new MyTravelsDAO();
        this.context = context;
    }

    public ResponseCountries listCountries(RequestQueue queue,Context context) {
        ResponseCountries responseCountries = new ResponseCountries();

        try {
            responseCountries = myTravelsDAO.listCountries(queue,context);
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }

        return responseCountries;
    }



}
