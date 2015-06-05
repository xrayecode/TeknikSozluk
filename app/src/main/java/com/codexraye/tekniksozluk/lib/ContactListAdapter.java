package com.codexraye.tekniksozluk.lib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codexraye.tekniksozluk.MainActivity;
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

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/DoppioOne.ttf");
        xKelime.setTypeface(typeface);

        setColor(xKelime,contactListItems.getKelime(),MainActivity.xYazilanKelime.toString(), Color.RED);
        return convertView;
    }
    private void setColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i!=-1)
            str.setSpan(new ForegroundColorSpan(color), i, i+subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
