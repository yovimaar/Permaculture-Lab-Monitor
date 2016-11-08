package edu.oswego.permaculturemonitor;

import android.graphics.Point;

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * Created by chrisrk192 on 11/1/2016.
 */

public class DataQuery {
    Timestamp from, to; //called timestamp in database
    int sensorID;
    int zoneID;
    float lowValue, highValue;
    Point location;
    int numSensors;



    //Getters and Setters/////////////////////
    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public int getNumSensors() {
        return numSensors;
    }

    public void setNumSensors(int numSensors) {
        this.numSensors = numSensors;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public float getLowValue() {
        return lowValue;
    }

    public void setLowValue(float lowValue) {
        this.lowValue = lowValue;
    }

    public float getHighValue() {
        return highValue;
    }

    public void setHighValue(float highValue) {
        this.highValue = highValue;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    //End of Getters and Setters/////////////////////

    /*public float[] getLatestPHs() {
        float vals[] = new float[numSensors];

        return vals;
    }

    public float[] getLatestHumids() {
        float vals[] = new float[numSensors];
        return vals;
    }

    public float[] getLatestTemps() {
        float vals[] = new float[numSensors];
        return vals;
    }*/

    public ArrayList<Reading> getLatestTemps() {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getLatestHumids() {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getLatestpHs() {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getLatestMoists() {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getFromSensor(int sensorID, Timestamp from) {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getFromZone(int zoneID) {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }
    public ArrayList<Reading> getFromZone(Point location, Timestamp from) {
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }

    public ArrayList<Reading> getRange(Timestamp from, Timestamp to){
        ArrayList<Reading> vals = new ArrayList<>();

        return vals;
    }
}
