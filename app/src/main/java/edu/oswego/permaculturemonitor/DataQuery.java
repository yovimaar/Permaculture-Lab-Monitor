package edu.oswego.permaculturemonitor;


import android.app.Notification;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;


import com.google.maps.android.heatmaps.WeightedLatLng;
import java.util.List;

/**
 * Created by chrisrk192 on 11/1/2016.
 *
 * THIS CLASS MUST BE INSTANCIATED AFTER THE DATABASE
 * CONNECTION HAS BEEN ESTABLISHED IN THE CONNECTOR CLASS
 */

public class DataQuery {
    Timestamp from, to; //called timestamp in database
    int sensorID;
    int zoneID;
    float lowValue, highValue;
    Point location;
    int numSensors;
    Connection con;

    public DataQuery(){
        con = Connector.getInstance().getConnection();
    }

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

    public ArrayList<Reading> getLatestPHs() {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  typeID = '4')";//may be 3
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("PH Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestPHs(Timestamp from) {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '4') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("PH Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestPHs(Timestamp from, Timestamp to) {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '4') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("PH Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getLatestHumids() {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  typeID = '3')";//may be 4
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();

                        Log.v("Hum Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));

                        vals.add(read);
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestHumids(Timestamp from) {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '3') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();

                        Log.v("HUM Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));

                        vals.add(read);
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestHumids(Timestamp from, Timestamp to) {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '3') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        Reading read = new Reading();

                        Log.v("HUM Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));

                        vals.add(read);
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getLatestTemps() {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE " +
                "typeID = '2')";
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        int i = 1;
                        while(rs.next()){
                            Reading read = new Reading();

                            Log.v("TP Reading", ""
                                    + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                    + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                    + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                    + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                            );
                            read.setReadingID(rs.getInt(1));
                            read.setSensorID(rs.getInt(2));
                            read.setValue(rs.getFloat(3));
                            read.setTs(rs.getTimestamp(4));

                            vals.add(read);

                        }
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestTemps(Timestamp from) {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '2') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        int i = 1;
                        while(rs.next()){
                            Reading read = new Reading();

                            Log.v("TP Reading", ""
                                    + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                    + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                    + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                    + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                            );
                            read.setReadingID(rs.getInt(1));
                            read.setSensorID(rs.getInt(2));
                            read.setValue(rs.getFloat(3));
                            read.setTs(rs.getTimestamp(4));

                            vals.add(read);

                        }
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestTemps(Timestamp from, Timestamp to) {
        /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '2') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{

            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    numberOfColumns = metadata.getColumnCount();

                    //Should increment through the result set and populate the arrayList
                    while (rs.next()) {
                        int i = 1;
                        while(rs.next()){
                            Reading read = new Reading();

                            Log.v("TP Reading", ""
                                    + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                    + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                    + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                    + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                            );
                            read.setReadingID(rs.getInt(1));
                            read.setSensorID(rs.getInt(2));
                            read.setValue(rs.getFloat(3));
                            read.setTs(rs.getTimestamp(4));

                            vals.add(read);

                        }
                    }

                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getLatestMoists() {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE typeID = '1' )";
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    while(rs.next()) {
                        Reading read = new Reading();

                        Log.v("Mst Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1) + "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2) + "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3) + "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4) + "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestMoists(Timestamp from) {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '1' AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current.toString() + "')";
        Log.v("QUERY!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    while(rs.next()) {
                        Reading read = new Reading();
                        Log.v("Mst Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1) + "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2) + "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3) + "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4) + "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    public ArrayList<Reading> getLatestMoists(Timestamp from, Timestamp to) {
         /*
        Preconditions: the connection must be established
        Returns an Array list populated with values on success
        Returns null on failure
        */
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '1') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        Log.v("QUERY!!!!!!!!!!!!!", query);
        ResultSet rs;
        ResultSetMetaData metadata;
        int numberOfColumns;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.v("Connection","Connection Null in getLatestPHs");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    metadata = rs.getMetaData();
                    while(rs.next()) {
                        Reading read = new Reading();
                        Log.v("Mst Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1) + "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2) + "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3) + "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4) + "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }


    public ArrayList<Reading> getFromSensor(int sensorID, Timestamp from) {
        /**
         Preconditions: the connection must be established
         a valid sensorID must be passed
         If a null timestamp is provided, all the readings for that sensor
         will be returned
         Returns an Array list populated with values on success
         Returns null on failure
         */
        ArrayList<Reading> vals = new ArrayList<>();
        Timestamp current = new Timestamp(System.currentTimeMillis());
        String query;
        //check to see if Timestamp is valid
        if(from != null) {
            query = "SELECT * FROM reading WHERE sensorID = '" + sensorID
                    + "' AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                    + from.toString() + "' AND '" + current + "')";
        } else{
            query = "SELECT * FROM reading WHERE sensorID = '" + sensorID
                    + "' AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                    + "2016-11-10 11:00:00' AND '" + current + "')";
        }
        ResultSet rs;
        ResultSetMetaData metadata;

        //Query
        try{
            //it should be larger in scope and passed in
            if(con == null){
                //Error connecting to DataBase
                Log.e("Connection","Couldnt connect to to database");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));

                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getFromZone(int zoneID) {
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId;";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        /////THIS SHOULD CHANGE WITH PROPER ORDER//////////////////////
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));

                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    //could break
    public ArrayList<Reading> getFromZone(Point location, Timestamp from) {
        ArrayList<Reading> vals = new ArrayList<>();
        Timestamp current = new Timestamp(System.currentTimeMillis());
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId AND reading.ts IN (SELECT ts FROM reading " +
                "WHERE ts BETWEEN '" + from.toString() + "' AND '" + current.toString() + "');";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));

                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getFromZone(Point location, Timestamp from, Timestamp to) {
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId AND reading.ts IN (SELECT ts FROM reading " +
                "WHERE ts BETWEEN '" + from.toString() + "' AND '" + to.toString() + "');";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();

                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));

                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public List<WeightedLatLng> getWeightedFromZone(int zoneID, int type){
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"'";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID

                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double val = (double) vals.get(i).getValue();
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();

            list.add(new WeightedLatLng(new LatLng(lat,lng),val));
        }
        return list;
    }
    public List<WeightedLatLng> getWeightedFromZone(int zoneID, int type, Timestamp from){
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"' and " +
                "ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current + "')";

        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID

                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double val = (double) vals.get(i).getValue();
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();

            list.add(new WeightedLatLng(new LatLng(lat,lng),val));
        }
        return list;
    }
    public List<WeightedLatLng> getWeightedFromZone(int zoneID, int type, Timestamp from, Timestamp to){
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"' and " +
                "ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";

        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID

                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double val = (double) vals.get(i).getValue();
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();

            list.add(new WeightedLatLng(new LatLng(lat,lng),val));
        }
        return list;
    }


    public List<LatLng> getLatLngFromZone(int zoneID, int type){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"'";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();
            list.add(new LatLng(lat,lng));
        }
        return list;
    }
    public List<LatLng> getLatLngFromZone(int zoneID, int type, Timestamp from){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"' and " +
                "ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + current + "')";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();
            list.add(new LatLng(lat,lng));
        }
        return list;
    }
    public List<LatLng> getLatLngFromZone(int zoneID, int type, Timestamp from, Timestamp to){
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        //get the data
        /////////////////////////////////////////////////////////////////
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ArrayList<Reading> vals = new ArrayList<>();
        //returns zoneID sensorID loc value
        String query = "select zone.id,sensor.id, X(loc),Y(loc),reading.value, " +
                "reading.ts from zone,sensor,reading where " +
                "zone.id = '" + zoneID  +"' and ST_CONTAINS(zone.perimeter,sensor.loc) " +
                "and sensor.id = reading.sensorId and sensor.typeID = '"+ type +"' and " +
                "ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                //just a test query
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(4)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getFloat(5)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(6)+ "\n"
                        );
                        //no need for zoneID
                        read.setSensorID(rs.getInt(2));
                        read.setX(rs.getFloat(3));
                        read.setY(rs.getFloat(4));
                        read.setValue(rs.getFloat(5));
                        read.setTs(rs.getTimestamp(6));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        ///////////////////////////////////////////////////////////////////
        //parse it
        for (int i = 0; i < vals.size(); i++) {
            double lat = (double)vals.get(i).getY();
            double lng = (double)vals.get(i).getX();
            list.add(new LatLng(lat,lng));
        }
        return list;
    }


