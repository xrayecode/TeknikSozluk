package com.codexraye.tekniksozluk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Detail_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Intent intent = getIntent();
        TextView xkelime = (TextView) findViewById(R.id.tvKelime);
        TextView xaciklama= (TextView) findViewById(R.id.tvAciklama);

        xkelime.setText(intent.getStringExtra("DET_KELIME"));
        xaciklama.setText(intent.getStringExtra("DET_ACIKLAMA"));
    }


}
