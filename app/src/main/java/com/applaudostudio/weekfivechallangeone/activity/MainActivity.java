package com.applaudostudio.weekfivechallangeone.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.adapter.SimpleFragmentPagerAdapter;

/***
 * Main Activity for the loader and set the fragment data for the list on the tabs
 */
public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_SEARCH_STATUS = "LOAD_SEARCH_VIEW";
    SimpleFragmentPagerAdapter mNewsAdapter;
    ViewPager mPager;

    /***
     * override of the on create to set the pager and the tabs
     * @param savedInstanceState save instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpagerNews);
        mPager.setAdapter(mNewsAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
    }

    /***
     * start activity for the menu icon
     * @param item item news model as param
     * @return return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_SEARCH_STATUS, true);
        startActivity(intent);
        super.onOptionsItemSelected(item);
        return true;
    }

    /***
     * function to inflate the menu item (show the icon only)
     * @param menu menu for the menu view
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
