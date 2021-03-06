package com.thingsle.sameer.thingsle.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.thingsle.sameer.thingsle.adapter.CustomDrawerAdapter;
import com.thingsle.sameer.thingsle.data.Drawer_Item;
import com.thingsle.sameer.thingsle.R;

import java.util.ArrayList;
import java.util.List;

public class NavDrawerActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;
    protected FrameLayout frameLayout;

    List<Drawer_Item> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_items);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        dataList.add(new Drawer_Item(R.drawable.ic_home, "Home"));
        dataList.add(new Drawer_Item(R.drawable.icon_todolist, "To do list"));
        dataList.add(new Drawer_Item(R.drawable.icon_favorites, "Favorites"));
        dataList.add(new Drawer_Item(R.drawable.icon_suggestions, "Suggestions"));
        dataList.add(new Drawer_Item(R.drawable.icon_login, "Login/Logout"));

        adapter =  new CustomDrawerAdapter(this,R.layout.activity_drawer_item,dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectActivity(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, MainActivity.class));
                break;

            default:
                break;
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectActivity(position);
        }
    }

/*  When we use fragment use this method
    public void selectFragment(int position){
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();

        switch (position) {
            case 0:

                Intent intent = new Intent(NavDrawer.this,MainActivity.class);
                startActivity(intent);
                /*
                Toast.makeText(this, dataList.get(position).getTvItemName(), Toast.LENGTH_SHORT).show();
                fragment = new FragmentOne();
                args.putString(FragmentOne.Item_name, dataList.get(position)
                        .getTvItemName());
                args.putInt(FragmentOne.Image_resourceId, dataList.get(position)
                        .getIvIcon());

                break;
            case 1:
                fragment = new FragmentOne();
                args.putString(FragmentOne.Item_name, dataList.get(position)
                        .getTvItemName());
                args.putInt(FragmentOne.Image_resourceId, dataList.get(position)
                        .getIvIcon());
                break;
            case 2:
                fragment = new FragmentOne();
                args.putString(FragmentOne.Item_name, dataList.get(position)
                        .getTvItemName());
                args.putInt(FragmentOne.Image_resourceId, dataList.get(position)
                        .getIvIcon());
                break;
            case 3:
                fragment = new FragmentOne();
                args.putString(FragmentOne.Item_name, dataList.get(position)
                        .getTvItemName());
                args.putInt(FragmentOne.Image_resourceId, dataList.get(position)
                        .getIvIcon());
                break;
            case 4:
                fragment = new FragmentOne();
                args.putString(FragmentOne.Item_name, dataList.get(position)
                        .getTvItemName());
                args.putInt(FragmentOne.Image_resourceId, dataList.get(position)
                        .getIvIcon());
                break;

            default:
                break;

        }

        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(dataList.get(position).getTvItemName());
        mDrawerLayout.closeDrawer(mDrawerList);
    }*/
}


