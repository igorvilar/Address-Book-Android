package com.example.igorvilar.addressbookandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by igorvilar on 15/07/16.
 */
public class ContactBookAdapter extends ArrayAdapter<ContactBookModel> {

    List<ContactBookModel> contactBookModelList;
    Context context;

    public ContactBookAdapter(Context context, int item_list_contact_layout, List<ContactBookModel> activeSectionModelList, ListView contactBookListView) {
        super(context, R.layout.item_list_contact_layout, activeSectionModelList);
        this.context = context;
        this.contactBookModelList = activeSectionModelList;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_list_contact_layout, parent, false);
        TextView nameContact = (TextView) convertView.findViewById(R.id.name_item_simple_list_text);

        nameContact.setText(contactBookModelList.get(position).Name);

        return convertView;
    }
}
