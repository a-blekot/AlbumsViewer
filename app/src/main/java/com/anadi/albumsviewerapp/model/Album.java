package com.anadi.albumsviewerapp.model;

import androidx.annotation.NonNull;

public class Album {
    private String id;
    private String name;
    private String link;

    public Album(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Album %s, %s, %s", id, name, link);
    }
}
