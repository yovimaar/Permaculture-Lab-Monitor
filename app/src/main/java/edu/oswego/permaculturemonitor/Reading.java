package edu.oswego.permaculturemonitor;

import android.graphics.Point;
import android.os.StrictMode;
import android.util.Log;

import java.util.Date;
import java.sql.Timestamp;
import java.sql.Blob;


/**
 * Created by chrisrk192 on 11/28/2016.
 */

public class Reading {
    private int sensorID = -1;
    private int readingID = -1;
    private int typeID = -1;
    private float value = -1.0f;
    //private Point location;
    //private Blob location;
    //private LatLng latLng;
    private float x,y = -1;
    private Timestamp ts;
    private Blob perimeter;
    private String perimeterAsText;

    public String getPerimeterAsText() {
        return perimeterAsText;
    }
    public void setPerimeterAsText(String perimeterAsText) {
        this.perimeterAsText = perimeterAsText;
    }

    public Blob getPerimeter() {
        return perimeter;
    }
    public void setPerimeter(Blob perimeter) {
        this.perimeter = perimeter;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

    public int getTypeID() {
        return typeID;
    }
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getSensorID() {
        return sensorID;
    }
    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public int getReadingID() {
        return readingID;
    }
    public void setReadingID(int readingID) {
        this.readingID = readingID;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    /*
    public Blob getLocation() {
        return location;
    }

    public void setLocation(Blob location) {
        this.location = location;
    }
    */
    public Timestamp getTs() {
        return ts;
    }
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Reading clone(){
        Reading cloned = new Reading();
        cloned.setValue(value);
        cloned.setSensorID(sensorID);
        cloned.setReadingID(readingID);
        cloned.setTypeID(typeID);
        cloned.setTs(ts);
        cloned.setPerimeter(perimeter);
        cloned.setX(x);
        cloned.setY(y);

        return cloned;
    }


    public int getTSasInt(){
        int date = 0;
        if(ts != null){
            String timeString = ts.toString();
            int year,month,day;
            year = (timeString.charAt(0) - '0')* 1000;
            year += (timeString.charAt(1) - '0')* 100;
            year += (timeString.charAt(2) - '0')* 10;
            year += (timeString.charAt(3) - '0');
            Log.v("Date to Int", "Year : "+ year);
            month = (timeString.charAt(5) - '0') * 10;
            month += (timeString.charAt(6) - '0');
            Log.v("Date to Int", "Month : "+ month);
            day =  (timeString.charAt(8) - '0') *10;
            day +=  (timeString.charAt(9) - '0');
            Log.v("Date to Int", "Day : "+ day);

            date = (year * 10000) +(month * 100) + day;
            Log.v("Date to Int","Date: " + date);
            return date;
        } else {return date;}
    }

    public Date getTSasDate(){
        if (ts != null){
            Date d = new Date(ts.getTime());
            return d;
        }else return null;
    }
}
