package edu.oswego.permaculturemonitor;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
//Import graph libraries

/**
 * Created by chrisrk192 on 11/2/2016.
 */
public class Graph {
    private float xMin, xMax, yMin, yMax, deltaX, deltaY;
    private float[][] point;
    ArrayList<Reading> readings;

    public Graph(){
        xMax = 24.0f;
        xMin = 0.0f;
        yMax = 100.0f;
        yMin = 0.0f;
        deltaX = 1.0f;
        deltaY = 5.0f;

    }
    public Graph(int xMin, int xMax, int yMin, int yMax, int deltaX, int deltaY, ArrayList<Reading> read){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        readings = read;
    }

    //Getters and Setters
    public float getxMin() {
        return xMin;
    }

    public void setxMin(float xMin) {
        this.xMin = xMin;
    }

    public float getxMax() {
        return xMax;
    }

    public void setxMax(float xMax) {
        this.xMax = xMax;
    }

    public float getyMin() {
        return yMin;
    }

    public void setyMin(float yMin) {
        this.yMin = yMin;
    }

    public float getyMax() {
        return yMax;
    }

    public void setyMax(float yMax) {
        this.yMax = yMax;
    }

    public float getDeltaX() {
        return deltaX;
    }

    private void setDeltaX() {
        this.deltaX = (xMax - xMin)/ readings.size();
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaX = (yMax - yMin)/ readings.size();
    }

    public DataPoint[] getSeries() {
        DataPoint[] series = new DataPoint[readings.size()];
        for(int i = 0; i < readings.size(); i++){
            series[i] = new DataPoint(readings.get(i).getTs(), readings.get(i).getValue());
        }
        return series;
    } //DataPoint (Time, Value)

    //End of Getters and Setters
}
