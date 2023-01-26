package com.example.private_lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.private_lesson.model.Post;

import java.util.List;

class PostViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView idTv;
    CheckBox cb;
    List<Post> data;
    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.postlistrow_name_tv);
        idTv = itemView.findViewById(R.id.postlistrow_id_tv);
        cb = itemView.findViewById(R.id.postlistrow_cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)cb.getTag();
              Post post = data.get(pos);
                post.cb = cb.isChecked();
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind( Post post, int pos) {
        nameTv.setText(post.teacherName);
        idTv.setText(post.id);
        cb.setChecked(post.cb);
        cb.setTag(pos);
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder>{
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;
    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_row,parent,false);
        return new PostViewHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
       Post post = data.get(position);
        holder.bind(post,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}


