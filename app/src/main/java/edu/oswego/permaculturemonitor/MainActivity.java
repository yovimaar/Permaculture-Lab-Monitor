package edu.oswego.permaculturemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBarToolBar);
  


    }
}
