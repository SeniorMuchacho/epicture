package org.gradle.epicture;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*
        Pour récupérer les infos comme accessToken, etc
        SharedPreferences MyToken = this.getSharedPreferences("PREFS", this.MODE_PRIVATE);
        String accessToken = MyToken.getString("username", "");
        */
        fetchData();
    }

    private void fetchData() {
        httpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/hot/viral/0.json")
                .header("Authorization","Client-ID 8be4c924d7a92be")
                .header("User-Agent","Epicture")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("callBack", "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject data = null;
                try {
                    data = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray items = null;
                try {
                    items = data.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final List<Photo> photos = new ArrayList<Photo>();

                for(int i=0; i<items.length();i++) {
                    JSONObject item = null;
                    try {
                        item = items.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Photo photo = new Photo();
                    try {
                        if(item.getBoolean("is_album")) {
                            photo.id = item.getString("cover");
                        } else {
                            photo.id = item.getString("id");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        photo.title = item.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    photos.add(photo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        render(photos);
                    }
                });
            }
        });

    }

    private void render(final List<Photo> photos) {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.get().load("https://i.imgur.com/" +
                        photos.get(position).id + ".jpg").into(holder.photo);
                holder.title.setText(photos.get(position).title);
            }

            @Override
            public int getItemCount() {
                return photos.size();
            }
        };
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 30;
            }
        });
        rv.setAdapter(adapter);

    }

    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;

        public PhotoVH(View itemView) {
            super(itemView);
        }
    }
    private static class Photo {
        String id;
        String title;
    }

}