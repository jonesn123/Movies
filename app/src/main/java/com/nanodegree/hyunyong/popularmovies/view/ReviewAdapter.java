package com.nanodegree.hyunyong.popularmovies.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.hyunyong.popularmovies.R;
import com.nanodegree.hyunyong.popularmovies.data.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> mReview;
    private final ReviewAdapterOnClickHandler mClickListener;

    public ReviewAdapter(List<Review> reviews, ReviewAdapterOnClickHandler listener) {
        mReview = reviews;
        mClickListener = listener;
    }

    public interface ReviewAdapterOnClickHandler {
        void onClick(String outLinkUrl);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Review review =  mReview.get(position);
        viewHolder.mAuthor.setText(review.getAuthor());
        viewHolder.mContent.setText(review.getContent());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(review.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mReview) return 0;
        return mReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mContent = itemView.findViewById(R.id.content);
        }
    }
}
