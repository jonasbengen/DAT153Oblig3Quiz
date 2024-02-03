package com.example.oblig1quiz.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1quiz.R;


// Class to show a image object in RecyclerView
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Datamanager dataManager;
    private Context context;

    public PhotoAdapter(Datamanager dataManager, Context context){
        this.dataManager = dataManager;
        this.context = context;
    }

    public Datamanager getDataManager() {
        return dataManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.bind(dataManager.getPhotolist().get(position));
    }

    @Override
    public int getItemCount() {
        return dataManager.getPhotolist().size();
    }

    public void delete(int position) { //removes the row
        dataManager.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView nameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.deleteButton).setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView2);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }

        public void bind(PhotoInfo photoInfo){
            imageView.setImageURI(photoInfo.getUri());
            nameTextView.setText(photoInfo.getName());
        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition());
        }
    }

}
