package com.stealthfueldelivery.app.delivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stealthfueldelivery.app.delivery.R;

public class NavigationDrawerActivity extends AppCompatActivity implements View.OnClickListener{
        //implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawer;
        TextView toolbartextView;
        Intent intent;
        RelativeLayout layout_dashboard,layout_account;
        Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_navigation_drawer);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            toolbartextView = findViewById(R.id.toolbartextView);

           /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            // navigationView.setNavigationItemSelectedListener(this);

            layout_dashboard = findViewById(R.id.layout_dashboard);
            layout_dashboard.setOnClickListener(this);
            layout_account = findViewById(R.id.layout_account);
            layout_account.setOnClickListener(this);

       }

    @Override
    public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
                }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_dashboard: {
                intent = new Intent(this, Home.class);
                startActivity(intent);
                break;
            }
            case R.id.layout_account: {
                intent = new Intent(this, Account.class);
                startActivity(intent);
                break;
            }

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notification) {
            intent = new Intent(this,Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
       /* @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_camera) {
                // Handle the camera action
            } else if (id == R.id.account) {
                intent = new Intent(this,Account.class);
                startActivity(intent);

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_manage) {

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }*/
 }
