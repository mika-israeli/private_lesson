package com.example.private_lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.private_lesson.model.Education;
import com.example.private_lesson.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApiRecyclerAdapter RecyclerView.ViewHolder{

        TextView title;
        TextView year;
        TextView imdbID;
        TextView type;
        ImageView poster;
        List<Education> data;


public EducationViewHolder(@NonNull View itemView,ApiRecyclerAdapter.OnItemClickListener listener,List<Education> data){
        super(itemView);
        this.data=data;
        title=itemView.findViewById(R.id.title);
        year=itemView.findViewById(R.id.year);
        imdbID=itemView.findViewById(R.id.imdbID);
        type=itemView.findViewById(R.id.type);
        poster=itemView.findViewById(R.id.poster);
        }


public void bind(Education rd,int pos){
        title.setText("title: "+rd.getTitle());
        year.setText("year: "+rd.getYear());
        imdbID.setText("imdbID: "+rd.getImdbID());
        type.setText("type: "+rd.getType());
        Picasso.get().load(rd.getPoster()).placeholder(R.drawable.avatar).into(poster);
        Picasso.get().load(rd.getPoster).placeholder
        (R.drawable.poster).into(poster);


        }
        }

public class ApiViewHolder extends RecyclerView.Adapter<ApiViewHolder>{
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Education> data;
    public void setData(List<Education> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public ApiRecyclerAdapter(LayoutInflater inflater, List<Education> data){
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

        if(data==null) {
            return 0;
        }
        return data.size();
    }
}
