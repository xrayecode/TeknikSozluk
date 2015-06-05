package com.codexraye.tekniksozluk.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codexraye.tekniksozluk.R;
import com.codexraye.tekniksozluk.Sozluk;

import java.util.ArrayList;

/**
 * Created by akkaraman on 4.6.2015.
 */
public class ContactListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sozluk> contactList;

    public ContactListAdapter(Context context, ArrayList<Sozluk> list) {

        this.context = context;
        contactList = list;
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Object getItem(int position) {

        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Sozluk contactListItems = contactList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_list_row, null);

        }

        TextView xId = (TextView) convertView.findViewById(R.id.edId);
        xId.setText(contactListItems.getId());
        TextView xKelime = (TextView) convertView.findViewById(R.id.edKelime);
        xKelime.setText(contactListItems.getKelime());
        TextView xTuru = (TextView) convertView.findViewById(R.id.edTuru);
        xTuru.setText(contactListItems.getTuru());
        /*TextView xAciklama = (TextView) convertView.findViewById(R.id.edAciklama);
        xAciklama.setText(contactListItems.getAciklama();
        */

        return convertView;
    }
}
