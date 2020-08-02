package com.anadi.albumsviewerapp.ui.photo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Photo;
import com.anadi.albumsviewerapp.util.OnItemSelectedListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private OnItemSelectedListener listener;

    private List<Photo> photos = new ArrayList<>();

    public PhotoAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);

        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo photo = photos.get(position);

        if (photo.getName().isEmpty())
            holder.name.setVisibility(View.GONE);
        else {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(photo.getName());
        }

        holder.time.setText(photo.getCreatedTime());

        holder.image.setImageURI(photo.getLink());
        holder.image.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
        holder.containerView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setData(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    class PhotoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public RelativeLayout containerView;
        public SimpleDraweeView image;
        public TextView name;
        public TextView time;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.photo_item);
            containerView.setOnClickListener(this);

            image = itemView.findViewById(R.id.photo_item_image);
            name = itemView.findViewById(R.id.photo_item_name);
            time = itemView.findViewById(R.id.photo_item_time);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                listener.onPhotoSelected(position);
            }
        }
    }
}
