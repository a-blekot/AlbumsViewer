package com.anadi.albumsviewerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.data.Photo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private OnPhotoSelectedListener listener;

    //    private MainActivityContract.Presenter presenter;
    private ArrayList<Photo> photos = new ArrayList<>();

    //    public PhotoAdapter(Context context, OnAlbumSelectedListener listener, MainActivityContract.Presenter presenter) {
    public PhotoAdapter(Context context, OnPhotoSelectedListener listener) {
        this.listener = listener;
//        this.presenter = presenter;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);

        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo photo = photos.get(position);

        holder.name.setText(photo.getName());

//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
        holder.image.setImageURI(photo.getImage());
//        Picasso.get().load(album.getImage()).into(holder.image);
//        holder.icon.setImageURI(Uri.parse(album.getImage()));

        holder.containerView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setData(ArrayList<Photo> albums) {
        this.photos = albums;
        notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public interface OnPhotoSelectedListener {
        void onSelected(Photo album);
    }

    class PhotoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public RelativeLayout containerView;
        public SimpleDraweeView image;
        public TextView name;

        PhotoHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.album_item);
            containerView.setOnClickListener(this);

            image = itemView.findViewById(R.id.album_item_image);
            name = itemView.findViewById(R.id.album_item_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onSelected(photos.get(position));
        }
    }
}
