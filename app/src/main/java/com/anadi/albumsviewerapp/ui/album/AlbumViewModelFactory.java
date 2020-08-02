package com.anadi.albumsviewerapp.ui.album;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.anadi.albumsviewerapp.model.GraphRepository;

public class AlbumViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AlbumViewModel(GraphRepository.getInstance());
    }
}
