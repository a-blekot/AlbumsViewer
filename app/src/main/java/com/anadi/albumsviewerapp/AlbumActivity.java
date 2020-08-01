package com.anadi.albumsviewerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.adapters.PhotoAdapter;
import com.anadi.albumsviewerapp.data.Photo;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import timber.log.Timber;

public class AlbumActivity extends AppCompatActivity implements PhotoAdapter.OnPhotoSelectedListener {

    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Photo> photos = new ArrayList<>();

    private String albumId;
    private String albumName;
    private String albumImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        if (intent != null) {
            albumId = intent.getStringExtra("id");
            albumName = intent.getStringExtra("name");
            albumImage = intent.getStringExtra("image");
        } else {
            Timber.d("MY_LOG Why Intent is empty!?");
        }

        recyclerView = findViewById(R.id.recycler_view_album);

        adapter = new PhotoAdapter(this, this);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        loadPhotos();
    }

    public void updatePhotos(View view) {
        adapter.setData(photos);
        Toast.makeText(this, "Update photos in album!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelected(Photo photo) {
        if (photo == null) {
            Timber.d("MY_LOG album == null !!!");
            return;
        }

        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("name", photo.getName());
        intent.putExtra("image", photo.getImage());
        startActivity(intent);
    }

    private void loadPhotos() {


        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                String.format("/%s/photos", albumId),
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Timber.d("MY_LOG Response %s", response.toString());

                        try {
                            JSONArray jsonPhotos = response.getJSONObject().getJSONArray("data");

                            /**Parse json array, search for "name" and "id"**/
                            for (int i = 0; i < jsonPhotos.length(); i++) {
                                JSONObject jsonFBPhoto = jsonPhotos.getJSONObject(i);


                                String id = jsonFBPhoto.getString("id");
                                String name = jsonFBPhoto.getString("name");
//                                thumbnail, album, normal
                                String url = String.format("https://graph.facebook.com/%s/picture?type=normal&access_token=%s",
                                        id, AccessToken.getCurrentAccessToken().getToken());

                                photos.add(new Photo(id, name, url));
                            }

                        } catch (Exception e) {
                            Timber.d("MY_LOG%s", e.getMessage());
                            e.printStackTrace();
                        }

                        adapter.setData(photos);
                    }
                });

//        FB.api(albumId + '/?fields=photos.limit(10){picture,images}', response => {
//                console.log(response);
//});

//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "photos{id,cover_photo,name}");
//        request.setParameters(parameters);
//        request.executeAsync();


        request.executeAsync();


//        GraphRequest request1 = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//
//                        if (object == null) {
//                            Timber.d("MY_LOG object == null");
//                            Timber.d("MY_LOG newMeRequest Response %s", response.toString());
//                            return;
//                        }
//
//                        Timber.d("MY_LOG newMeRequest Response %s", response.toString());
//
//                        Timber.d("MY_LOG whole JSON object: ================================================");
//                        Timber.d("MY_LOG ===================================================================");
//                        Timber.d(object.toString());
//
//                        try {
//                            JSONObject obj = object.getJSONObject("albums");
//                            JSONArray jArray = obj.getJSONArray("data");
//                            for (int i = 0; i < jArray.length(); i++) {
//                                JSONObject data = jArray.getJSONObject(i);
//
////                                data.getString("cover_photo")
//
//                                String id = data.getString("id");
//                                String url = "https://graph.facebook.com/" +
//                                        id +
//                                        "/picture?type=album" +
//                                        "&access_token=" +
//                                        AccessToken.getCurrentAccessToken().getToken();
//
//                                Photo photo = new Photo(
//                                        id,
//                                        data.getString("name"),
//                                        url);
//
//
//                                Timber.d("MY_LOG new %s", photo);
//
//                                photos.add(photo);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        adapter.setData(photos);
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "albums{id,cover_photo,name}");
//        request1.setParameters(parameters);
//        request1.executeAsync();
    }
}