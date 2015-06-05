package com.codexraye.tekniksozluk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.codexraye.tekniksozluk.lib.ContactListAdapter;
import com.codexraye.tekniksozluk.lib.DatabaseHelper;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView lvSearchList;
    Button ara;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSearchList = (ListView) findViewById(R.id.lSearch);

        db = (new DatabaseHelper(this)).getWritableDatabase();
        if (db==null){
            Toast.makeText(this, "Bağlanamadı", 2000).show();
        }
        ara= (Button) findViewById(R.id.bAra);
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlist(readAll(db));
            }
        });
    }
    public Cursor readAll(SQLiteDatabase db) {
        try {
            Cursor cursor = db.query("SOZLUK",
                    new String[] {"ID", "KELIME", "TURU","ACIKLAMA"}
                    , null, null, null, null, null);
            if (cursor.moveToNext()) {
                return cursor;
            }
            return null;
        } finally {
        }

    }
    private void showlist(Cursor c1) {

        ArrayList<Sozluk> contactList = new ArrayList<Sozluk>();
        contactList.clear();
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Sozluk contactListItems = new Sozluk();

                    contactListItems.setId(c1.getString(c1
                            .getColumnIndex("ID")));
                    contactListItems.setKelime(c1.getString(c1
                            .getColumnIndex("KELIME")));
                    contactListItems.setTuru(c1.getString(c1
                            .getColumnIndex("TURU")));
                    contactList.add(contactListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();

        ContactListAdapter contactListAdapter;
        contactListAdapter = new ContactListAdapter(MainActivity.this, contactList);
        lvSearchList.setAdapter(contactListAdapter);

    }


}
