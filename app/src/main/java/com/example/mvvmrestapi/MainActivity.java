package com.example.mvvmrestapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.mvvmrestapi.Adapter.PostAapter;
import com.example.mvvmrestapi.ViewModel.PostsVM;
import com.example.mvvmrestapi.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PostsVM postsVM;
    public static final int ADD_POST_REQUEST = 1;
    public static final int Edit_POST_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.add_post);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditPostActivity.class);
                startActivityForResult(intent, ADD_POST_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAapter adapter = new PostAapter();
        recyclerView.setAdapter(adapter);

        postsVM = ViewModelProviders.of(this).get(PostsVM.class);
        postsVM.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                adapter.submitList(posts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                postsVM.deletePost(adapter.getPostAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PostAapter.onItemClickListener(){
            @Override
            public void onItemClick(Post post){
                Intent intent = new Intent(MainActivity.this, AddEditPostActivity.class);
                intent.putExtra(AddEditPostActivity.EXTRA_TITLE, post.getTitle());
                intent.putExtra(AddEditPostActivity.EXTRA_BODY, post.getBody());
                intent.putExtra(AddEditPostActivity.EXTRA_USERID, post.getUserId());
                intent.putExtra(AddEditPostActivity.EXTRA_ID, post.getId());
                startActivityForResult(intent, Edit_POST_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == ADD_POST_REQUEST && res == RESULT_OK) {
            int userid = data.getIntExtra(AddEditPostActivity.EXTRA_USERID,1);
            String title  = data.getStringExtra(AddEditPostActivity.EXTRA_BODY);
            String body = data.getStringExtra(AddEditPostActivity.EXTRA_BODY);

            Post new_post = new Post(userid,title,body);
            postsVM.createPost(new_post);

        }else if(req == Edit_POST_REQUEST && res == RESULT_OK){
            int id = data.getIntExtra(AddEditPostActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Can't update Post", Toast.LENGTH_SHORT ).show();
                return;
            }else{
                int userid = data.getIntExtra(AddEditPostActivity.EXTRA_USERID,1);
                String title  = data.getStringExtra(AddEditPostActivity.EXTRA_BODY);
                String body = data.getStringExtra(AddEditPostActivity.EXTRA_BODY);
                Post update_post = new Post(userid,title,body);
                update_post.setId(id);
                postsVM.updatePost(update_post);
            }
        }else{
            Toast.makeText(this, "Post not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    postsVM.getPostByUserId(Integer.parseInt(newText));
                }else{
                    postsVM.getAllPosts();
                }
                return false;
            }
        });
        return true;
    }


}
