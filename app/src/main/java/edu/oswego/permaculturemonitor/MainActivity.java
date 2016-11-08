package edu.oswego.permaculturemonitor;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


//android studio emulator was killed before it could launch. i usually use a different emulator, but it uses ap 19 for some reason
//i still can't test anything, but this is what i've got

//Right now, these graphs should just be displaying readings the database is getting in. As it is, you can't switch to looking
//at averages, or looking at specific sensors, or anything like that. 
//Didn't have time to create any dummy data yet. 
//For now, the graph assumes that you've chosen to look at the ph graph
//For now, the checkbox brings up the other graphs: soil moisture, temperature, and humidity
//The button is supposed to let you pick a date and see the graph data for that date.

public class MainActivity extends AppCompatActivity {

    //declaring series, lastX, and graph objects
    private LineGraphSeries<DataPoint> phSeries;
    private LineGraphSeries<DataPoint> humSeries;
    private LineGraphSeries<DataPoint> smSeries;
    private LineGraphSeries<DataPoint> tempSeries;
    private int lastX = 0;
    private Graph phgraph;
    private Graph hmgraph;
    private Graph smgraph;
    private Graph tpgraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBarToolBar);
        actionBarToolBar.setLogo(R.mipmap.logo);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }


            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Toast.makeText(MainActivity.this, "---test---", Toast.LENGTH_SHORT).show();
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Toast.makeText(MainActivity.this, "---TEST---", Toast.LENGTH_SHORT).show();

            }
        });

        if (viewPager.getCurrentItem() == 2) {
            //create dataquery
            DataQuery dq = new DataQuery();
            //create reading
            Reading read = new Reading();
            //create readingsanalizers: one for each type of reading
            ReadingsAnalizer pHra = new ReadingsAnalizer(dq.getLatestpHs());
            ReadingsAnalizer sMra = new ReadingsAnalizer(dq.getLatestMoists());
            ReadingsAnalizer tpra = new ReadingsAnalizer(dq.getLatestTemps());
            ReadingsAnalizer hmra = new ReadingsAnalizer(dq.getLatestHumids());

            //create new graph objects from the readinganalizers and dataquery
            phgraph = new Graph(((int) dq.getLowValue()), ((int) dq.getHighValue()), dq.getFrom().getNanos(), dq.getTo().getNanos(), ((int) (dq.getHighValue() - dq.getLowValue())), ((int) (dq.getFrom().getNanos() - dq.getTo().getNanos())), pHra.getList());
            smgraph = new Graph(((int) dq.getLowValue()), ((int) dq.getHighValue()), dq.getFrom().getNanos(), dq.getTo().getNanos(), ((int) (dq.getHighValue() - dq.getLowValue())), ((int) (dq.getFrom().getNanos() - dq.getTo().getNanos())), sMra.getList());
            hmgraph = new Graph(((int) dq.getLowValue()), ((int) dq.getHighValue()), dq.getFrom().getNanos(), dq.getTo().getNanos(), ((int) (dq.getHighValue() - dq.getLowValue())), ((int) (dq.getFrom().getNanos() - dq.getTo().getNanos())), hmra.getList());
            tpgraph = new Graph(((int) dq.getLowValue()), ((int) dq.getHighValue()), dq.getFrom().getNanos(), dq.getTo().getNanos(), ((int) (dq.getHighValue() - dq.getLowValue())), ((int) (dq.getFrom().getNanos() - dq.getTo().getNanos())), tpra.getList());

            //creates the graph that will have the pH values in it
            GraphView phGraph = (GraphView) findViewById(R.id.graph);
            //creates the series with the data that will be graphed
            //phSeries = new LineGraphSeries<>(phgraph.getSeries());
            phSeries = new LineGraphSeries<>();
            //adds the series to the graph; actually graphs the DataPoints
            phGraph.addSeries(phSeries);

            GraphView humGraph = (GraphView) findViewById(R.id.graph);
            //humSeries = new LineGraphSeries<>(hmgraph.getSeries());
            humSeries = new LineGraphSeries<>();
            //humGraph.addSeries(humSeries);

            GraphView tempGraph = (GraphView) findViewById(R.id.graph);
            //tempSeries = new LineGraphSeries<>(tpgraph.getSeries());
            tempSeries = new LineGraphSeries<>();
            //tempGraph.addSeries(tempSeries);

            GraphView smGraph = (GraphView) findViewById(R.id.graph);
            smSeries = new LineGraphSeries<>();
            //smSeries = new LineGraphSeries<>(smgraph.getSeries());
            //smGraph.addSeries(smSeries);


            //Check Box UI: creates the checkbox that's toggled to compare graphs against each other
            final CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
            //if the check box is checked
            if (checkbox.isChecked()) {
                //ph graph is already graphed, graph humidity, temperature, and soil moisture
                humGraph.addSeries(humSeries);
                tempGraph.addSeries(tempSeries);
                smGraph.addSeries(smSeries);
            }
            //BUTTON UI: creates the box that, when clicked, brings up the date slider
            final Button button = (Button) findViewById(R.id.button);
            //creates a SetDate object when clicked
            button.setOnClickListener((View.OnClickListener) new SetDate());
            if (button.isActivated()) {
                button.callOnClick();
            }
        }
    }

    //method that updates the series and graphs. this needs to be called in realtime separate from onCreate, so it is its own method
    public void addEntry() {
        phSeries.appendData((phgraph.getSeries()[lastX++]), true, phgraph.getSeries().length);
        humSeries.appendData((hmgraph.getSeries()[lastX++]), true, hmgraph.getSeries().length);
        tempSeries.appendData((tpgraph.getSeries()[lastX++]), true, tpgraph.getSeries().length);
        smSeries.appendData((smgraph.getSeries()[lastX++]), true, smgraph.getSeries().length);
    }

    public void onResume() {
        //simulate real time with thread that appends data to the graph
        final DataQuery dq = new DataQuery();
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < dq.getLatestpHs().size(); i++) {
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


//commented out - r.menu.menu and r.id.preferences are causing errors on my end, but I've left them unchanged
//i have no idea why this happened    
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences: {
                Intent intent = new Intent();
                intent.setClassName(this, "edu.oswego.permaculturemonitor.MyPreferenceActivity");
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    */


    //class to instantiate onClick method
    public class SetDate {
        DataQuery dq = new DataQuery();

        //when clicked, the button will switch from the graph layout to the date slider layout
        public void onClick(View v) {
            setContentView(R.layout.selectdateandtime);
            //instantiate the datepicker
            final DatePicker date = (DatePicker) findViewById(R.id.datePicker);
            //updates the date
            date.updateDate(date.getYear(), date.getMonth(), date.getDayOfMonth());
            //gets the date
            Date dt = new Date(date.getYear(), date.getMonth(), date.getDayOfMonth());
            //formats the date
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            //convert the date into a long
            long output = dt.getTime() / 1000L;
            String str = Long.toString(output);
            long timestamp = Long.parseLong(str) * 1000;
            //new timestamp from the long made from the date
            Timestamp ts = new Timestamp(timestamp);
            //query data from that date
            dq.setTo(ts);
            dq.setFrom(ts);
            //back to the graph
            setContentView(R.layout.fragmenttab2);
        }


    }
}
