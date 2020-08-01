package com.anadi.albumsviewerapp.data;

import androidx.annotation.NonNull;

public class Album {
    private static final String DEFAULT_NAME = "NO NAME";
    private static final String DEFAULT_IMAGE = "NO IMAGE";

    private String id;
    private String name;
    private String image;

    public Album(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Album %s, %s, %s", id, name, image);
    }
}
