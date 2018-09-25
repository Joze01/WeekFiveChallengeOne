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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays "news list".
 */
public class FeedNewFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private List<ItemNews> NewsList;

    public static FeedNewFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FeedNewFragment fragment = new FeedNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feednew, container, false);
        TextView txtParam = (TextView) v.findViewById(R.id.textView);
        txtParam.setText("# Fragment" + mPage);
        return v;
    }

    public static class NewsListLoader extends AsyncTaskLoader<List<ItemNews>> {
        private String mQueryString;

        public NewsListLoader(Context context, String queryString) {
            super(context);
            mQueryString = queryString;
        }

        @Nullable
        @Override
        public List<ItemNews> loadInBackground() {
            List<ItemNews> data = new ArrayList<>();

            return null;
        }
    }

}
