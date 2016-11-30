package edu.oswego.permaculturemonitor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBarToolBar);


        // find the temperature view
        TextView temperature = (TextView) findViewById(R.id.temperature);

        // set a click listener on that View
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the TemperatureActivity
                Intent tempIntent = new Intent(MainActivity.this, TempActivity.class);

                // start the new actitivy
                startActivity(tempIntent);
            }
        });


        // find humidity view
        TextView humidity = (TextView) findViewById(R.id.humidity);

        // set a click listener on that View
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the TemperatureActivity
                Intent humidityIntent = new Intent(MainActivity.this, HumidityActivity.class);

                // start the new actitivy
                startActivity(humidityIntent);
            }
        });

        // find the soil view
        TextView soil = (TextView) findViewById(R.id.soil);

        // set a click listener on that View
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the TemperatureActivity
                Intent soilIntent = new Intent(MainActivity.this, SoilActivity.class);

                // start the new actitivy
                startActivity(soilIntent);
            }
        });

        // find the ph view
        TextView ph = (TextView) findViewById(R.id.ph);

        // set a click listener on that View
        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the TemperatureActivity
                Intent phIntent = new Intent(MainActivity.this, PhActivity.class);

                // start the new actitivy
                startActivity(phIntent);
            }
        });


        // find the about view
        TextView about = (TextView) findViewById(R.id.about);

        // set a click listener on that View
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to open the TemperatureActivity
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);

                // start the new actitivy
                startActivity(aboutIntent);
            }
        });


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


    public void contactUs(View view){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:permaculture@oswego.edu"));
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }




}
