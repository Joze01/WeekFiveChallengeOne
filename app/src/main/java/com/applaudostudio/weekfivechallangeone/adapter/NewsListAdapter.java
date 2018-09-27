package com.applaudostudio.weekfivechallangeone.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudostudio.weekfivechallangeone.R;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;


import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.RadioViewHolder> {
    private List<ItemNews> mDataSet;
    private final ItemSelectedListener mCallback;

    /***
     * Constructor to set data set and a callback for the SelectedItem listener
     * @param mDataSet Data with the news items
     * @param callback callback for the item selected.
     */
    public NewsListAdapter(List<ItemNews> mDataSet, ItemSelectedListener callback) {
        this.mDataSet = mDataSet;
        mCallback=callback;
    }

    /***
     * Constructor for the view Holder of the recyclerview
     * @param parent the parent viewGroup
     * @param viewType Type of view to be render
     * @return returns a RadioViewHolder
     */
    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist, parent, false);
        return new RadioViewHolder(view);
    }

    /***
     * bindin for the view holder
     * @param viewHolder view holder for news Adapter
     * @param i item index
     */
    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.RadioViewHolder viewHolder, int i) {
        viewHolder.bindData(mDataSet.get(i));
    }

    /***
     * function to get the data set size.
     * @return  number of item of the dataset
     */
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    /***
     * Class for the news View holder
     */
    class RadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImagenDownload;
        private TextView mTxtHeadline;
        private ConstraintLayout mItemElements;
        RadioViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemElements = itemView.findViewById(R.id.containerList);
            mTxtHeadline=itemView.findViewById(R.id.textViewHeadline);
            mImagenDownload=itemView.findViewById(R.id.imageViewThumbnail);

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
                        mCallback.onClickNewsDetail(mDataSet.get(getAdapterPosition()));
                    }
                    break;
            }
        }

        /***
         * Function to bind the data to the view element
         * @param item
         */
        private void bindData(ItemNews item){
            mTxtHeadline.setText(item.getmTitle());
        }

    }

    /***
     * Interface for the click on the items to send the data to the activity
     */
    public interface ItemSelectedListener {
        void onClickNewsDetail(ItemNews item);
    }


}
