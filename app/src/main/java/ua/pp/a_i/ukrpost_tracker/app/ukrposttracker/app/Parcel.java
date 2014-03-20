package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by em on 18.03.14.
 */
public class Parcel {
    private int Id;
    private String Name;
    private String Barcode;
    private String Status;
    private Date StatusDate;


    static ParcelsApp app=new ParcelsApp();
    ParcelDatabaseHelper helper=new ParcelDatabaseHelper();
    SQLiteDatabase db;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getStatusDate() {
        return StatusDate;
    }

    public void setStatusDate(Date statusDate) {
        StatusDate = statusDate;
    }

    public void InsertParcelToDb(Parcel parcel){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",parcel.Name);
        values.put("Barcode",parcel.Barcode);
        values.put("Status",parcel.Status);
        values.put("StatusDate",parcel.getStatusDate().toString());
        db.insert("Parcels",null,values);
        db.close();
    }


    public Parcel(int id, String name, String barcode, String status, Date statusDate) {
        Id = id;
        Name = name;
        Barcode = barcode;
        Status = status;
        StatusDate = statusDate;
    }

    class ParcelDatabaseHelper extends SQLiteOpenHelper{
        ParcelDatabaseHelper() {
            super(app.getContext(), "Parcels", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Parcels (Id NUMBER, Name TEXT, Barcode TEXT, Status TEXT, StatusUpdate DATE)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE Parcels");
            onCreate(db);
        }
    }







}
