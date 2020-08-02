package com.anadi.albumsviewerapp.ui.photo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anadi.albumsviewerapp.model.GraphRepository;
import com.anadi.albumsviewerapp.model.Photo;
import com.anadi.albumsviewerapp.util.ProgressState;

import java.util.List;

public class PhotoViewModel extends ViewModel implements GraphRepository.PhotosListener {
    private GraphRepository repository;
    private MutableLiveData<List<Photo>> photos;
    private MutableLiveData<ProgressState> state;
    private MutableLiveData<Integer> currentPhoto;
    private String albumId;

    public PhotoViewModel(GraphRepository graphRepository) {
        repository = graphRepository;
        photos = new MutableLiveData<>();
        state = new MutableLiveData<>(ProgressState.IDLE);
        currentPhoto = new MutableLiveData<>(0);
        albumId = "";
    }

    public LiveData<List<Photo>> getPhotos() {
        loadPhotos(albumId);
        return photos;
    }

    public void loadPhotos(final String albumId) {
        state.setValue(ProgressState.LOADING);
        repository.loadPhotosFromFB(this, albumId);
    }

    @Override
    public void onLoaded(List<Photo> value) {
        state.setValue(ProgressState.IDLE);
        photos.postValue(value);
    }

    public LiveData<ProgressState> getState() {
        return state;
    }

    public LiveData<Integer> getCurrentPhoto() {
        return currentPhoto;
    }

    public void setCurrentPhoto(int index) {
        currentPhoto.setValue(index);
    }

    public void setCurrentAlbum(String albumId) {
        this.albumId = albumId;
    }
}