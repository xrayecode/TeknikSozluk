package com.codexraye.tekniksozluk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.codexraye.tekniksozluk.lib.ContactListAdapter;
import com.codexraye.tekniksozluk.lib.DatabaseHelper;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView lvSearchList;
    Button ara;
    SQLiteDatabase db;
    TextView edSearch;
    public static String xYazilanKelime ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSearchList = (ListView) findViewById(R.id.lSearch);
        if(db==null) db = (new DatabaseHelper(this)).getWritableDatabase();
        edSearch = (TextView) findViewById(R.id.edSearch);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                xYazilanKelime=edSearch.getText().toString();
                showlist(readAll(db));
            }
        });

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
            ContactListAdapter contactListAdapter;
            contactListAdapter = new ContactListAdapter(MainActivity.this, contactList);
            lvSearchList.setAdapter(contactListAdapter);
        }
        c1.close();


    }

    public Cursor readAll(SQLiteDatabase db) {
        try {
            Cursor cursor = db.query("SOZLUK",
                    new String[] {"ID", "KELIME", "TURU","ACIKLAMA"}
                    , " KELIME like '%"+edSearch.getText().toString()+"%'", null, null, null, null);
            if (cursor.moveToNext()) {
                return cursor;
            }
            return null;
        } finally {
        }
    }

}
