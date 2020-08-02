package com.anadi.albumsviewerapp.model;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Photo {
    public static final String DEFAULT_NAME = "NO NAME";

    private String id;
    private String name;
    private String album;
    private String createdTime;
    private int width;
    private int height;
    private String link;

    public Photo(String id, String name, String album, String createdTime, int width, int height, String link) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.createdTime = createdTime;
        this.width = width;
        this.height = height;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getLink() {
        return link;
    }

    public String getNameDetails() {
        return String.format(Locale.UK, "%-8s : %s", "Name",
                name.isEmpty() ? DEFAULT_NAME : name);
    }

    public String getAlbumDetails() {
        return String.format(Locale.UK, "%-8s : %s", "Album", album);
    }

    public String getCreatedTimeDetails() {
        return String.format(Locale.UK, "%-8s : %s", "Created", createdTime);
    }

    public String getSizeDetails() {
        return String.format(Locale.UK, "%-11s : %d * %d", "Size", width, height);
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder builder = new StringBuilder(300);
        builder.append("Photo id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", album=");
        builder.append(album);
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", width=");
        builder.append(width);
        builder.append(", height=");
        builder.append(height);
        builder.append(", link=");
        builder.append(link);

        return builder.toString();
    }
}
