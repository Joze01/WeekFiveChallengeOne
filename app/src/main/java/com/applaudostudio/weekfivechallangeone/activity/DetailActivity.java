package com.applaudostudio.weekfivechallangeone.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.fragment.DetailFragment;
import com.applaudostudio.weekfivechallangeone.fragment.FeedNewFragment;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;

/***
 * Activity for detail item
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "ITEM_DATA";
    ItemNews item;

    /***
     * Create and inflate the fragment on the container
     * @param savedInstanceState bundle for save preferences
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item = getIntent().getParcelableExtra(EXTRA_ITEM);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = DetailFragment.newInstance(item);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerDetail, fragment);
        transaction.commit();
    }


}
