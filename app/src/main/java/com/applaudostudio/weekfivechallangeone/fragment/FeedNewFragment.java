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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.adapter.NewsListAdapter;
import com.applaudostudio.weekfivechallangeone.loader.LoaderNewsAsync;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.util.JSONParserItem;
import com.applaudostudio.weekfivechallangeone.util.UrlManager;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays "news list".
 */
public class FeedNewFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> , NewsListAdapter.ItemSelectedListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    private String mPage;
    private String categoryText;
    private RecyclerView mRecyclerViewNews;
    private NewsListAdapter mAdapterNews;

    private List<ItemNews> mNewsList;



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
        mPage = getArguments().getString(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feednew, container, false);
        TextView txtParam = (TextView) v.findViewById(R.id.textView);
        txtParam.setText("# Fragment" + mPage);
        mRecyclerViewNews= v.findViewById(R.id.recyclerNews);


        categoryText=mPage;
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).startLoading();

        mRecyclerViewNews.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewNews.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        Log.v("ONCREATE_ACTIVITY","OK");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        UrlManager urlGenrator = new UrlManager();
        return new LoaderNewsAsync(getActivity(),urlGenrator.createFirstLoadUrl(categoryText));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        JSONParserItem parser = new JSONParserItem();
        mNewsList=parser.getNewList(data);

        mAdapterNews = new NewsListAdapter(mNewsList,this);
        mRecyclerViewNews.setAdapter(mAdapterNews);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        mNewsList=new ArrayList<>();
    }


    @Override
    public void onClickNewsDetail(ItemNews item) {

    }
}
