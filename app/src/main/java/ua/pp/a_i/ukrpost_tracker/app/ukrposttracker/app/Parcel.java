package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;


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


    public Parcel(int id, String name, String barcode, String status, Date statusDate) {
        Id = id;
        Name = name;
        Barcode = barcode;
        Status = status;
        StatusDate = statusDate;
    }
}
