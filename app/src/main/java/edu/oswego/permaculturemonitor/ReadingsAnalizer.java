package edu.oswego.permaculturemonitor;

import java.util.ArrayList;
import java.lang.Math;


/**
 * Created by chrisrk192 on 11/4/2016.
 * There should be 4 of these created. One for each type of Reading val
 */
public class ReadingsAnalizer {
    public ArrayList<Reading> list;


    public ReadingsAnalizer(){

    }

    public ReadingsAnalizer(ArrayList<Reading> r){
        list = r;
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
        if(list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                avg += list.get(i).getValue();
            }
            avg = avg / list.size();
        }
        return avg;
    }

    public float calcStdDev(){
        float dev = 0;
        //1. Work out the Mean (the simple average of the numbers)
        float avg = getAverage();
        //NOT SURE IF THIS IS MAKING A SHALLOW OR DEEP COPY
        ArrayList<Reading> tempList = list;

        //2. Then for each number: subtract the Mean and square the result
        for(int i = 0; i < tempList.size(); i++) {
            float subMean = tempList.get(i).getValue() - avg;
            double powered = Math.pow((double) subMean, 2.0);
            tempList.get(i).setValue((float) powered);
        }

        //3. Then work out the mean of those squared differences.
        avg = 0;
        if(tempList.size() != 0) {
            for (int i = 0; i < tempList.size(); i++) {
                avg += tempList.get(i).getValue();
            }
            avg = avg / tempList.size();
        }

        //4. Take the square root of that and we are done!
        dev = (float) Math.pow((double)avg, .5);
        return dev;
    }
}
