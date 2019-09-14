package com.example.mvvmrestapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Intent.EXTRA_TITLE;

public class AddEditPostActivity extends AppCompatActivity {
    public static final String EXTRA_USERID = "COM.EXAMPLE.MVVMRESTAPI.EXTRA.USERID";
    public static final String EXTRA_BODY = "COM.EXAMPLE.MVVMRESTAPI.EXTRA.BODY";
    public static final String EXTRA_TITLE= "COM.EXAMPLE.MVVMRESTAPI.EXTRA.TITLE";
    public static final String EXTRA_ID = "COM.EXAMPLE.MVVMRESTAPI.EXTRA.ID";
    private EditText editTextTitle, editTextBody, editTextuserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_post);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextBody = findViewById(R.id.edit_text_body);
        editTextuserId = findViewById(R.id.edit_text_userId);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_post);

        setTitle("Add Post");
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Notes");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextBody.setText(intent.getStringExtra(EXTRA_BODY));
            int userId = intent.getIntExtra(EXTRA_ID,1);
            editTextuserId .setText(String.valueOf(userId));
        }else{
            setTitle("Add Notes");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_post:
                savePost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void savePost(){
        String title = editTextTitle.getText().toString();
        String body = editTextBody.getText().toString();
        int userId = Integer.valueOf(editTextuserId.getText().toString());

        if(title.trim().isEmpty() || body.trim().isEmpty()){
            Toast.makeText(this, "Add a Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_BODY, body);
        data.putExtra(EXTRA_USERID, userId);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
