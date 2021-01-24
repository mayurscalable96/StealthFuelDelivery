package com.stealthfueldelivery.app.delivery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.adapter.ViewPagerAdapter;
import com.stealthfueldelivery.app.delivery.fragement.AssignedOrderDelivered;
import com.stealthfueldelivery.app.delivery.fragement.NewOrderAssigned;
@RequiresApi(api = Build.VERSION_CODES.M)
public class Home extends NavigationDrawerActivity {

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView tabOne;
    TextView tabTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_home);

        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        drawer.addView(contentView, 0);
        toolbartextView.setText("Stealth Fuel");

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        createViewPager(viewPager);
        // Create an adapter that knows which fragment should be shown on each page
       // adapter = new ViewPagerAdapter( getSupportFragmentManager());

        // Set the adapter onto the view pager
       // viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //adapter.notifyDataSetChanged(); //this line will force all pages to be loaded fresh when changing between fragments
               // tabOne.setTextColor(getResources().getColor(R.color.red));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                adapter.notifyDataSetChanged();
            }
        });

    }

    private void createTabIcons() {

        tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabtext, null);
        tabOne.setText("New Order");
       // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.splash, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabtext, null);
        tabTwo.setText("Delivered Order");
       // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.splash, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }


    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NewOrderAssigned(), "New Order");
        adapter.addFrag(new AssignedOrderDelivered(), "Delivered Order");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
