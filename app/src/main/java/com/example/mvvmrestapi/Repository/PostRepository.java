package com.example.mvvmrestapi.Repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmrestapi.Interface.PostAPI;
import com.example.mvvmrestapi.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {
    private PostAPI postAPI;
    private Application application;
    final MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public PostRepository(@NonNull Application application) {
        this.application = application;
        //initialize gson instance to serialize null data field
        Gson gson = new GsonBuilder().serializeNulls().create();

        //initialize retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // implement postAPI interface
        postAPI = retrofit.create(PostAPI.class);

    }

    public LiveData<List<Post>> getAllPosts (){
        Call<List<Post>> call = postAPI.getAllPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    posts.postValue(response.body());
                }else {
                    Toast.makeText(application,"fail to retrieve ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(application,"fail to retrieve ",Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });

        return posts;
    }

    public LiveData<List<Post>> getPostsByUserId(int userId){
        Call<List<Post>> call = postAPI.getPostsByUserId(userId);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts.postValue(response.body());
                }else{
                    Toast.makeText(application,"fail to retrieve ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(application,"fail to retrieve ",Toast.LENGTH_SHORT).show();
            }
        });
        return posts;
    }

    public void createPost(Post post){
        Call<Post> call = postAPI.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(application,"Post Created!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application,"Fail to Create Post",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(application,"Fail to Create Post",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePost(Post post){
        Call<Post> call = postAPI.updatePost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(application,"Post Updated!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application,"Fail to Update Post",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(application,"Fail to update Post",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletePost(Post post){
        int id = post.getId();
        Call<Void> call = postAPI.deletePost(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(application,"Post Deleted!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application,"Fail to Updated Post",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application,"Fail to Updated Post",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
