package com.anadi.albumsviewerapp.ui.album;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anadi.albumsviewerapp.model.Album;
import com.anadi.albumsviewerapp.model.GraphRepository;
import com.anadi.albumsviewerapp.util.ProgressState;

import java.util.ArrayList;
import java.util.List;

public class AlbumViewModel extends ViewModel implements GraphRepository.AlbumsListener {
    private GraphRepository repository;
    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<ProgressState> state;

    public AlbumViewModel(GraphRepository repo) {
        repository = repo;
        albums = new MutableLiveData<>();
        state = new MutableLiveData<>(ProgressState.IDLE);
        loadAlbums();
    }

    public void clear() {
        state.setValue(ProgressState.IDLE);
        albums.setValue(new ArrayList<Album>());
    }

    public LiveData<List<Album>> getAlbums() {
        return albums;
    }

    public LiveData<ProgressState> getState() {
        return state;
    }

    public void loadAlbums() {
        state.setValue(ProgressState.LOADING);
        repository.loadAlbumsFromFB(this);
    }

    @Override
    public void onLoaded(List<Album> value) {
        state.postValue(ProgressState.IDLE);
        albums.postValue(value);
    }
}