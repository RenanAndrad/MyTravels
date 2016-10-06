package model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import webservice.util.Country;

public class CountrySqlite {

    private static final String TAG = "MY TRAVELS";
    private SQLiteDatabase database;
    private ManagerSqlite managerSqlite;
    private Context context;
    private SharedPreferences sharedPreferences;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private String idFacebook;

    private String[] colunTableCountry = {
            managerSqlite.TABLE_ID_COUNTRY,
            managerSqlite.TABLE_ISO,
            managerSqlite.TABLE_SHORTNAME,
            managerSqlite.TABLE_LONGNAME,
            managerSqlite.TABLE_CALLIN_GCODE,
            managerSqlite.TABLE_STATUS,
            managerSqlite.TABLE_CULTURE,
            managerSqlite.TABLE_START_DATE,
            managerSqlite.TABLE_END_DATE,
            managerSqlite.TABLE_IMAGE_URL,
            managerSqlite.TABLE_ID_FACEBOOK,
            managerSqlite.TABLE_RATING};


    public CountrySqlite(Context context) {
        managerSqlite = new ManagerSqlite(context);
        this.context = context;
        loadPreferencess();
    }

    public void open() {
        database = managerSqlite.getWritableDatabase();
    }

    public void close() {
        managerSqlite.close();
    }


    public List<Country> list() {
        List<Country> countries = new ArrayList<Country>();

        String selectQuery = "select * from "
                + managerSqlite.TABLE_COUNTRY + " where " + colunTableCountry[10] + " = " + idFacebook;

        open();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            while (cursor.moveToNext()) {
                Country country = new Country();

                country.setId(cursor.getInt(cursor.getColumnIndex(managerSqlite.TABLE_ID_COUNTRY)));
                country.setIso(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_ISO)));
                country.setShortname(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_SHORTNAME)));
                country.setLongname(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_LONGNAME)));
                country.setCallingCode(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_CALLIN_GCODE)));
                country.setStatus(cursor.getInt(cursor.getColumnIndex(managerSqlite.TABLE_STATUS)));
                country.setCulture(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_CULTURE)));

                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_START_DATE)) != null) {
                    country.setStartDate(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_START_DATE)));
                }
                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_END_DATE)) != null) {
                    country.setEndDate(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_END_DATE)));
                }
                country.setImageUrl(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_IMAGE_URL)));
                country.setIdFacebook(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_ID_FACEBOOK)));

                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)) != null) {
                    country.setRating(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)));
                }

                countries.add(country);
            }
        }


        cursor.close();

        return countries;
    }

    public String listCountryRating(Country country) {

        String selectQuery = "select * from "
                + managerSqlite.TABLE_COUNTRY + " where " + colunTableCountry[10] + " = " + idFacebook + " and " + colunTableCountry[0] + " = " + country.getId();

        open();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)) != null) {
                    country.setRating(cursor.getString(cursor.getColumnIndex(managerSqlite.TABLE_RATING)));
                }

            }
        }

        cursor.close();

        return country.getRating();
    }

    public long save(Country country) {
        open();
        ContentValues values = new ContentValues();


        values.put(colunTableCountry[0], country.getId());
        values.put(colunTableCountry[2], country.getShortname());
        values.put(colunTableCountry[3], country.getLongname());
        values.put(colunTableCountry[9], country.getImageUrl().toString());
        values.put(colunTableCountry[10], country.getIdFacebook().toString());


        long result = -1;
        try {
            result = database.insert(managerSqlite.TABLE_COUNTRY,
                    null, values);
        } catch (SQLiteConstraintException e) {
            result = -1;
        } catch (Exception e) {
            result = -1;
        }

        close();
        return result;
    }

    public long update(Country country) {
        ContentValues values = new ContentValues();
        values.put(colunTableCountry[7], country.getStartDate().toString());
        values.put(colunTableCountry[8], country.getEndDate().toString());
        if (country.getRating() != null) {
            values.put(colunTableCountry[11], country.getRating().toString());
        }

        String where = colunTableCountry[0] + "=" + country.getId() + " and " + colunTableCountry[10] + " = " + idFacebook;
        database = managerSqlite.getReadableDatabase();

        long result = database.update(managerSqlite.TABLE_COUNTRY, values, where, null);
        Log.d("Update Result:", "=" + result);
        return result;

    }


    public long delete(Country country) {


        String where = colunTableCountry[0] + "=" + country.getId() + " and " + colunTableCountry[10] + " = " + idFacebook;
        database = managerSqlite.getReadableDatabase();
        long result = database.delete(managerSqlite.TABLE_COUNTRY, where, null);
        close();

        return result;
    }

    private void loadPreferencess() {
        try {
            sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            idFacebook = sharedPreferences.getString("idFacebook", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }
}
