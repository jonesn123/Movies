package com.nanodegree.hyunyong.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;

import java.util.List;

public class PopMovieAdapter extends RecyclerView.Adapter<PopMovieAdapter.ViewHolder> {

    private List<String> mImageUrl;
    private final MovieAdapterOnClickHandelr mClickHandler;

    public interface MovieAdapterOnClickHandelr {
        void onClick();
    }

    public PopMovieAdapter(List<String> items, MovieAdapterOnClickHandelr clickHandelr) {
        mImageUrl = items;
        mClickHandler = clickHandelr;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick();
        }
    }

    @NonNull
    @Override
    public PopMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopMovieAdapter.ViewHolder viewHolder, int position) {
        String movieImageUrl = mImageUrl.get(position);
        ImageView imageView = viewHolder.mImageView;
        Context context = imageView.getContext();
        String imageUrl = NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_185, movieImageUrl);
        Glide.with(context).load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (null == mImageUrl) return 0;
        return mImageUrl.size();
    }
}