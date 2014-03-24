package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static ParcelDatabaseHelper helper=new ParcelDatabaseHelper();
    private static SQLiteDatabase db;


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

    public static void insertParcelToDb(Parcel parcel){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",parcel.Name);
        values.put("Barcode",parcel.Barcode);
        values.put("Status",parcel.Status);
        values.put("StatusDate",parcel.getStatusDate().toString());
        db.insert("Parcels",null,values);
        db.close();
    }

    public static ArrayList<Parcel> getParcelsFromDb(){
        ArrayList<Parcel> parcels=new ArrayList<>();
        db=helper.getReadableDatabase();
        Cursor cursor=db.query("Parcels",new String[]{"Id","Name","Barcode","Status","StatusDate"},"",null,"","","");
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Boolean end=cursor.moveToFirst();
        while(!end){
            int id=cursor.getInt(cursor.getColumnIndex("Id"));
            String name=cursor.getString(cursor.getColumnIndex("Name"));
            String barcode=cursor.getString(cursor.getColumnIndex("Barcode"));
            String status=cursor.getString(cursor.getColumnIndex("Status"));
            Date statusDate=null;
            try {
                statusDate=dateFormat.parse(cursor.getString(cursor.getColumnIndex("Id")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Parcel parcel=new Parcel(name,barcode,status,statusDate);
            parcels.add(parcel);
            end=cursor.moveToNext();
        }
        return parcels;
    }


    public Parcel(String name, String barcode, String status, Date statusDate) {
        Name = name;
        Barcode = barcode;
        Status = status;
        StatusDate = statusDate;
    }


    static class ParcelDatabaseHelper extends SQLiteOpenHelper{
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
