package edu.oswego.permaculturemonitor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Vi on 12/1/16.
 */

public class MoistGraphActivity extends AppCompatActivity {
    ///////Datepicker stuff/////////////////////////////////////////////////////////////////////////////////////////
    //Christopher King 12/1/2016
    Connection con;
    Button fromBtn, toBtn;
    int year_from, year_to, month_from, month_to, day_from, day_to;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private int dateFlag;
    private final int FROM_DIALOG = 0;
    private final int TO_DIALOG = 111;
    private final int FROM_DIALOG2 = 222;
    private final int TO_DIALOG2 = 333;

    private Timestamp fromTS = Timestamp.valueOf("2016-10-10 00:00:00");
    private Timestamp toTS = Timestamp.valueOf("2016-12-10 00:00:00");
    private Timestamp fromTS2 = Timestamp.valueOf("2016-10-10 00:00:00");
    private Timestamp toTS2 = Timestamp.valueOf("2016-12-10 00:00:00");
    ///////END DATE PICKER STUFF////////////////////////////////////////////////////////////////////////////////////////

    LineGraphSeries<DataPoint> moistseries1,moistseries2;
    DataPoint[] dataPoints1,dataPoints2;
    private Graph moistgraph;
    private int lastX = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moist_graph_layout);

        ///////Datepicker stuff/////////////////////////////////////////////////////////////////////////////////////////
        //Christopher King 12/1/2016
        dateView = (TextView) findViewById(R.id.diagnosticM);
        //sets the current date and feeds that into the dialog so they apprear with 2016 not 1947
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        //WARNING THERE MAY BE AN OFF BY ONE ERROR WITH MONTH, BUT I THINK I FIXED IT
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //by default the TO timestamp should be initialized to current
        //ERRORS ARE THROWN IF THE MONTH IS LESS THAN 10 BECAUSE IT NEEDS 2 DIGITS
        if (day < 10 && month < 10) {
            toTS = Timestamp.valueOf(year + "-0" + month + "-0" + day + " 00:00:00");
            toTS2 = Timestamp.valueOf(year + "-0" + month + "-0" + day + " 00:00:00");
        } else if (day < 10) {
            toTS = Timestamp.valueOf(year + "-" + month + "-0" + day + " 00:00:00");
            toTS2 = Timestamp.valueOf(year + "-" + month + "-0" + day + " 00:00:00");
        } else if (month < 10) {
            toTS = Timestamp.valueOf(year + "-0" + month + "-" + day + " 00:00:00");
            toTS2 = Timestamp.valueOf(year + "-0" + month + "-" + day + " 00:00:00");
        } else {
            toTS = Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00");
            toTS2 = Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00");
        }
        Log.v("DatePicker to", toTS.toString());

        ///////END DATE PICKER STUFF////////////////////////////////////////////////////////////////////////////////////////


 //////////GRAPH VIEW STUFF//////////////////////////////////////////////////////////////////////////////////////////
        DataQuery dq = new DataQuery();
        //create reading
        Reading read = new Reading();
        //create readingsanalizers: one for each type of reading

        GraphView moistGraph = (GraphView) findViewById(R.id.graph);
        moistseries1 = new LineGraphSeries<>();
        moistGraph.getGridLabelRenderer().setVerticalAxisTitle("Value");
        moistGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        moistGraph.getViewport().setScrollable(true);
        moistGraph.getViewport().setScalableY(true);

        moistGraph.addSeries(moistseries1);

/////////END GRAPH VIEW STUFF///////////////////////////////////////////////////////////////////////////////////////
    }
