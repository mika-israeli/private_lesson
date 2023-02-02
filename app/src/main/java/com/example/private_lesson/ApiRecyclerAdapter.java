package com.example.private_lesson;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.private_lesson.model.Education;
import com.google.protobuf.Api;
import com.squareup.picasso.Picasso;

import java.util.List;

 class ApiViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView year;
    TextView type;
    TextView imdbID;
    List<Education> educationList;
    ImageView poster;


    public ApiViewHolder(@NonNull View itemView, ApiRecyclerAdapter.OnItemClickListener listener,
                         List<Education> educationList) {
        super(itemView);
        this.educationList = educationList;
        title = itemView.findViewById(R.id.title);
        year = itemView.findViewById(R.id.year);
        type = itemView.findViewById(R.id.iddb);
        imdbID = itemView.findViewById(R.id.type);
        poster = itemView.findViewById(R.id.poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });

    }

    public void bind(Education education, int pos) {
        title.setText("Movie Educatuon Title: " + education.getTitle());
        year.setText("Movie Educatuon Year: " + education.getYear());
        type.setText("Movie Educatuon Type: " + education.getType());
        imdbID.setText("Movie Educatuon ID: " + education.getImdbID());
        if (education.getPoster() != null && education.getPoster().length() > 0) {
            Picasso.get().load(education.getPoster()).placeholder(R.drawable.avatar).into(poster);
        } else {
            poster.setImageResource(R.drawable.avatar);
        }
    }
}



    public class ApiRecyclerAdapter extends RecyclerView.Adapter<ApiViewHolder> {

        OnItemClickListener listener;

static interface OnItemClickListener {
    void onItemClick(int pos);

}

    LayoutInflater inflater;
    List<Education> educationList;

    public void setData(List<Education> educationList) {
        this.educationList = educationList;
       notifyDataSetChanged();
    }

    public ApiRecyclerAdapter(LayoutInflater inflater, List<Education> educationList) {

        this.inflater = inflater;
        this.educationList = educationList;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    @NonNull
    @Override
    public ApiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.education_row, parent, false);
        return new ApiViewHolder(view, listener, educationList);

    }

    @Override
    public void onBindViewHolder(@NonNull ApiViewHolder holder, int position) {

        Education education = educationList.get(position);
        holder.bind(education, position);
    }

    @Override
    public int getItemCount() {
        if(educationList == null){
            return 0;
        }
        return educationList.size();
    }




}


