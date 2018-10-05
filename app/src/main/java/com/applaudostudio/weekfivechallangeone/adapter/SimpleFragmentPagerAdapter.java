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
package com.applaudostudio.weekfivechallangeone.adapter;

import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.applaudostudio.weekfivechallangeone.fragment.FeedNewFragment;
import com.applaudostudio.weekfivechallangeone.receiver.InternetReceiver;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter implements InternetReceiver.InternetConnectionListener {
    //for the tabs
    private String tabTitles[] = new String[]{"NATIONAL", "INTERNATIONAL", "ENTERTAINMENT", "TECHNOLOGY", "SPORT", "LIFE"};
    private boolean mNetworkStatus;
    private InternetReceiver mInternetReceiver;

    /***
     * Constructor
      * @param fm Fragment manager
     */
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /***
     * get item on the position selected
     * @param position position of the tab
     * @return returns a fragment
     */
    @Override
    public Fragment getItem(int position) {
        return FeedNewFragment.newInstance(tabTitles[position]);
    }

    /***
     * count of items
      * @return returns an int
     */
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public void onInternetAvailable(boolean status) {
        mNetworkStatus=status;
    }
}
