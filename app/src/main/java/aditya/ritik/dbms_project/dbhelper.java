package aditya.ritik.dbms_project;
/**
 * Created by Ritik Srivastava on 02-05-2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
public class dbhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "seats.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_TABLEID="tid";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_TIME = "time";
    public static final String CONTACTS_COLUMN_DATE = "date";
    private HashMap hp;
    public dbhelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, tid integer,name text,email text, time text ,date text )"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
    public void insertContact (Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tid",contact.getid());
        contentValues.put("name", contact.getname());
        contentValues.put("email", contact.getemail());
        contentValues.put("time", contact.gettime());
        contentValues.put("date", contact.getdate());
        db.insert("contacts", null, contentValues);
    }
    public int getData(int tid,String date,String time) {
        int i=1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONTACTS_TABLE_NAME+" where tid='"+tid+"'AND date='"+date+"'", null );
        if(res.getCount()!=0){
            i=0;
        }
        return i;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}

