package com.example.mvvmrestapi.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.example.mvvmrestapi.model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostAPI {
    @GET("posts")
    Call<List<Post>> getAllPosts();

    @GET("Posts")
    Call<List<Post>> getPostsByUserId(@Query("userId") int userId);
//
//    @GET("posts")
//    Call<List<Post>> getPostByParams(@QueryMap Map<String, String> parameters);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @PUT("posts")
    Call<Post> updatePost(@Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
