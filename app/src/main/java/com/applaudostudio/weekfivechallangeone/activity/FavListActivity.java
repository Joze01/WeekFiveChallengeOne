package com.applaudostudio.weekfivechallangeone.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.fragment.DetailFragment;
import com.applaudostudio.weekfivechallangeone.fragment.ReadMeLatterFragment;
import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;

public class FavListActivity extends AppCompatActivity {

    /***
     * Inflate the fragment for the readme latter list
     * @param savedInstanceState saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        FragmentManager manager= getSupportFragmentManager();
        Fragment fragment = ReadMeLatterFragment.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerFavs, fragment);
        transaction.commit();
    }
}
