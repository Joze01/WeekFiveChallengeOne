package com.applaudostudio.weekfivechallangeone.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.loader.LoaderBitMapsAsync;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.util.UrlManager;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks, View.OnClickListener {
    public static final String BUNDLE_KEY = "itemKey";
    private TextView mTextBody;
    private ImageView mImageTumb;
    private TextView mTxtTitle;
    private ImageView mButtonInternet;
    private ItemNews mItem;
    private FloatingActionButton mFavButton;
    private ContentResolver mContentResolver;
    private boolean mReadmeLatterStatus;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance(ItemNews item) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY, item);
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * on create  to init the item wit the data
     * @param savedInstanceState saved preferences
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(BUNDLE_KEY);
            mReadmeLatterStatus = false;
        }
    }

    /***
     * con create view to get the
     * @param inflater a LayoutInflater
     * @param container a view container
     * @param savedInstanceState bundle to save a preferences
     * @return returns a view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        mTextBody = v.findViewById(R.id.textViewBody);
        mImageTumb = v.findViewById(R.id.appCompatImageViewHeader);
        mButtonInternet = v.findViewById(R.id.imageViewButton);
        mTxtTitle = v.findViewById(R.id.textViewHeaderTitle);
        mFavButton = v.findViewById(R.id.floatingActionButton);
        mButtonInternet.setOnClickListener(this);
        mFavButton.setOnClickListener(this);
        return v;
    }

    /***
     * Create activity to set the data on each view element and the loader
     * @param savedInstanceState saved preferences
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextBody.setText(mItem.getTextBody());
        mTxtTitle.setText(mItem.getTitle());
        getLoaderManager().restartLoader(2, null, this);
        getLoaderManager().restartLoader(4, null, this);
        if (mItem.getWebUrl().equals("")) {
            mButtonInternet.setVisibility(View.GONE);
        }


        mContentResolver = getActivity().getContentResolver();
        buttonStatusChanger(mReadmeLatterStatus);
    }

    //Call backs of the loader implement

    /***
     * On create loader, to to set the url of a image to be download
     * @param id the id of the loader
     * @param args bundle with arguments
     * @return returns a bitmap data
     */
    /*
    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        String url;
        url = mItem.getThumbnailUrl();
        UrlManager urlGenerator = new UrlManager();
        LoaderBitMapsAsync async = null;
        if (url != null) {
            async = new LoaderBitMapsAsync(getActivity(), urlGenerator.GenerateURLByElement(UrlManager.ELEMENT_TYPE_IMAGE, url, 0, false));
        }
        return async;

    }
*/
    /***
     * if load finish ends here
     * @param loader loader retuerned by oncreate
     * @param data data with the downloaded data.
     */
    /*
    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        if (data != null) {
            mImageTumb.setImageBitmap(data);
        }
    }
*/
    /***
     * if loads gets reset
     * @param loader loader for the status
     */
    /*
    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }
*/

    /***
     * on click for the web button to open the browser
     * @param v returns a view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewButton:
                if (!mItem.getWebUrl().equals("")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mItem.getWebUrl()));
                    startActivity(intent);
                }
                break;
            case R.id.floatingActionButton:
                if (mReadmeLatterStatus) {
                    mContentResolver.delete(TheGuardianContact.ReadMeLatter.CONTENT_URI, TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID + "=?", new String[]{mItem.getNewId()});
                    mReadmeLatterStatus = false;
                    buttonStatusChanger(mReadmeLatterStatus);
                } else {
                    mContentResolver.insert(TheGuardianContact.ReadMeLatter.CONTENT_URI, this.valuesGenerator(mItem));
                    mReadmeLatterStatus = true;
                    buttonStatusChanger(mReadmeLatterStatus);
                }
                break;
        }
    }

    public ContentValues valuesGenerator(ItemNews item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID, item.getNewId());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_HEAD_LINE, item.getTitle());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_BODY_TEXT, item.getTextBody());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_WEB_URL, item.getWebUrl());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_THUMBNAIL, item.getThumbnailUrl());
        return contentValues;
    }


    public void buttonStatusChanger(boolean status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mReadmeLatterStatus) {
                mFavButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate_pink, getContext().getTheme()));
            } else {
                mFavButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fab, getContext().getTheme()));
            }
        } else {
            if (mReadmeLatterStatus) {
                mFavButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate_pink));
            } else {
                mFavButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fab));
            }
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        String url;
        url = mItem.getThumbnailUrl();
        UrlManager urlGenerator = new UrlManager();
        LoaderBitMapsAsync async = null;
        if (url != null) {
            if (id == 2) {
                async = new LoaderBitMapsAsync(getActivity(), urlGenerator.GenerateURLByElement(UrlManager.ELEMENT_TYPE_IMAGE, url, 0, false));
            } else {
                return new CursorLoader(getActivity(), TheGuardianContact.ReadMeLatter.CONTENT_URI, null, TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID + "=?", new String[]{mItem.getNewId()}, null);
            }
        }
        return async;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        if (data != null) {
            if (loader.getId() == 2) {
                mImageTumb.setImageBitmap((Bitmap) data);
            } else {
                Cursor dataList = (Cursor) data;
                if (dataList.moveToFirst()) {
                    mReadmeLatterStatus = true;
                } else {
                    mReadmeLatterStatus = false;
                }
                buttonStatusChanger(mReadmeLatterStatus);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
