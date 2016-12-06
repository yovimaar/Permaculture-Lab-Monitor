package edu.oswego.permaculturemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

        ra.setList(phVals);
        ph.setText("" + ra.getAverage());

        ra.setList(tempVals);
        temperature.setText("" + ra.getAverage());

        ra.setList(moistVals);
        soil.setText("" + ra.getAverage());

        ra.setList(humidVals);
        humidity.setText("" + ra.getAverage());
        ////////////////////////////////////////////////////////////////////////////////////

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
}
