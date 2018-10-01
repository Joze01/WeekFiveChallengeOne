package com.applaudostudio.weekfivechallangeone.adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.loader.apiclient.GuardianApiClient;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;
import com.applaudostudio.weekfivechallangeone.util.UrlManager;

import java.util.List;

/***
 * Adapter for the recycler view
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private List<ItemNews> mDataSet;
    private final ItemSelectedListener mCallback;

    /***
     * Constructor to set data set and a callback for the SelectedItem listener
     * @param mDataSet Data with the news items
     * @param callback callback for the item selected.
     */
    public NewsListAdapter(List<ItemNews> mDataSet, ItemSelectedListener callback) {
        this.mDataSet = mDataSet;
        mCallback = callback;
    }

    /***
     * Constructor for the view Holder of the recycler view
     * @param parent the parent viewGroup
     * @param viewType Type of view to be render
     * @return returns a RadioViewHolder
     */
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, parent, false);
        return new NewsViewHolder(view);
    }

    /***
     * bindin for the view holder
     * @param viewHolder view holder for news Adapter
     * @param i item index
     */
    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.NewsViewHolder viewHolder, int i) {
        viewHolder.bindData(mDataSet.get(i));
    }

    /***
     * function to get the data set size.
     * @return number of item of the data set
     */
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    /***
     * Class for the news View holder
     */
    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImagenDownload;
        private TextView mTxtHeadline;
        private ConstraintLayout mItemElements;
        //private Bitmap mImagenMap;
        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemElements = itemView.findViewById(R.id.containerList);
            mTxtHeadline = itemView.findViewById(R.id.textViewHeadline);
            mImagenDownload = itemView.findViewById(R.id.imageViewThumbnail);
            mItemElements.setOnClickListener(this);
        }

        /***
         * On click for all the container
         * @param view the whole view as parameter
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.containerList:
                    if (mCallback != null) {
                        //mDataSet.get(getAdapterPosition()).setImageMap(mImagenMap);
                        mCallback.onClickNewsDetail(mDataSet.get(getAdapterPosition()));
                    }
                    break;
            }
        }

        /***
         * Function to bind the data to the view element
         * @param item item news
         */
        private void bindData(ItemNews item) {
            mTxtHeadline.setText(item.getTitle());
            mImagenDownload.setImageResource(R.drawable.ic_launcher_background);
            new AsyncLoadImage().execute(item.getThumbnailUrl());
        }

        //Async task to load each image
        class AsyncLoadImage extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... strings) {
                UrlManager urlManager = new UrlManager();
                GuardianApiClient client = new GuardianApiClient();
                DataInterpreter interpreter = new DataInterpreter();

                return interpreter.streamToBitMap(client.makeHttpRequest(urlManager.GenerateURLByElement(UrlManager.ELEMENT_TYPE_IMAGE, strings[0], 0, false)));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //mImagenMap=bitmap;
                mImagenDownload.setImageBitmap(bitmap);
            }
        }
    }

    /***
     * Interface for the click on the items to send the data to the activity
     */
    public interface ItemSelectedListener {
        void onClickNewsDetail(ItemNews item);
    }


}
