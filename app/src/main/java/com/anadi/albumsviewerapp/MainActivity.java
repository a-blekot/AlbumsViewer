package com.anadi.albumsviewerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.albumsviewerapp.adapters.AlbumAdapter;
import com.anadi.albumsviewerapp.data.Album;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements AlbumAdapter.OnAlbumSelectedListener {
//    /user/albums
//    /user/photos
    // TODO endpoints

    private static final int RESULT_MAIN_ACTIVITY = 1;

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Album> albums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If MainActivity is reached without the user being logged in,
        // redirect to the Login Activity
        if (!isLoggedIn()) {
            startActivity(new Intent(this, FacebookLoginActivity.class));
        }

        recyclerView = findViewById(R.id.recycler_view_main);

        adapter = new AlbumAdapter(this, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Button fbLogoutButton = findViewById(R.id.btn_fb_logout);
        fbLogoutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginManager.getInstance().logOut();
                        Intent loginIntent = new Intent(MainActivity.this, FacebookLoginActivity.class);
                        startActivityForResult(loginIntent, RESULT_MAIN_ACTIVITY);
                    }
                });

        //FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        loadAlbumsFromFB();
        updateAlbums(findViewById(R.id.update_albums_btn));

        Timber.d("MY_LOG onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_MAIN_ACTIVITY && resultCode == RESULT_OK) {
            loadAlbumsFromFB();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSelected(Album album) {
        if (album == null) {
            Timber.d("MY_LOG album == null !!!");
            return;
        }

        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra("id", album.getId());
        intent.putExtra("name", album.getName());
        intent.putExtra("image", album.getImage());
        startActivity(intent);
    }

    public void updateAlbums(View view) {
        adapter.setData(albums);
        Toast.makeText(this, "Update albums!", Toast.LENGTH_SHORT).show();
    }

    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return (accessToken != null) && !accessToken.isExpired();
    }

    private void loadAlbumsFromFB() {

        /**
         Graph API method for reading the album data
         Visit https://developers.facebook.com/docs/graph-api/reference/v2.5/album/photos  for more info
         **/

//        AccessToken accessToken = new AccessToken(getString(R.string.access_token),
//                getString(R.string.facebook_app_id),
//                getString(R.string.aleksey_blekot_fb_id),
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);


        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if (object == null) {
                            Timber.d("MY_LOG object == null");
                            Timber.d("MY_LOG newMeRequest Response %s", response.toString());
                            return;
                        }

                        Timber.d("MY_LOG newMeRequest Response %s", response.toString());

                        try {
                            JSONObject obj = object.getJSONObject("albums");
                            JSONArray jArray = obj.getJSONArray("data");
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject data = jArray.getJSONObject(i);

//                                data.getString("cover_photo")

                                String id = data.getString("id");
                                String name = data.getString("name");
//                                thumbnail, small, album
                                String url = String.format("https://graph.facebook.com/%s/picture?type=album&access_token=%s",
                                        id, AccessToken.getCurrentAccessToken().getToken());

                                Album album = new Album(id, name, url);


                                Timber.d("MY_LOG new %s", album);

                                albums.add(album);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.setData(albums);
                    }
                });

//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link");
//        request1.setParameters(parameters);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{id,cover_photo,name}");
        request.setParameters(parameters);
        request.executeAsync();


        // USER INFO TODO user info
//        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                // Get facebook data from login
//                try {
//                    Bundle bundle = new Bundle();
//                    String id = object.getString("id");
//
//                    URL profile_pic = null;
//                    try {
//                        profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
//                        bundle.putString("profile_pic", profile_pic.toString());
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//
//                    String firstName = object.getString("first_name");
//                    String lastName = object.getString("last_name");
//
//                } catch(JSONException e) {
//                    Log.d("FB","Error parsing JSON");
//                }
//            }
//        });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id, first_name, last_name"); // Parámetros que pedimos a facebook
//        request.setParameters(parameters);
//        request.executeAsync();




    }
}