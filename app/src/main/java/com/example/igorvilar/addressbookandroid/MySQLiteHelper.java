package com.example.igorvilar.addressbookandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorvilar on 14/07/16.
 */


public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "addressbook";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CONTACTS = "CONTACTS";

    // Post Table Columns
    private static final String KEY_CONTACTS_ID = "id";
    private static final String KEY_CONTACTS_NAME = "name";
    private static final String KEY_CONTACTS_ADDRESS = "address";
    private static final String KEY_CONTACTS_PHONE = "phone";


    //tag
    private static final String TAG = "MySQLiteHelper";

    private static MySQLiteHelper sInstance;

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MySQLiteHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                KEY_CONTACTS_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_CONTACTS_NAME + " TEXT," +
                KEY_CONTACTS_ADDRESS + " TEXT," +
                KEY_CONTACTS_PHONE + " TEXT" +
                ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }


    public void addOrUpdateContact(ContactBookModel contactBookModel) {
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).

            ContentValues values = new ContentValues();
            values.put(KEY_CONTACTS_NAME, contactBookModel.Name);
            values.put(KEY_CONTACTS_ADDRESS, contactBookModel.Address);
            values.put(KEY_CONTACTS_PHONE, contactBookModel.Phone);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_CONTACTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add contact to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all contacts in the database
    public List<ContactBookModel> getAllContacts() {
        List<ContactBookModel> contacts = new ArrayList<>();

        String CONTACT_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_CONTACTS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONTACT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ContactBookModel contactBookModel = new ContactBookModel();
                    contactBookModel.IdContact = cursor.getInt(cursor.getColumnIndex(KEY_CONTACTS_ID));
                    contactBookModel.Name = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_NAME));
                    contactBookModel.Address = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_ADDRESS));
                    contactBookModel.Phone = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_PHONE));
                    contacts.add(contactBookModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contacts;
    }

    public ContactBookModel retrieveSelectContact(int idSelectedContact) {
        ContactBookModel contactBookModel = new ContactBookModel();

        String CONTACT_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE ID=%d",
                        TABLE_CONTACTS, idSelectedContact);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONTACT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    contactBookModel.IdContact = cursor.getInt(cursor.getColumnIndex(KEY_CONTACTS_ID));
                    contactBookModel.Name = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_NAME));
                    contactBookModel.Address = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_ADDRESS));
                    contactBookModel.Phone = cursor.getString(cursor.getColumnIndex(KEY_CONTACTS_PHONE));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contactBookModel;
    }


    public int updateContactBook(ContactBookModel contactBookModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTACTS_NAME, contactBookModel.Name);
        values.put(KEY_CONTACTS_ADDRESS, contactBookModel.Address);
        values.put(KEY_CONTACTS_PHONE, contactBookModel.Phone);

        String[] args = new String[]{"" + contactBookModel.IdContact};
        return db.update(TABLE_CONTACTS, values, "id=?", args);
    }

    // Delete all posts and users in the database
    public int deleteSelectedContactsAddressBook(int IdSelected) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        return db.delete(TABLE_CONTACTS, KEY_CONTACTS_ID + "=" + IdSelected, null);
    }
}