/////////GRAPH STUFF//////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addEntry() {
        //UNUSED
        moistseries1.appendData((moistgraph.getSeries()[lastX++]), true, moistgraph.getSeries().length);
    }
    public void onResume() {
        //UNUSED
        //simulate real time with thread that appends data to the graph
        final DataQuery dq = new DataQuery();
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < dq.getLatestMoists().size(); i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    //sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
/////////END GRAPH STUFF//////////////////////////////////////////////////////////////////////////////////////////////////////////







    ///////Datepicker stuff/////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("deprecation")
    public void setFrom(View view) {
        showDialog(FROM_DIALOG);
        Toast.makeText(getApplicationContext(), "from",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setTo(View view) {
        showDialog(TO_DIALOG);
        Toast.makeText(getApplicationContext(), "to",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setFrom2(View view) {
        showDialog(FROM_DIALOG2);
        Toast.makeText(getApplicationContext(), "from",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setTo2(View view) {
        showDialog(TO_DIALOG2);
        Toast.makeText(getApplicationContext(), "to",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == FROM_DIALOG) {
            dateFlag = 1;
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id == TO_DIALOG) {
            dateFlag = 2;
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id == FROM_DIALOG2) {
            dateFlag = 3;
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id == TO_DIALOG2) {
            dateFlag = 4;
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    arg2++; // it goes 0 to 11, but we need 1 to 12
                    //arg1 = year;
                    //arg2 = month;
                    //arg3 = day;
                    switch (dateFlag){
                        case 1://from
                            year_from = arg1;
                            month_from = arg2;
                            day_from = arg3;
                            //or
                            //ERRORS ARE THROWN IF THE MONTH IS LESS THAN 10 BECAUSE IT NEEDS 2 DIGITS
                            if(arg3 < 10 && arg2 < 10)
                                fromTS = Timestamp.valueOf(arg1 + "-0" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg3 < 10)
                                fromTS = Timestamp.valueOf(arg1 + "-" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg2 < 10)
                                fromTS = Timestamp.valueOf(arg1 + "-0" + arg2 + "-" + arg3 + " 00:00:00");
                            else
                                fromTS = Timestamp.valueOf(arg1 + "-" + arg2 + "-" + arg3 + " 00:00:00");
                            Log.v("DatePicker from",fromTS.toString());
                            testTime();//just a test, not needed
                            break;
                        case 2://to
                            year_to = arg1;
                            month_to = arg2;
                            day_to = arg3;
                            //ERRORS ARE THROWN IF THE MONTH IS LESS THAN 10 BECAUSE IT NEEDS 2 DIGITS
                            if(arg3 < 10 && arg2 < 10)
                                toTS = Timestamp.valueOf(arg1 + "-0" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg3 < 10)
                                toTS = Timestamp.valueOf(arg1 + "-" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg2 < 10)
                                toTS = Timestamp.valueOf(arg1 + "-0" + arg2 + "-" + arg3 + " 00:00:00");
                            else
                                toTS = Timestamp.valueOf(arg1 + "-" + arg2 + "-" + arg3 + " 00:00:00");
                            Log.v("DatePicker to",toTS.toString());
                            testTime();//just a test, not needed
                            break;

                        //THE Second DataSet
                        case 3://from
                            year_from = arg1;
                            month_from = arg2;
                            day_from = arg3;
                            //or
                            //ERRORS ARE THROWN IF THE MONTH IS LESS THAN 10 BECAUSE IT NEEDS 2 DIGITS
                            if(arg3 < 10 && arg2 < 10)
                                fromTS2 = Timestamp.valueOf(arg1 + "-0" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg3 < 10)
                                fromTS2 = Timestamp.valueOf(arg1 + "-" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg2 < 10)
                                fromTS2 = Timestamp.valueOf(arg1 + "-0" + arg2 + "-" + arg3 + " 00:00:00");
                            else
                                fromTS2 = Timestamp.valueOf(arg1 + "-" + arg2 + "-" + arg3 + " 00:00:00");
                            Log.v("DatePicker from",fromTS2.toString());
                            testTime();//just a test, not needed
                            break;
                        case 4://to
                            year_to = arg1;
                            month_to = arg2;
                            day_to = arg3;
                            //ERRORS ARE THROWN IF THE MONTH IS LESS THAN 10 BECAUSE IT NEEDS 2 DIGITS
                            if(arg3 < 10 && arg2 < 10)
                                toTS2 = Timestamp.valueOf(arg1 + "-0" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg3 < 10)
                                toTS2 = Timestamp.valueOf(arg1 + "-" + arg2 + "-0" + arg3 + " 00:00:00");
                            else if(arg2 < 10)
                                toTS2 = Timestamp.valueOf(arg1 + "-0" + arg2 + "-" + arg3 + " 00:00:00");
                            else
                                toTS2 = Timestamp.valueOf(arg1 + "-" + arg2 + "-" + arg3 + " 00:00:00");
                            Log.v("DatePicker to",toTS2.toString());
                            testTime();//just a test, not needed
                            break;


                        default:
                            Log.e("DatePicker","Bad flag");
                            break;
                    }
                    dataPoints1 = updateDataSet1();//set 1
                    dataPoints2 = updateDataSet2();

////////////////////////////////////////////
                    //moistgraph = new Graph(0, 100,0, 40, 10, 10, moistra.getList());
                    GraphView moistGraph = (GraphView) findViewById(R.id.graph);
                    //phSeries = new LineGraphSeries<>(hmgraph.getSeries());
                    moistseries1 = new LineGraphSeries<>(dataPoints1);
                    moistseries2 = new LineGraphSeries<>(dataPoints2);
                    //humGraph.addSeries(phSeries);
                    moistGraph.removeAllSeries();
                    moistGraph.addSeries(moistseries1);
                    moistGraph.addSeries(moistseries2);

                    moistGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                    moistGraph.getGridLabelRenderer().setNumHorizontalLabels(3);
                    moistGraph.getGridLabelRenderer().setNumVerticalLabels(5);
                    moistGraph.getGridLabelRenderer().setHumanRounding(false);
// //////////////////////////////////////////


                }
            };

    public void testTime(){
        //JUST A TEST METHOD TO DISPLAY READINGS. REQUIRES A TextView with id diagnostic
        con = Connector.getInstance().getConnection();
        String d = "No readings";
        TextView diag = (TextView) findViewById(R.id.diagnosticM);
        if(con != null) {
            DataQuery dq = new DataQuery();
            ReadingsAnalizer ra = new ReadingsAnalizer();
            ArrayList<Reading> readings;
            Log.v("Connection", "Not Null");
            readings = dq.getLatestMoists(fromTS, toTS); //1 is moisture type
            Log.v("DATA SETS!!!!!!!!!", "FROM : " + fromTS.toString() + "\n" + "       TO: " + toTS.toString());
            if (readings != null) {
                ra.setList(readings);
                d = "Std Dev :" + ra.calcStdDev() + "\n";
                d = d + "Mean    :" + ra.getAverage() + "\n";

                for (int i = 0; i < readings.size(); i++) {
                    d = d + "Value :" + readings.get(i).getValue() +
                            "ReadingID :" + readings.get(i).getReadingID() + "\n";
                }

            } else {
                d = "NULL READINGS";
                Toast.makeText(getApplicationContext(), "NULL READINGS", Toast.LENGTH_SHORT).show();
            }
        } else {
            d = "NULL CONNECTION";
            Toast.makeText(getApplicationContext(), "NULL CONNECTION", Toast.LENGTH_SHORT).show();
        }
        diag.setText(d);
    }

    public DataPoint[] updateDataSet1(){
        con = Connector.getInstance().getConnection();
        if(con != null) {
            DataQuery dq = new DataQuery();
            ReadingsAnalizer ra = new ReadingsAnalizer();
            ArrayList<Reading> readings;

            Log.v("Connection", "Not Null");
            readings = dq.getLatestMoists(fromTS, toTS);// Set1 buttons
            Log.v("DATA SETS!!!!!!!!!", "FROM : " + fromTS.toString() + "\n" + "     TO: " + toTS.toString());
            Log.v("DATA SETS!!!!!!!!!", "FROM : " + fromTS2.toString() + "\n" + "     TO: " + toTS2.toString());
            if (readings != null) {
                //UPDATE LISTS, OR PARSE READINGS TO GRAPHABLE FORMAT
                DataPoint[] values = new DataPoint[readings.size()];
                for (int i = 0; i < readings.size(); i++) {
                    Date x = readings.get(i).getTSasDate(); //PROBLEM HERE BECAUSE IT RETURNS AS A DOUBLE
                    double y = (double) readings.get(i).getValue();
                    DataPoint v = new DataPoint(x, y);
                    values[i] = v;
                }
                return values;
            } else {return null;}
        }else {return null;}
    }
    public DataPoint[] updateDataSet2(){
        con = Connector.getInstance().getConnection();
        if(con != null) {
            DataQuery dq = new DataQuery();
            ReadingsAnalizer ra = new ReadingsAnalizer();
            ArrayList<Reading> history;


            Log.v("Connection", "Not Null");
            history = dq.getLatestMoists(fromTS2,toTS2);// Set2 buttons
            Log.v("DATA SETS!!!!!!!!!", "FROM : " + fromTS.toString() + "\n" + "     TO: " + toTS.toString());
            Log.v("DATA SETS!!!!!!!!!", "FROM : " + fromTS2.toString() + "\n" + "     TO: " + toTS2.toString());
            if (history != null) {
                //UPDATE LISTS, OR PARSE READINGS TO GRAPHABLE FORMAT
                DataPoint[] values = new DataPoint[history.size()];
                for (int i = 0; i < history.size(); i++) {
                    double x = (double) history.get(i).getTs().getTime(); //PROBLEM HERE BECAUSE IT RETURNS AS A DOUBLE
                    double y = (double) history.get(i).getValue();
                    DataPoint v = new DataPoint(x, y);
                    values[i] = v;
                }
                return values;
            } else {return null;}
        }else {return null;}
    }

    //////////END DATEPICKER STUFF/////////////////////////////////////////////////////////////////////////////////

}