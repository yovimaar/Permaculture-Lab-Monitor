package edu.oswego.permaculturemonitor;

/**
 * Created by Vi on 11/28/16.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String strUserName = SP.getString("username", "NA");
        boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
        String downloadType = SP.getString("downloadType","1");
        String lowHumid = SP.getString("lowHumid","0");
        String highHumid = SP.getString("highHumid","100");
        String lowMoist = SP.getString("lowMoist","0");
        String highMoist = SP.getString("highMoist","100");
        String lowTemp = SP.getString("lowTemp","0");
        String highTemp = SP.getString("highTemp","100");
        String lowPh = SP.getString("lowPh","0");
        String highPh = SP.getString("highPh","100");



    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

    }

}