package com.example.mvvmrestapi.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmrestapi.Repository.PostRepository;
import com.example.mvvmrestapi.model.Post;

import java.util.List;

public class PostsVM extends AndroidViewModel {
    private PostRepository postRepository;
    private LiveData<List<Post>> allPosts;

    public PostsVM(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        allPosts = postRepository.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        return postRepository.getAllPosts();
    }

    public LiveData<List<Post>> getPostByUserId(int userId){
        return postRepository.getPostsByUserId(userId);
    }

    public void createPost(Post post){
        postRepository.createPost(post);
    }

    public void updatePost(Post post){
        postRepository.updatePost(post);
    }

    public void deletePost(Post post){
        postRepository.deletePost(post);
    }

}
