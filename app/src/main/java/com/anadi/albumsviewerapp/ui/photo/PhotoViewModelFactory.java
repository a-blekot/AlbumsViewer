package com.anadi.albumsviewerapp.ui.photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.anadi.albumsviewerapp.model.GraphRepository;

public class PhotoViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoViewModel(GraphRepository.getInstance());
    }
}
