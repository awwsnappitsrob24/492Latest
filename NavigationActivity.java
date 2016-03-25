package com.example.robien.beachbuddy;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

/**
 * Created by Calvin on 2/24/2016.
 */
public class NavigationActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawerList);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
