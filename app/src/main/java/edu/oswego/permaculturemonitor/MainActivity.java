package edu.oswego.permaculturemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends AppCompatActivity {

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
    }
}
