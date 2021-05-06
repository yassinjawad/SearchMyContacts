package com.example.searchmycontacts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract

class DBHandler(context: Context?, cursorFactory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION){

    /**
     * Creates database table
     * @param db reference to the mycontactssearchapp database
     */
    override fun onCreate(db: SQLiteDatabase) {

        val query = "CREATE TABLE " + TABLE_CONTACT + "(" +
                COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_CONTACT_EMAIL + " TEXT, " +
                COLUMN_CONTACT_CONTACT_GROUP + " TEXT);"

        db.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        val query = "DROP TABLE IF EXISTS " + TABLE_CONTACT

        db.execSQL(query)

        onCreate(db)
    }


    fun addContact(name: String?, email: String?, contact_group: String?){

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_CONTACT_NAME, name)
        values.put(COLUMN_CONTACT_EMAIL, email)
        values.put(COLUMN_CONTACT_CONTACT_GROUP, contact_group)

        db.insert(TABLE_CONTACT, null, values)

        // close database connection
        db.close()
    }


    fun search(key: String, value: String?) : MutableList<Contacts> {
        val db = writableDatabase

        var query = ""

        if (key.equals("group_contact")) {
            query = "SELECT * FROM " + TABLE_CONTACT +
                    " WHERE " + COLUMN_CONTACT_CONTACT_GROUP + " = " + "'" + value + "'"
        } else {
            query = "SELECT * FROM " + TABLE_CONTACT +
                    " WHERE " + COLUMN_CONTACT_EMAIL + " = " + "'" + value + "'"
        }

        val c = db.rawQuery(query, null)

        val list: MutableList<Contacts> = ArrayList()

        while (c.moveToNext()) {

            val contact: Contacts = Contacts(c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("group_contact")),
                c.getString(c.getColumnIndex("email")));
            list.add(contact)
        }

        db.close()

        return list
    }

    companion object {

        private const val DATABASE_NAME = "mycontactssearchapp.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CONTACT = "contact"
        private const val COLUMN_CONTACT_ID = "_id"
        private const val COLUMN_CONTACT_NAME = "name"
        private const val COLUMN_CONTACT_CONTACT_GROUP = "group_contact"
        private const val COLUMN_CONTACT_EMAIL = "email"
    }
}