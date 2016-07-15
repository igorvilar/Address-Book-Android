package com.example.igorvilar.addressbookandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView contactBookListView;
    ContactBookAdapter contactBookAdapter;
    List<ContactBookModel> listContactBookModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contatos");
        contactBookListView = (ListView) findViewById(R.id.listViewContactBook);

        contactBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailsContactActivity.class);
                Bundle b = new Bundle();
                b.putInt("idContactSelect", listContactBookModel.get(position).IdContact);
                intent.putExtras(b);
                startActivity(intent);
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listContactBookModel = MySQLiteHelper.getInstance(this).getAllContacts();
        listViewPopulate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addContact:

                Log.d("Log", "add contact");
                Intent intent = new Intent(this, AddContactActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void listViewPopulate() {
        contactBookAdapter = new ContactBookAdapter(this, R.layout.item_list_contact_layout, listContactBookModel, contactBookListView);

        contactBookListView.setAdapter(contactBookAdapter);

    }

}
