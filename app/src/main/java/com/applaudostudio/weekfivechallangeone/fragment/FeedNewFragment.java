/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.applaudostudio.weekfivechallangeone.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.applaudostudio.weekfivechallangeone.activity.FavListActivity;
import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.activity.DetailActivity;
import com.applaudostudio.weekfivechallangeone.adapter.NewsListAdapter;
import com.applaudostudio.weekfivechallangeone.loader.LoaderNewsAsync;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.receiver.InternetReceiver;
import com.applaudostudio.weekfivechallangeone.util.ConnectionManager;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;
import com.applaudostudio.weekfivechallangeone.util.JSONParserItem;
import com.applaudostudio.weekfivechallangeone.util.UrlManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays "news list".
 */
public class FeedNewFragment extends Fragment implements LoaderManager.LoaderCallbacks, NewsListAdapter.ItemSelectedListener, InternetReceiver.InternetConnectionListener, View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_SEARCHOFLINE = "SEARCHOFLINE";
    public static final String PAGE_COUNTER = "counterPages";
    public static final int MIN_PAGE_COUNT = 6;
    private String mPage;
    private String mCategoryText;
    private RecyclerView mRecyclerViewNews;
    private List<ItemNews> mNewsList;
    private int mPagerCount = 6;
    private FrameLayout mProgressLoad;
    private NewsListAdapter mAdapterNews;
    private InternetReceiver mInternetReceiver;
    private ContentResolver mContentResolver;
    private boolean mInternetStatus;
    private FloatingActionButton mFloatButton;
    private static boolean mOfflineSearch;

    /***
     * instance to load if internet is not available
     * @param category string for the search on api
     * @return return a fragment instance
     */
    public static FeedNewFragment newInstance(String category) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, category);
        FeedNewFragment fragment = new FeedNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * instance to load if internet is not available
     * @param category string for the search on api
     * @return return a fragment instance
     */
    public static FeedNewFragment newInstanceSearchOnline(String category) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, category);
        mOfflineSearch = true;
        FeedNewFragment fragment = new FeedNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * create of the fragment
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getString(ARG_PAGE);
        }
        if(getActivity()!=null)
        mContentResolver = getActivity().getContentResolver();

    }

    /***
     * Inflate view of the fragment
     * @param inflater inflater
     * @param container container view
     * @param savedInstanceState saved state bundle
     * @return return a view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feednew, container, false);
        mRecyclerViewNews = v.findViewById(R.id.recyclerNews);
        mProgressLoad = v.findViewById(R.id.progressBarLoading);
        mProgressLoad.setVisibility(View.GONE);
        mFloatButton = v.findViewById(R.id.floatingListFavButton);
        mCategoryText = mPage;
        return v;
    }

    /***
     * create the fragment activity
     * @param savedInstanceState saved bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFloatButton.setOnClickListener(this);

        mNewsList = new ArrayList<>();
        if (savedInstanceState != null) {
            mPagerCount = savedInstanceState.getInt(PAGE_COUNTER);
        } else {
            mPagerCount = 6;
        }
        mRecyclerViewNews.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewNews.setLayoutManager(mLayoutManager);
        ConnectionManager connectionManager = new ConnectionManager(getContext());
        mInternetStatus = connectionManager.isNetworkAvailable();
        mAdapterNews = new NewsListAdapter(mNewsList, this, mInternetStatus);
        mRecyclerViewNews.setAdapter(mAdapterNews);
        //use the internet loader local storage data
        if (!mInternetStatus) {
            getLoaderManager().initLoader(1, null, this);
            getLoaderManager().getLoader(1);
        } else { //load the api info
            getLoaderManager().initLoader(0, null, this);
            getLoaderManager().getLoader(0);
            mRecyclerViewNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {
                        mPagerCount++;
                        getLoaderManager().restartLoader(0, null, FeedNewFragment.this);
                    }
                }
            });
        }
    }

    /***
     * check network status to notify the user if network enable
     */
    @Override
    public void onResume() {
        super.onResume();
        mInternetReceiver = new InternetReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        if(getActivity()!=null)
        getActivity().registerReceiver(mInternetReceiver, intentFilter);
    }

    /**
     * save the state of a views
     *
     * @param outState bundle to save
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(PAGE_COUNTER, mPagerCount);
        super.onSaveInstanceState(outState);
    }

    /**
     * simple click listener implementation
     *
     * @param item item as param
     */
    @Override
    public void onClickNewsDetail(ItemNews item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ITEM, item);
        startActivity(intent);
    }

    /***
     * LongclickImplementation
     * @param item item as apram
     * @return return false to trigger the simple click
     */
    @Override
    public boolean onLongClickNewDelete(ItemNews item) {
        return false;
    }

    /***
     * CotentValues generator for the insert on SQLite
     * @param item item instace of NewItem
     * @return returns a contentValues object
     */
    private ContentValues valuesContainer(ItemNews item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_ID, item.getNewId());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_HEAD_LINE, item.getTitle());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_BODY_TEXT, item.getTextBody());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_WEB_URL, item.getWebUrl());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_THUMBNAIL, item.getThumbnailUrl());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_CATEGORY, item.getCategory());
        return contentValues;
    }

    /***
     * create for loader using the id 0 for for api and 1 for SQLite
     * @param id id of loader
     * @param args argument bundle of loader
     * @return returns a loader
     */
    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        UrlManager urlGenerator = new UrlManager();
        if (id == 0) {
            mProgressLoad.setVisibility(View.VISIBLE);
            if (mPagerCount > MIN_PAGE_COUNT) {
                return new LoaderNewsAsync(getActivity(), urlGenerator.GenerateURLByElement(UrlManager.ELEMENT_TYPE_JSON, mCategoryText, mPagerCount, false));
            }
        } else {
            if (getActivity() != null) {
                if (!mOfflineSearch) {
                    return new CursorLoader(getActivity(), TheGuardianContact.News.CONTENT_URI, null, TheGuardianContact.News.COLUMN_NEW_CATEGORY + "=?", new String[]{mCategoryText}, null);
                } else {
                    return new CursorLoader(getActivity(), TheGuardianContact.News.CONTENT_URI, null, TheGuardianContact.News.COLUMN_NEW_HEAD_LINE + " LIKE ? OR " + TheGuardianContact.News.COLUMN_NEW_BODY_TEXT + " LIKE ?", new String[]{"%" + mCategoryText + "%", "%" + mCategoryText + "%"}, null);
                }
            }
        }
        return new LoaderNewsAsync(getActivity(), urlGenerator.GenerateURLByElement(UrlManager.ELEMENT_TYPE_JSON, mCategoryText, 0, true));
    }

    /***
     * load finish  for all loader.
     * @param loader load as objetct loader
     * @param data data result
     */
    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        // for api data
        if (loader.getId() == 0) {
            JSONParserItem parser = new JSONParserItem();
            mNewsList.addAll(parser.getNewList((String) data, mCategoryText));
            mContentResolver.delete(TheGuardianContact.News.CONTENT_URI, TheGuardianContact.News.COLUMN_NEW_CATEGORY + "=?", new String[]{mCategoryText});
            for (ItemNews item : mNewsList) {
                mContentResolver.insert(TheGuardianContact.News.CONTENT_URI, valuesContainer(item));
            }
            //for SQLite data
        } else {
            mNewsList = new ArrayList<>();
            DataInterpreter interpreter = new DataInterpreter();
            mNewsList = interpreter.cursorToList((Cursor) data);
        }
        mAdapterNews.setData(mNewsList);
        mProgressLoad.setVisibility(View.GONE);
    }

    /***
     * reset the list of data
     * @param loader loader as param
     */
    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mNewsList = new ArrayList<>();
    }


    /***
     * listener for the internet status
     * @param status internet status
     */
    @Override
    public void onInternetAvailable(boolean status) {
        mInternetStatus = status;
    }

    /***
     * on click for favorite button
     * @param v view as param
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingListFavButton:
                Intent i = new Intent(getActivity(), FavListActivity.class);
                startActivity(i);
        }
    }
}
