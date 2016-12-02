package edu.oswego.permaculturemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
