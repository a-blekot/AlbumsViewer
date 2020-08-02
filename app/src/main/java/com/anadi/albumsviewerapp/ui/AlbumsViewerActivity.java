package com.anadi.albumsviewerapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.ui.album.AlbumViewModel;
import com.anadi.albumsviewerapp.ui.album.AlbumViewModelFactory;
import com.anadi.albumsviewerapp.ui.photo.PhotoViewModel;
import com.anadi.albumsviewerapp.ui.photo.PhotoViewModelFactory;
import com.anadi.albumsviewerapp.util.OnItemSelectedListener;
import com.anadi.albumsviewerapp.util.ProgressState;

public class AlbumsViewerActivity extends AppCompatActivity implements OnItemSelectedListener {
    private Observer<ProgressState> progressStateObserver;

    private NavController navController;
    private ViewModelProvider photoProvider;
    private ViewModelProvider albumProvider;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressStateObserver = new Observer<ProgressState>() {
            @Override
            public void onChanged(ProgressState state) {
                setProgressState(state);
            }
        };

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        photoProvider = new ViewModelProvider(this, new PhotoViewModelFactory());
        albumProvider = new ViewModelProvider(this, new AlbumViewModelFactory());

        progressBar = findViewById(R.id.progress);

        albumProvider.get(AlbumViewModel.class).getState().observe(this, progressStateObserver);
        photoProvider.get(PhotoViewModel.class).getState().observe(this, progressStateObserver);
    }

    @Override
    public void onAlbumSelected(String id) {
        photoProvider.get(PhotoViewModel.class).setCurrentAlbum(id);
        navController.navigate(R.id.action_album_to_photo);
    }

    @Override
    public void onPhotoSelected(int index) {
        photoProvider.get(PhotoViewModel.class).setCurrentPhoto(index);
        navController.navigate(R.id.action_photo_to_fullscreen);
    }

    private void setProgressState(ProgressState state) {
        switch (state) {
            case IDLE:
                progressBar.setVisibility(View.GONE);
                break;
            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }
}