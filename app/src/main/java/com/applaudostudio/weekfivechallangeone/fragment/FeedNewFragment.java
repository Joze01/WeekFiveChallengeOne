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
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
public class FeedNewFragment extends Fragment implements LoaderManager.LoaderCallbacks, NewsListAdapter.ItemSelectedListener,InternetReceiver.InternetConnectionListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NETWORK = "ARG_NETWORK";
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

    public static FeedNewFragment newInstance(String category) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, category);
        FeedNewFragment fragment = new FeedNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mPage = getArguments().getString(ARG_PAGE);
        }
        mContentResolver = getActivity().getContentResolver();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feednew, container, false);
        mRecyclerViewNews = v.findViewById(R.id.recyclerNews);
        mProgressLoad = v.findViewById(R.id.progressBarLoading);
        mProgressLoad.setVisibility(View.GONE);
        mCategoryText = mPage;
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        mInternetStatus=connectionManager.isNetworkAvailable();
        mAdapterNews = new NewsListAdapter(mNewsList, this,mInternetStatus);
        mRecyclerViewNews.setAdapter(mAdapterNews);

        if (!mInternetStatus){
            getLoaderManager().initLoader(1, null, this);
            getLoaderManager().getLoader(1);
        } else {
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

    @Override
    public void onResume() {
        super.onResume();
        mInternetReceiver=new InternetReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        getActivity().registerReceiver(mInternetReceiver,  intentFilter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(PAGE_COUNTER, mPagerCount);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClickNewsDetail(ItemNews item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ITEM, item);
        startActivity(intent);
    }


    private ContentValues valuesContainer(ItemNews item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_ID, item.getNewId());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_HEAD_LINE, item.getTitle());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_BODY_TEXT, item.getTextBody());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_WEB_URL, item.getWebUrl());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_THUMBNAIL, item.getWebUrl());
        contentValues.put(TheGuardianContact.News.COLUMN_NEW_CATEGORY, item.getmCategory());
        return contentValues;
    }

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
            return new CursorLoader(getActivity(), TheGuardianContact.News.CONTENT_URI, null, TheGuardianContact.News.COLUMN_NEW_CATEGORY+"=?", new String[]{mCategoryText}, null);
        }
        return new LoaderNewsAsync(getActivity(), urlGenerator.GenerateURLByElement(UrlManager.ELEMENT_TYPE_JSON, mCategoryText, 0, true));
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        if (loader.getId() == 0) {
            JSONParserItem parser = new JSONParserItem();
            mNewsList.addAll(parser.getNewList((String) data, mCategoryText));
            mContentResolver.delete(TheGuardianContact.News.CONTENT_URI, TheGuardianContact.News.COLUMN_NEW_CATEGORY + "=?", new String[]{mCategoryText});
            for (ItemNews item : mNewsList) {
                mContentResolver.insert(TheGuardianContact.News.CONTENT_URI, valuesContainer(item));
            }
        } else {
            mNewsList = new ArrayList<>();
            DataInterpreter interpreter = new DataInterpreter();
            mNewsList = interpreter.cursorToList((Cursor) data);
        }
        mAdapterNews.setData(mNewsList);
        mProgressLoad.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mNewsList = new ArrayList<>();
    }


    @Override
    public void onInternetAvailable(boolean status) {
        mInternetStatus=status;
    }
}
