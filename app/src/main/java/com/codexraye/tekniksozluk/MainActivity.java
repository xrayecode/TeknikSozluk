package com.codexraye.tekniksozluk;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codexraye.tekniksozluk.lib.ContactListAdapter;
import com.codexraye.tekniksozluk.lib.DatabaseHelper;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends ActionBarActivity {
    private ProgressDialog progressDialog;
    public static boolean dbAc=false;
    ListView lvSearchList;
    SQLiteDatabase db;
    TextView edSearch;
    NodeList statements;

    ArrayList<Sozluk> contactList = new ArrayList<Sozluk>();
    public static String xYazilanKelime ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(db==null) db = (new DatabaseHelper(this)).getWritableDatabase();
        if(dbAc) {
            try {
                InputStream in = this.getResources().openRawResource(R.raw.sql);
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(in, null);
                statements = doc.getElementsByTagName("statement");
            } catch (Exception t) {
            }
            new LoadViewTask().execute(this);

        }


        lvSearchList = (ListView) findViewById(R.id.lSearch);
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
        registerClickCallback();

    }

    private void showlist(Cursor c1) {
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
                    contactListItems.setAciklama(c1.getString(c1.getColumnIndex("ACIKLAMA")));
                    contactList.add(contactListItems);

                } while (c1.moveToNext());
            }

        }
        if (c1!=null){
            c1.close();
        }

        ContactListAdapter contactListAdapter;
        contactListAdapter = new ContactListAdapter(MainActivity.this, contactList);
        lvSearchList.setAdapter(contactListAdapter);
    }

    public Cursor readAll(SQLiteDatabase db) {
        String sql="1=2";
        if(!edSearch.getText().toString().equals(""))
            sql=" KELIME like '%"+edSearch.getText().toString()+"%'";
        try {
            Cursor cursor = db.query("SOZLUK",
                    new String[] {"ID", "KELIME", "TURU","ACIKLAMA"}
                    ,sql , null, null, null, null);
            if (cursor.moveToNext()) {
                return cursor;
            }
            return null;
        } finally {
        }
    }

    private void registerClickCallback() {
        ListView list=(ListView) findViewById(R.id.lSearch);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(MainActivity.this, Detail_Activity.class);
                                            intent.putExtra("DET_KELIME", contactList.get(position).getKelime().toString());
                                            intent.putExtra("DET_ACIKLAMA", contactList.get(position).getAciklama().toString());
                                            startActivity(intent);
                                        }
                                    }

        );
    }

    private class LoadViewTask extends AsyncTask<Context, Integer, Void>
    {
        String s;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Yükleniyor...");
            progressDialog.setMessage("Lütfen programın hazırlanmasını bekleyiniz...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(statements.getLength());
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Context... params)
        {
            try
            {
                synchronized (this)
                {
                        for (int i=0; i<statements.getLength(); i++) {
                            s = statements.item(i).getChildNodes().item(0).getNodeValue();
                            db.execSQL(s);
                            this.wait(1);
                            publishProgress(i);
                        }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            progressDialog.dismiss();
        }
    }


}