    public ArrayList<Reading> getRange(Timestamp from, Timestamp to, int typeID){
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT * FROM reading WHERE sensorID IN (SELECT id FROM sensor WHERE  " +
                "typeID = '"+ typeID +"') AND ts IN (SELECT ts FROM reading WHERE ts BETWEEN '"
                + from.toString() + "' AND '" + to.toString() + "')";
        ResultSet rs;
        ResultSetMetaData metadata;

        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();
                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading", ""
                                + metadata.getColumnName(1) + ": " + rs.getInt(1)+ "\n"
                                + metadata.getColumnName(2) + ": " + rs.getInt(2)+ "\n"
                                + metadata.getColumnName(3) + ": " + rs.getFloat(3)+ "\n"
                                + metadata.getColumnName(4) + ": " + rs.getTimestamp(4)+ "\n"
                        );
                        read.setReadingID(rs.getInt(1));
                        read.setSensorID(rs.getInt(2));
                        read.setValue(rs.getFloat(3));
                        read.setTs(rs.getTimestamp(4));

                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }
    //Not sure how to handle bloc
    public ArrayList<Reading> getPerimeterAsBlob(int id){
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT perimeter FROM zone WHERE id = '" + id + "'";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();

                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading",""
                                + metadata.getColumnName(1) + ": " + rs.getBlob(1)+ "\n");
                        read.setPerimeter(rs.getBlob(1));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

    public ArrayList<Reading> getPerimeterAsString(int id){
        ArrayList<Reading> vals = new ArrayList<>();
        String query = "SELECT AsText(perimeter) FROM zone WHERE id = '" + id + "'";
        ResultSet rs;
        ResultSetMetaData metadata;
        //Query
        try{
            if(con == null){
                //Error connecting to DataBase
                Log.e("connection","Couldnt connect to to database!?!");
            } else {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    Log.i("connection","it worked!");
                    metadata = rs.getMetaData();

                    while (rs.next()) {
                        Reading read = new Reading();
                        Log.v("Reading",""
                                + metadata.getColumnName(1) + ": " + rs.getString(1)+ "\n");
                        read.setPerimeterAsText(rs.getString(1));
                        vals.add(read);
                    }
                }
            }
        }catch (Exception ex){
            Log.e("Exceptions","");
            ex.printStackTrace();
            return null;
        }
        return vals;
    }

}
