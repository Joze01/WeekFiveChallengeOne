package com.applaudostudio.weekfivechallangeone;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applaudostudio.weekfivechallangeone.adapter.SimpleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity  {
    SimpleFragmentPagerAdapter mNewsAdapter;

    ViewPager mPager;
    public static final int OPERATION_SEARCH_LOADER = 22;
    public static final String OPERATION_URL_EXTRA = "url_that_return_json_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),this);

        mPager = findViewById(R.id.viewpagerNews);
        mPager.setAdapter(mNewsAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

    }



}
