package com.anadi.albumsviewerapp.ui.fullscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Photo;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.PhotoHolder> {

    private List<Photo> photos = new ArrayList<>();

    @NonNull
    @Override
    public FullScreenAdapter.PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.big_photo_item, parent, false);

        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo photo = photos.get(position);

        holder.image.setImageURI(photo.getLink());
        holder.image.getHierarchy().setProgressBarImage(new ProgressBarDrawable());

        holder.name.setText(photo.getNameDetails());
        holder.album.setText(photo.getAlbumDetails());
        holder.time.setText(photo.getCreatedTimeDetails());
        holder.size.setText(photo.getSizeDetails());

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

    static class PhotoHolder extends RecyclerView.ViewHolder {

        public RelativeLayout containerView;
        public SimpleDraweeView image;
        public TextView name;
        public TextView album;
        public TextView time;
        public TextView size;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.big_photo_item);

            image = itemView.findViewById(R.id.big_photo_image);
            name = itemView.findViewById(R.id.big_photo_name);
            album = itemView.findViewById(R.id.big_photo_album);
            time = itemView.findViewById(R.id.big_photo_time);
            size = itemView.findViewById(R.id.big_photo_size);
        }
    }
}