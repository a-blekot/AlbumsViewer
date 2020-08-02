package com.anadi.albumsviewerapp.model;

import android.os.Bundle;
import android.util.Pair;

import com.anadi.albumsviewerapp.util.TimeConverter;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class GraphRepository {

    private static volatile GraphRepository instance;
    // simple Local storage implementation =)
    private Map<AccessToken, List<Album>> cashedAlbums =
            Collections.synchronizedMap(new HashMap<AccessToken, List<Album>>());
    // photos need albumId in addition to AccessToken
    private Map<Pair<AccessToken, String>, List<Photo>> cashedPhotos =
            Collections.synchronizedMap(new HashMap<Pair<AccessToken, String>, List<Photo>>());

    private GraphRepository() {
    }

    // Singleton with double check
    public static GraphRepository getInstance() {
        GraphRepository result = instance;
        if (result == null) {
            synchronized (GraphRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new GraphRepository();
                }
            }
        }
        return result;
    }

    public void loadAlbumsFromFB(final AlbumsListener listener) {

        if (cashedAlbums.containsKey(AccessToken.getCurrentAccessToken())) {
            listener.onLoaded(cashedAlbums.get(AccessToken.getCurrentAccessToken()));
            return;
        }

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if (object == null) {
                            Timber.d("Empty albums response %s", response.toString());
                            return;
                        }

                        List<Album> albums = new ArrayList<>();

                        try {
                            JSONObject obj = object.getJSONObject("albums");
                            JSONArray jArray = obj.getJSONArray("data");
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject data = jArray.getJSONObject(i);

//                                data.getString("cover_photo")

                                String id = data.getString("id");
                                String name = data.optString("name");
//                                thumbnail, small, album
                                String url = String.format("https://graph.facebook.com/%s/picture?type=album&access_token=%s",
                                        id, AccessToken.getCurrentAccessToken().getToken());

                                Album album = new Album(id, name, url);

                                albums.add(album);
                            }

                        } catch (JSONException e) {
                            Timber.d(e);
                            Timber.d(Arrays.toString(e.getStackTrace()));
                        }

                        cashedAlbums.put(AccessToken.getCurrentAccessToken(), albums);
                        listener.onLoaded(albums);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{id,cover_photo,name}");
        request.setParameters(parameters);

        request.executeAsync();
    }

    public void loadPhotosFromFB(final PhotosListener listener, final String albumId) {

        final Pair<AccessToken, String> pair = new Pair<>(AccessToken.getCurrentAccessToken(), albumId);

        if (cashedPhotos.containsKey(pair)) {
            listener.onLoaded(cashedPhotos.get(pair));
            return;
        }

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                String.format("/%s/photos", albumId),
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        List<Photo> photos = new ArrayList<>();


                        if (response.getJSONObject() == null) {
                            Timber.d("Empty photo response %s", response.toString());
                            return;
                        }

                        try {
                            JSONArray jsonPhotos = response.getJSONObject().getJSONArray("data");

                            // Parse json array, search for id, name, album, created_time, size
                            for (int i = 0; i < jsonPhotos.length(); i++) {
                                JSONObject jsonFBPhoto = jsonPhotos.getJSONObject(i);


                                String id = jsonFBPhoto.getString("id");
                                String name = jsonFBPhoto.optString("name");
                                JSONObject album = jsonFBPhoto.getJSONObject("album");
                                String albumName = album.optString("name");
                                String createdTime = jsonFBPhoto.optString("created_time");
                                createdTime = TimeConverter.convert(createdTime);

                                int width = jsonFBPhoto.getInt("width");
                                int height = jsonFBPhoto.getInt("height");

                                // thumbnail, album, normal
                                String link = String.format("https://graph.facebook.com/%s/picture?type=normal&access_token=%s",
                                        id, AccessToken.getCurrentAccessToken().getToken());

                                Photo photo = new Photo(id, name, albumName, createdTime, width, height, link);

                                photos.add(photo);
                            }

                        } catch (Exception e) {
                            Timber.d(e);
                            Timber.d(Arrays.toString(e.getStackTrace()));
                        }

                        cashedPhotos.put(pair, photos);
                        listener.onLoaded(photos);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,album,name,created_time,width,height,place");
        request.setParameters(parameters);

        request.executeAsync();
    }

    public interface AlbumsListener {
        void onLoaded(List<Album> albums);
    }

    public interface PhotosListener {
        void onLoaded(List<Photo> photos);
    }
}
