package com.example.mirest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirest.modals.PlanetaryApod;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "jILLmQa009rsMMC8BMmEfLhHz3MrcLsmC0PSLZPw";
    private TextView mJsonText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJsonText = findViewById(R.id.jsonPosts);
        imageView = findViewById(R.id.image_view);
        getPosts();
        getPlanetary();
    }

    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService postService = retrofit.create(PostService.class);

        Call<List<Posts>> call = postService.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(!response.isSuccessful()){
                    mJsonText.setText("Codigo" + response.code());
                    return;
                }

                List<Posts> postsLists = response.body();

                String content = "";
                for(Posts post: postsLists){
                    content += "userId :"+ post.getUserId() + "\n";
                    content += "Id :"+ post.getId() + "\n";
                    content += "title :"+ post.getTitle() + "\n";
                    content += "body :"+ post.getBody() + "\n";
                }
                mJsonText.setText(content);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });
    }
    private void getPlanetary(){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService postService = retrofit.create(PostService.class);

        Call<PlanetaryApod> call = postService.getDayPlanetary(API_KEY);

        call.enqueue(new Callback<PlanetaryApod>() {
            @Override
            public void onResponse(Call<PlanetaryApod> call, Response<PlanetaryApod> response) {
                if(!response.isSuccessful()){
                    mJsonText.setText("Codigo" + response.code());
                    return;
                }

                PlanetaryApod postsLists = response.body();

                String content = "";
                content += "userId :"+ postsLists.getTitle() + "\n";
                content += "Id :"+ postsLists.getExplanation() + "\n";
                content += "url :"+ postsLists.getUrl() + "\n";
                mJsonText.setText(content);
                Picasso.get().load(postsLists.getUrl()).into(imageView);
            }

            @Override
            public void onFailure(Call<PlanetaryApod> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });
    }
}
