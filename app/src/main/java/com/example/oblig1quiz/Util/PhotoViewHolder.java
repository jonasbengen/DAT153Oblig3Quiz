package com.example.oblig1quiz.Util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.R;

/*
    Class that describes an item in the RecyclerView
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //private final TextView photoItemView;
    private final ImageView imageView;
    private final TextView nameTextView;

    private PhotoViewHolder(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.deleteButton).setOnClickListener((this));
        imageView = itemView.findViewById(R.id.imageView2);
        nameTextView = itemView.findViewById(R.id.nameTextView);
    }


    public void bind(PhotoInfo photoInfo) {
        imageView.setImageURI(Uri.parse(photoInfo.getUri()));
        nameTextView.setText(photoInfo.getName());
    }

    static PhotoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onClick(View v) {showDeleteDialog(getAdapterPosition());}

    // Show dialog for confirming image deletion
    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(imageView.getContext())
                .setTitle("Do you want to delete?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhotoViewModel photoViewModel = new ViewModelProvider((GalleryActivity) itemView.getContext()).get(PhotoViewModel.class);
                        PhotoInfo photo = photoViewModel.getPhotoAtPosition(position);
                        photoViewModel.delete(photo);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

}
