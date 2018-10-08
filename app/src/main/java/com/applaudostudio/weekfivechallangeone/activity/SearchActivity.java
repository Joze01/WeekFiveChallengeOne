package com.applaudostudio.weekfivechallangeone.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.fragment.DetailFragment;
import com.applaudostudio.weekfivechallangeone.fragment.FeedNewFragment;
import com.applaudostudio.weekfivechallangeone.util.ConnectionManager;

/***
 * Activity for the search activity
 */
public class SearchActivity extends AppCompatActivity {
    SearchView mSearchView;

    /***
     * Function on create to set inflate the view
     * @param savedInstanceState bundle saved preference
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }


    /***
     * Function to create menu options set the behavior of the search view
     * @param menu menu element
     * @return returns a boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item_detail, menu);

        mSearchView = (SearchView) menu.findItem(R.id.action_search_detail).getActionView();
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        mSearchView.setQueryHint(getText(R.string.search));
        //Anonymous implement of the OnQueryTextLister
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //for submit the query  for search and start load the result fragment
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, R.string.searching, Toast.LENGTH_SHORT).show();
                FragmentManager manager = getSupportFragmentManager();
                ConnectionManager connectionManager = new ConnectionManager(getApplication());
                Fragment fragment;
                if(connectionManager.isNetworkAvailable()) {
                    fragment = FeedNewFragment.newInstance(query);
                }else{
                    fragment = FeedNewFragment.newInstanceSearchOnline(query);
                }



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

        return true;
    }

}
