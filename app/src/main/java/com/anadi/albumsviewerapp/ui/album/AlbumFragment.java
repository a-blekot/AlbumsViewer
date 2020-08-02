package com.anadi.albumsviewerapp.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Album;
import com.anadi.albumsviewerapp.util.OnItemSelectedListener;

import java.util.List;

public class AlbumFragment extends Fragment {
    private AlbumAdapter adapter;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AlbumAdapter((OnItemSelectedListener) getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_main);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AlbumViewModel model = new ViewModelProvider(getActivity(), new AlbumViewModelFactory()).get(AlbumViewModel.class);
        model.getAlbums().observe(getActivity(), new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                adapter.setData(albums);
            }
        });
    }
}