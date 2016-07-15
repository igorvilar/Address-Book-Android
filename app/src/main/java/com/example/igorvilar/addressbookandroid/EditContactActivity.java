package com.example.igorvilar.addressbookandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContactActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextAddress;
    EditText editTextPhone;
    Button buttonDeteteContact;
    private int idContactSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        editTextName = (EditText) findViewById(R.id.editTextNameEdit);
        editTextAddress = (EditText) findViewById(R.id.editTextAddressEdit);
        editTextPhone = (EditText) findViewById(R.id.editTextPhoneEdit);
        buttonDeteteContact = (Button) findViewById(R.id.buttonDeteteContact);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        idContactSelect = b.getInt("idContactSelect");
        Log.d("Details", "idContactSelect: " + idContactSelect);


        ContactBookModel contactBookModel = new ContactBookModel();
        contactBookModel = MySQLiteHelper.getInstance(this).retrieveSelectContact(idContactSelect);
        editTextName.setText(contactBookModel.Name);
        editTextAddress.setText(contactBookModel.Address);
        editTextPhone.setText(contactBookModel.Phone);

        addListenerOnButtonDeleteContact();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveContact:
                Log.d("Log", "Save contact");
                ContactBookModel contactBookModel = new ContactBookModel();
                contactBookModel.Name = editTextName.getText().toString();
                contactBookModel.Address = editTextAddress.getText().toString();
                contactBookModel.Phone = editTextPhone.getText().toString();
                contactBookModel.IdContact = idContactSelect;
                MySQLiteHelper.getInstance(this).updateContactBook(contactBookModel);
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void addListenerOnButtonDeleteContact() {

        buttonDeteteContact = (Button) findViewById(R.id.buttonDeteteContact);
        buttonDeteteContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MySQLiteHelper.getInstance(EditContactActivity.this).deleteSelectedContactsAddressBook(idContactSelect);
                finish();
            }

        });

    }
}
