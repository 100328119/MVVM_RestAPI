package com.example.mvvmrestapi.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmrestapi.R;
import com.example.mvvmrestapi.model.Post;

public class PostAapter extends ListAdapter<Post, PostAapter.PostHolder> {
    private onItemClickListener listener;

    public PostAapter() {
        super(DIFF_CALLBACK);

    }

    private static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post currentPost = getItem(position);
        holder.title.setText(currentPost.getTitle());
        holder.body.setText(currentPost.getBody());
        holder.id.setText(String.valueOf(currentPost.getId()));
        holder.userid.setText(String.valueOf(currentPost.getUserId()));
    }

    public Post getPostAt(int position) {
        return getItem(position);
    }


    class PostHolder extends RecyclerView.ViewHolder {
        private TextView id, userid, title, body;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_title);
            body = itemView.findViewById(R.id.text_view_body);
            id = itemView.findViewById(R.id.text_view_Id);
            userid = itemView.findViewById(R.id.text_view_userId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Post post);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
