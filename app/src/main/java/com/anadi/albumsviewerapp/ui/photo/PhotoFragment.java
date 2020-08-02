package com.anadi.albumsviewerapp.ui.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Photo;
import com.anadi.albumsviewerapp.util.OnItemSelectedListener;

import java.util.List;

public class PhotoFragment extends Fragment {
    private PhotoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PhotoAdapter((OnItemSelectedListener) getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_album);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        PhotoViewModel model = new ViewModelProvider(getActivity(), new PhotoViewModelFactory()).get(PhotoViewModel.class);
        model.getPhotos().observe(getActivity(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                adapter.setData(photos);
            }
        });
    }
}