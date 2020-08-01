package com.anadi.albumsviewerapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.data.Album;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private OnAlbumSelectedListener listener;

    //    private MainActivityContract.Presenter presenter;
    private ArrayList<Album> albums = new ArrayList<>();

    //    public AlbumAdapter(Context context, OnAlbumSelectedListener listener, MainActivityContract.Presenter presenter) {
    public AlbumAdapter(Context context, OnAlbumSelectedListener listener) {
        this.listener = listener;
//        this.presenter = presenter;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);

        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = albums.get(position);

        holder.name.setText(album.getName());

//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
        holder.image.setImageURI(album.getImage());
//        Picasso.get().load(album.getImage()).into(holder.image);
//        holder.icon.setImageURI(Uri.parse(album.getImage()));

        holder.containerView.setTag(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setData(ArrayList<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public interface OnAlbumSelectedListener {
        void onSelected(Album album);
    }

    class AlbumHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public RelativeLayout containerView;
        public SimpleDraweeView image;
        public TextView name;

        AlbumHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.album_item);
            containerView.setOnClickListener(this);

            image = itemView.findViewById(R.id.album_item_image);
            name = itemView.findViewById(R.id.album_item_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onSelected(albums.get(position));
        }
    }
}
