package com.applaudostudio.weekfivechallangeone.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.fragment.FeedNewFragment;

public class SearchActivity extends AppCompatActivity {
    private Boolean mLoadSearchView;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        if (intent != null) {
            mLoadSearchView = intent.getBooleanExtra(MainActivity.EXTRA_SEARCH_STATUS, false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mLoadSearchView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.search_item_detail, menu);

            mSearchView = (SearchView) menu.findItem(R.id.action_search_detail).getActionView();
            mSearchView.setIconified(false);
            mSearchView.requestFocusFromTouch();
            mSearchView.setQueryHint(getText(R.string.search));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(SearchActivity.this, query.toLowerCase(), Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getSupportFragmentManager();
                    Fragment fragment = FeedNewFragment.newInstance(query);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.detailContainer, fragment);
                    transaction.commit();
                    mSearchView.setQuery("", false);
                    mSearchView.setIconified(true);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });
        }
        return true;
    }

}
