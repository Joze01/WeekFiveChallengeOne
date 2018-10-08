package com.applaudostudio.weekfivechallangeone.fragment;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.activity.DetailActivity;
import com.applaudostudio.weekfivechallangeone.adapter.NewsListAdapter;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.util.ConnectionManager;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;

import java.util.ArrayList;
import java.util.List;


public class ReadMeLatterFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, NewsListAdapter.ItemSelectedListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    List<ItemNews> mNewsList;
    private RecyclerView mRecyclerViewNews;
    private NewsListAdapter mAdapterNews;
    private ItemNews mItem;

    public ReadMeLatterFragment() {
        // Required empty public constructor
    }

    /***
     * Function to get a instance of the fragment
     * @return returns a new instance of a fragment
     */
    public static ReadMeLatterFragment newInstance() {
        ReadMeLatterFragment fragment = new ReadMeLatterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Function to create the view of the fragment
     *
     * @param inflater           inflater for the fragment
     * @param container          container view
     * @param savedInstanceState bundle saved
     * @return this returns a view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_read_me_latter, container, false);
        mRecyclerViewNews = v.findViewById(R.id.recyclerViewFavs);
        return v;
    }

    /***
     * Function to create the activity of the fragment and init the adapter and the recycler view
     * @param savedInstanceState saved bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerViewNews.setHasFixedSize(true);
        // use a linear layout manager
        mNewsList = new ArrayList<>();
        ConnectionManager checkConnectionManager = new ConnectionManager(getActivity());
        mAdapterNews = new NewsListAdapter(mNewsList, this, checkConnectionManager.isNetworkAvailable());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewNews.setLayoutManager(mLayoutManager);
        mRecyclerViewNews.setAdapter(mAdapterNews);
    }

    /**
     * Restar loader on resume
     */
    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(3, null, ReadMeLatterFragment.this);
    }

    //Callbacks of the loader
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            return new CursorLoader(getActivity(), TheGuardianContact.ReadMeLatter.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mNewsList = new ArrayList<>();
        DataInterpreter interpreter = new DataInterpreter();
        mNewsList = interpreter.cursorToList(data);
        mAdapterNews.setData(mNewsList);
        mAdapterNews.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNewsList = new ArrayList<>();
    }

    /***
     * on click for the detail intent
     * @param item item to send to the new activity
     */
    @Override
    public void onClickNewsDetail(ItemNews item) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra(DetailActivity.EXTRA_ITEM, item);
        startActivity(i);
    }

    /***
     * long press for item on the recycler view
     * @param item item clicked
     * @return return true to not execute simple click listener and false to do it
     */
    @Override
    public boolean onLongClickNewDelete(ItemNews item) {
        mItem = item;
        if (getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.delete_dialog).setPositiveButton(R.string.yesdialog, dialogClickListener)
                    .setNegativeButton(R.string.dialogno, dialogClickListener).show();
        }

        return true;
    }


    /***
     * Dialog for the YES NO on delete item
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (getActivity() != null) {
                        ContentResolver contentResolver;
                        contentResolver = getActivity().getContentResolver();
                        contentResolver.delete(TheGuardianContact.ReadMeLatter.CONTENT_URI, TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID + "=?", new String[]{mItem.getNewId()});
                        getLoaderManager().restartLoader(3, null, ReadMeLatterFragment.this);
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };


}
