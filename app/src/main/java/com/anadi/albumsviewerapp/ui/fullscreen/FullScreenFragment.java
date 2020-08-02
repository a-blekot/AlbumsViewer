package com.anadi.albumsviewerapp.ui.fullscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.anadi.albumsviewerapp.R;
import com.anadi.albumsviewerapp.model.Photo;
import com.anadi.albumsviewerapp.ui.photo.PhotoViewModel;
import com.anadi.albumsviewerapp.ui.photo.PhotoViewModelFactory;

import java.util.List;

public class FullScreenFragment extends Fragment {

    private ViewPager2 viewPager;
    private FullScreenAdapter adapter;

    public FullScreenFragment() {
        // Required empty public constructor
    }

    public static FullScreenFragment newInstance() {
        return new FullScreenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FullScreenAdapter();

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        final PhotoViewModel model = new ViewModelProvider(getActivity(), new PhotoViewModelFactory()).get(PhotoViewModel.class);
        model.getPhotos().observe(getActivity(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                adapter.setData(photos);

                // it's obviously a workaround of bug:
                // without delay setCurrentItem() doesn't work properly
                viewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(model.getCurrentPhoto().getValue());
                    }
                }, 100);
            }
        });


    }
}