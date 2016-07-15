package com.example.igorvilar.addressbookandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsContactActivity extends AppCompatActivity {
    private int idContactSelect;
    private ContactBookModel contactBookModel;
    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);
        setTitle("Detalhes");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        idContactSelect = b.getInt("idContactSelect");
        Log.d("Details", "idContactSelect: " + idContactSelect);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);

    }

    @Override
    protected void onResume() {
        super.onResume();
        contactBookModel = new ContactBookModel();
        contactBookModel = MySQLiteHelper.getInstance(this).retrieveSelectContact(idContactSelect);
        textViewName.setText(contactBookModel.Name);
        textViewAddress.setText(contactBookModel.Address);
        textViewPhone.setText(contactBookModel.Phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editContact:

                Log.d("Log", "Edit contact");
                Intent intent = new Intent(this, EditContactActivity.class);
                Bundle b = new Bundle();
                b.putInt("idContactSelect", idContactSelect);
                intent.putExtras(b);
                startActivity(intent);

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
