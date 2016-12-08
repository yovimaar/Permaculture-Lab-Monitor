package edu.oswego.permaculturemonitor;

/**
 * Created by chrisrk192 on 11/4/2016.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.ArrayList;
import java.lang.Math;

public class ReadingsAnalizer {
    public ArrayList<Reading> list;
    public int numStdDev;


    public ReadingsAnalizer(){
        numStdDev = 3;
    }
    public ReadingsAnalizer(int numStdDev_){
        numStdDev = numStdDev_;
    }
    public ReadingsAnalizer(ArrayList<Reading> r){
        list = r;
    }
    public ReadingsAnalizer(ArrayList<Reading> r, int numStdDev_){
        list = r;
        numStdDev = numStdDev_;
    }

    public void setList(ArrayList<Reading> list) {
        this.list = list;
    }

    public ArrayList<Reading> getList(){
        return list;
    }

    public float getAverage(){
        /*Returns 0 if the size is 0, else returns the average of the values
        * */
        float avg = 0;
        if(list != null){
            Log.v("Avg","list size = " + list.size());
            if(list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    Log.v("avg","ading item : " + i + " value: " + list.get(i).getValue());
                    avg += list.get(i).getValue();
                }
                avg = avg / (float) list.size();
            }
        } else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return avg;
    }

    public float calcStdDev(){
        float dev = 0;
        if(list != null) {
            //1. Work out the Mean (the simple average of the numbers)
            float avg = getAverage();
            //need a temp list so that the true list is not altered
            ArrayList<Reading> tempList = new ArrayList<Reading>();
            for (Reading r : list) {//Deeeeeep Copy
                tempList.add(r.clone());
            }

            //2. Then for each number: subtract the Mean and square the result
            for (int i = 0; i < tempList.size(); i++) {
                float subMean = tempList.get(i).getValue() - avg;
                double powered = Math.pow((double) subMean, 2.0);
                tempList.get(i).setValue((float) powered);
            }

            //3. Then work out the mean of those squared differences.
            avg = 0;
            if (tempList.size() != 0) {
                for (int i = 0; i < tempList.size(); i++) {
                    avg += tempList.get(i).getValue();
                }
                avg = avg / (tempList.size() - 1);
            }

            //4. Take the square root of that and we are done!
            dev = (float) Math.pow((double) avg, .5);
        } else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return dev;
    }

    /**
     * this method returns weather a value is normal compared to the list
     * should not be called frequently. A more sutable method for frequent
     *      use is available
     * */
    public boolean isHealthyReading(Reading read){
        boolean healthy = true;
        if(list != null){
            float dev = calcStdDev();//may want to pass in the deviation
            float mean = getAverage();
            Log.v("Standard Deviation","" + dev);
            Log.v("Standard Mean","" + mean);
            if(read.getValue() > numStdDev * dev + mean || read.getValue() < numStdDev * dev - mean)
                healthy = false;
        } else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return healthy;
    }
    /**
     * this method returns weather a value is normal compared to the list
     * */
    public boolean isHealthyReading(Reading read, float dev, float mean){
        boolean healthy = true;
        if(list != null){
            Log.v("Health Check","Reading val: " + read.getValue());
            Log.v("Health Check","Upper: " + ((numStdDev * dev) + mean));
            Log.v("Health Check","Lowwer: " + (mean - (numStdDev * dev)));
            if(read.getValue() > (numStdDev * dev) + mean || read.getValue() < mean - (numStdDev * dev))
                healthy = false;
        }else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return healthy;
    }

    public boolean isSetHealthy(float low, float high){
        boolean healthy = true;
        float dev, mean;
        dev = calcStdDev();
        mean = getAverage();
        if(list != null) {




            //check the STD DEV for health
            for (int i = 0; i < list.size(); i++) {
                if (!isHealthyReading(list.get(i), dev, mean))
                    healthy = false;
                else if(list.get(i).getValue() > high || list.get(i).getValue() < low )
                    healthy = false;
            }
            //check if any are null or 0


        } else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return healthy;
    }

    /** Returns a list of unhealthy readings
     * isSetHealthy should be called before this to see if there are any unhealthy
     */
    public ArrayList<Reading> getUnhealthyReadings(float low, float high){
        ArrayList<Reading> unhealthy = new ArrayList<Reading>();
        float dev = calcStdDev();
        float mean = getAverage();
        Log.v("Standard Deviation","" + dev);
        Log.v("Standard Meain","" + mean);
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (!isHealthyReading(list.get(i), dev, mean))
                    unhealthy.add(list.get(i).clone()); //copy the unhealty reading
                else if(list.get(i).getValue() > high || list.get(i).getValue() < low )
                    unhealthy.add(list.get(i).clone()); //copy the unhealty reading
            }
        } else {Log.v("ReadingsAnalizer","Attempt to calculate with null list");}
        return unhealthy;
    }
}
