package com.codexraye.tekniksozluk.lib;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.codexraye.tekniksozluk.MainActivity;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TEKNIKSOZLUK";

    protected Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,20);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s =
        "CREATE TABLE IF NOT EXISTS SOZLUK ( "+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
        "TURU  VARCHAR(50), "+
         "       KELIME VARCHAR(500), "+
          "      ACIKLAMA TEXT) ";
        db.execSQL(s);
        MainActivity.dbAc=true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SOZLUK");
        onCreate(db);
    }

}