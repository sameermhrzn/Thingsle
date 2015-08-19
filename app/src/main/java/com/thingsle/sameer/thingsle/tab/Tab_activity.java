package com.thingsle.sameer.thingsle.tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.thingsle.sameer.thingsle.R;

public class Tab_activity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_activity);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);


        TabHost.TabSpec trending = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec recent = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec popular = tabHost.newTabSpec("Third tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        trending.setIndicator("Trending");
        trending.setContent(new Intent(this, Trending_Activit.class));

        popular.setIndicator("Popular");
        popular.setContent(new Intent(this, Popular_tab.class));

        recent.setIndicator("Recent");
        recent.setContent(new Intent(this, Recent_Tab.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(trending);
        tabHost.addTab(popular);
        tabHost.addTab(recent);

    }
}