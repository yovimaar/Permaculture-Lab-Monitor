package edu.oswego.permaculturemonitor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String ConnURL = "jdbc:mysql://pi.cs.oswego.edu:3306/pll";
    private String ur = "pllReader";
    private String pass = "readStuff";
    private Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //////CONNECTION TO DB///////////////////////////////////////////////////////////////////////////
        DataQuery dq;
        ReadingsAnalizer ra = new ReadingsAnalizer();
        ArrayList<Reading> readings;

        //connect to the Database
        con = connect();
        if(con != null){
            Connector.getInstance().setConnection(con);
            dq = new DataQuery();
            //exeTest(con);
        } else {dq = new DataQuery();}

        /////////////////////////////////////////////////////////////////////////////////


        // action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBarToolBar);


        Button tempMap = (Button) findViewById(R.id.tempMapButton);

        // set a click listener on that View
        tempMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, TempMapActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button tempGraph = (Button) findViewById(R.id.tempGraphButton);

        // set a click listener on that View
        tempGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, TempGraphActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button moistMap = (Button) findViewById(R.id.moistMapButton);

        // set a click listener on that View
        moistMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, MoistMapActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button moistGraph = (Button) findViewById(R.id.moistGraphButton);

        // set a click listener on that View
        moistGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, MoistGraphActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button humMap = (Button) findViewById(R.id.humMapButton);

        // set a click listener on that View
        humMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, HumMapActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button humGraph = (Button) findViewById(R.id.humGraphButton);

        // set a click listener on that View
        humGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, HumGraphActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button phMap = (Button) findViewById(R.id.pHMapButton);

        // set a click listener on that View
        phMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, PhMapActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        Button phGraph = (Button) findViewById(R.id.phGraphButton);

        // set a click listener on that View
        phGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the activity
                Intent Intent = new Intent(MainActivity.this, PhGraphActivity.class);
                // start the new actitivy
                startActivity(Intent);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////
        //Update the Text view of the "buttons"
        ArrayList<Reading> phVals, tempVals, humidVals, moistVals;
        TextView ph = (TextView) findViewById(R.id.pHTextViewVal);
        TextView temperature = (TextView) findViewById(R.id.tempTextViewVal);
        TextView soil = (TextView) findViewById(R.id.moistTextViewVal);
        TextView humidity = (TextView) findViewById(R.id.humTextViewVal);
        phVals = dq.getLatestPHs();
        tempVals = dq.getLatestTemps();
        humidVals = dq.getLatestHumids();
        moistVals = dq.getLatestMoists();

        if(phVals == null)
            Log.v("ERROR PH", "Value NULL");
        if(tempVals == null)
            Log.v("ERROR TEMP", "Value NULL");
        if(humidVals == null)
            Log.v("ERROR HUMID", "Value NULL");
        if(moistVals == null)
            Log.v("ERROR MOIST", "Value NULL");

        ra.setList(phVals);
        ph.setText("" + ra.getAverage());

        ra.setList(tempVals);
        temperature.setText("" + ra.getAverage());

        ra.setList(moistVals);
        soil.setText("" + ra.getAverage());

        ra.setList(humidVals);
        humidity.setText("" + ra.getAverage());
        ////////////////////////////////////////////////////////////////////////////////////

        //////ALERT STUFF//////////////////////////////////////////////////////////////////////

        short healthFlag = 0;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float highPh,lowPh,highMoist,lowMoist,highTemp,lowTemp,highHumid,lowHumid;
        //TODO SET THE HIGH AND LOWS BASED ON THE SP
        String lowHumidString = SP.getString("lowHumid","0");
        String highHumidString = SP.getString("highHumid","100");
        String lowMoistString = SP.getString("lowMoist","0");
        String highMoistString = SP.getString("highMoist","100");
        String lowTempString = SP.getString("lowTemp","0");
        String highTempString = SP.getString("highTemp","100");
        String lowPhString = SP.getString("lowPh","0");
        String highPhString = SP.getString("highPh","100");

        lowHumid = (float)Integer.valueOf(lowHumidString);
        highHumid = (float)Integer.valueOf(highHumidString);
        lowMoist = (float)Integer.valueOf(lowMoistString);
        highMoist = (float)Integer.valueOf(highMoistString);
        lowTemp = (float)Integer.valueOf(lowTempString);
        highTemp = (float)Integer.valueOf(highTempString);
        lowPh = (float)Integer.valueOf(lowPhString);
        highPh = (float)Integer.valueOf(highPhString);
                                                                      // DD   HH   MM   SS   MILISEC
        //Timestamp recent = new Timestamp(System.currentTimeMillis() - (  7 * 24 * 60 * 60 * 1000));
        ra.setList(dq.getLatestMoists());
        if(!ra.isSetHealthy(lowMoist, highMoist)) {
            healthFlag = 1;
        }
        ra.setList(dq.getLatestHumids());
        if(!ra.isSetHealthy(lowHumid,highHumid)) {
            healthFlag = 1;
        }
        ra.setList(dq.getLatestPHs());
        if(!ra.isSetHealthy(lowPh,highPh)) {
            healthFlag = 1;
        }
        ra.setList(dq.getLatestTemps());
        if(!ra.isSetHealthy(lowTemp,highTemp)) {
            healthFlag = 1;
        }
        if(healthFlag != 0){
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage(getAlert())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .show();
        }
        /////////////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preferences:
                Intent i = new Intent(this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /////ESTAbLISH A CONNECTION//////////////////////////////////////////////////////////////////////
    public Connection connect() {
        try {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(ConnURL, ur, pass);
            Log.v("Connection", "Connection success");
            return  connection;

        } catch (Exception e) {
            Log.v("Connection", "Connection FAILED");
            e.printStackTrace();
            return null;
        }
    }
    //////////////////////////////////////////////////////////////////////////
    public String getAlert() {
        // DD   HH   MM   SS   MILISEC
        //Timestamp recent = new Timestamp(System.currentTimeMillis() - (  7 * 24 * 60 * 60 * 1000));

        DataQuery dq = new DataQuery();
        ReadingsAnalizer ra = new ReadingsAnalizer();
        ArrayList<Reading> unh;
        String disp = "";
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float highPh,lowPh,highMoist,lowMoist,highTemp,lowTemp,highHumid,lowHumid;
        //TODO SET THE HIGH AND LOWS BASED ON THE SP
        String lowHumidString = SP.getString("lowHumid","0");
        String highHumidString = SP.getString("highHumid","100");
        String lowMoistString = SP.getString("lowMoist","0");
        String highMoistString = SP.getString("highMoist","100");
        String lowTempString = SP.getString("lowTemp","0");
        String highTempString = SP.getString("highTemp","100");
        String lowPhString = SP.getString("lowPh","0");
        String highPhString = SP.getString("highPh","100");

         lowHumid = (float)Integer.valueOf(lowHumidString);
         highHumid = (float)Integer.valueOf(highHumidString);
         lowMoist = (float)Integer.valueOf(lowMoistString);
         highMoist = (float)Integer.valueOf(highMoistString);
         lowTemp = (float)Integer.valueOf(lowTempString);
         highTemp = (float)Integer.valueOf(highTempString);
         lowPh = (float)Integer.valueOf(lowPhString);
         highPh = (float)Integer.valueOf(highPhString);

        Log.v("\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\nSETTINGS!!", "LowH: " + lowHumid + " HighH: " + highHumid
                        + "LowM: " +lowMoist  + " HighM: "+ highMoist
                        +"LowT: " + lowTemp  + " HighT: " + highTemp
                        +"LowP: " + lowPh + " HighP: " + highPh + "\n\n\n\n\n\n\n\n\n\n");


        ra.setList(dq.getLatestHumids());
        if (!ra.isSetHealthy(lowHumid,highHumid)) {
            unh = ra.getUnhealthyReadings(lowHumid,highHumid);
            for (int i = 0; i < unh.size(); i++) {
                Log.v("Unhealthy", "Size of list: " + unh.size());
                disp = disp + "Unhealthy Humidity found: SensorID " + unh.get(i).getSensorID() + "\n Value "
                        + unh.get(i).getValue() + "\n";
            }
        }
        ra.setList(dq.getLatestTemps());
        if (!ra.isSetHealthy(lowTemp,highTemp)) {
            unh = ra.getUnhealthyReadings(lowTemp,highTemp);
            for (int i = 0; i < unh.size(); i++) {
                Log.v("Unhealthy", "Size of list: " + unh.size());
                disp = disp + "Unhealthy Temp found: SensorID " + unh.get(i).getSensorID() + "\n Value "
                        + unh.get(i).getValue() + "\n";
            }
        }
        ra.setList(dq.getLatestPHs());
        if (!ra.isSetHealthy(lowPh,highPh)) {
            unh = ra.getUnhealthyReadings(lowPh,highPh);
            for (int i = 0; i < unh.size(); i++) {
                Log.v("Unhealthy", "Size of list: " + unh.size());
                disp = disp + "Unhealthy PH found: SensorID " + unh.get(i).getSensorID() + "\n Value "
                        + unh.get(i).getValue() + "\n";
            }
        }
        ra.setList(dq.getLatestMoists());
        if (!ra.isSetHealthy(lowMoist,highMoist)) {
            unh = ra.getUnhealthyReadings(lowMoist,highMoist);
            for (int i = 0; i < unh.size(); i++) {
                Log.v("Unhealthy", "Size of list: " + unh.size());
                disp = disp + "Unhealthy Moisture found: SensorID " + unh.get(i).getSensorID() + "\n Value "
                        + unh.get(i).getValue() + "\n";
            }
        }

        return disp;

    }




}
