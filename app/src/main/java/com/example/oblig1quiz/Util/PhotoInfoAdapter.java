package com.example.oblig1quiz.Util;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/*
    Adapters provide a binding from an app-specific data set
    to views that are displayed within a RecyclerView.
 */
public class PhotoInfoAdapter extends ListAdapter<PhotoInfo, PhotoViewHolder> {

    public PhotoInfoAdapter(@NonNull DiffUtil.ItemCallback<PhotoInfo> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PhotoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoInfo current = getItem(position);
        holder.bind(current);
    }


    public static class PhotoDiff extends DiffUtil.ItemCallback<PhotoInfo> {

        @Override
        public boolean areItemsTheSame(@NonNull PhotoInfo oldItem, @NonNull PhotoInfo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PhotoInfo oldItem, @NonNull PhotoInfo newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getUri().equals(newItem.getUri());
        }

    }
}
