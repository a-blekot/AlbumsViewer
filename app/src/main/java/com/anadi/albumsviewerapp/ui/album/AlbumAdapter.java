package com.anadi.albumsviewerapp.ui.album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Album;
import com.anadi.albumsviewerapp.util.OnItemSelectedListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private OnItemSelectedListener listener;

    private List<Album> albums = new ArrayList<>();

    public AlbumAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
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
        holder.image.setImageURI(album.getLink());
        holder.image.getHierarchy().setProgressBarImage(new ProgressBarDrawable());

        holder.containerView.setTag(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setData(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    class AlbumHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public FrameLayout containerView;
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

            if (position != RecyclerView.NO_POSITION) {
                listener.onAlbumSelected(albums.get(position).getId());
            }
        }
    }
}
