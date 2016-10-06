package model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ManagerSqlite extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 6;


    public static final String DATABASE_NAME = "MyTravels.db";


    public static final String TABLE_COUNTRY = "TB_COUNTRY";


    public static final String TABLE_ID_COUNTRY = "id_country";
    public static final String TABLE_ISO = "iso";
    public static final String TABLE_SHORTNAME = "shortname";
    public static final String TABLE_LONGNAME = "longname";
    public static final String TABLE_CALLIN_GCODE = "callingCode";
    public static final String TABLE_STATUS = "status";
    public static final String TABLE_CULTURE = "culture";
    public static final String TABLE_START_DATE = "startDate";
    public static final String TABLE_END_DATE = "endDate";
    public static final String TABLE_IMAGE_URL = "imageUrl";
    public static final String TABLE_ID_FACEBOOK = "idFacebook";
    public static final String TABLE_RATING = "rating";


    public static final String CREATE_TABLE_COUNTRY =
            "Create Table " + TABLE_COUNTRY + " ( "
                    + TABLE_ID_COUNTRY + " integer primary key,"
                    + TABLE_ISO + " integer,"
                    + TABLE_SHORTNAME + " text,"
                    + TABLE_LONGNAME + " text,"
                    + TABLE_CALLIN_GCODE + " text,"
                    + TABLE_STATUS + " text, "
                    + TABLE_CULTURE + " text, "
                    + TABLE_START_DATE + " text, "
                    + TABLE_END_DATE + " text, "
                    + TABLE_IMAGE_URL + " text, "
                    + TABLE_ID_FACEBOOK + " text, "
                    + TABLE_RATING + " text "
                    + ");";


    public ManagerSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COUNTRY);
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY + ";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }
}
