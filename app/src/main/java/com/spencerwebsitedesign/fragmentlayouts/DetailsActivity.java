package com.spencerwebsitedesign.fragmentlayouts;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by cspencer1 on 3/3/2016.
 */
public class DetailsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check to see if device is in Landscape Mode
        // We only want this activity in Portrait Mode
        // If we are in Landscape Mode, then just shut this activity down
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // Shut down this specific activity
            finish();
            return;
        }

        if(savedInstanceState == null)
        {
            DetailsFragment details = new DetailsFragment();

            // Get the bundle of key value pairs (just one, where the index was)
            details. setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}
