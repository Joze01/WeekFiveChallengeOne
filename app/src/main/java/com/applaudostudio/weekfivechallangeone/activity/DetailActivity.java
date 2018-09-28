package com.applaudostudio.weekfivechallangeone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM="ITEM_DATA";
    ItemNews item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item=getIntent().getParcelableExtra(EXTRA_ITEM);
        ImageView img = findViewById(R.id.imageView);
    }





}
