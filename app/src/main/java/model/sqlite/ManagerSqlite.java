package model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ManagerSqlite extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 3;


    public static final String DATABASE_NAME = "SuperMovies.db";


    public static final String TABLE_MOVIE = "TB_MOVIE";


    public static final String TABLE_ID_MOVIE= "idmovie";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_PLOT = "plot";
    public static final String TABLE_YEAR = "year";
    public static final String TABLE_RUNTIME = "runtime";
    public static final String TABLE_GENRE = "genre";
    public static final String TABLE_DIRECTOR = "director";
    public static final String TABLE_ACTORS = "actors";
    public static final String TABLE_LANGUAGE = "language";
    public static final String TABLE_POSTER = "poster";
    public static final String TABLE_ID_FACEBOOK = "idFacebook";
    public static final String TABLE_RATING = "rating";


    public static final String CREATE_TABLE_COUNTRY =
            "Create Table " + TABLE_MOVIE + " ( "
                    + TABLE_ID_MOVIE + " integer primary key autoincrement,"
                    + TABLE_TITLE + " integer,"
                    + TABLE_PLOT + " text,"
                    + TABLE_YEAR + " text,"
                    + TABLE_RUNTIME + " text,"
                    + TABLE_GENRE + " text, "
                    + TABLE_DIRECTOR + " text, "
                    + TABLE_ACTORS + " text, "
                    + TABLE_LANGUAGE + " text, "
                    + TABLE_POSTER + " text, "
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE + ";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }
}
