package com.example.private_lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.private_lesson.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

    class TeacherPostViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView idTv;
        TextView descriptionTv;
        TextView priceTv;
        CheckBox cb;
        List<Post> Mydata;
        ImageView avatarImage;
        public TeacherPostViewHolder(@NonNull View itemView, TeacherPostRecyclerAdapter.OnItemClickListener listener, List<Post> Mydata) {
            super(itemView);
            this.Mydata = Mydata;
            nameTv = itemView.findViewById(R.id.postlistrow_name_tv);
            idTv = itemView.findViewById(R.id.postlistrow_id_tv);
            descriptionTv = itemView.findViewById(R.id.postlistrow_description_tv);
            priceTv = itemView.findViewById(R.id.postlistrow_price_tv);
            avatarImage = itemView.findViewById(R.id.postlistrow_avatar_img);
            cb = itemView.findViewById(R.id.postlistrow_cb);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int)cb.getTag();
                    Post post = Mydata.get(pos);
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
            descriptionTv.setText(post.description);
            priceTv.setText(post.price+"$");
            cb.setChecked(post.cb);
            cb.setTag(pos);
            if (post.getAvatarUrl()  != null && post.getAvatarUrl().length() > 5) {
                Picasso.get().load(post.getAvatarUrl()).placeholder
                        (R.drawable.avatar).into(avatarImage);
            }else{
                avatarImage.setImageResource(R.drawable.avatar);
            }
        }
    }
public class TeacherPostRecyclerAdapter extends RecyclerView.Adapter<com.example.private_lesson.TeacherPostViewHolder>{

        com.example.private_lesson.TeacherPostRecyclerAdapter.OnItemClickListener listener;
        public static interface OnItemClickListener{
            void onItemClick(int pos);
        }

        LayoutInflater inflater;
        List<Post> Mydata;
        public void setMyData(List<Post> Mydata){
            this.Mydata = Mydata;
            notifyDataSetChanged();
        }
        public TeacherPostRecyclerAdapter(LayoutInflater inflater, List<Post> Mydata){
            this.inflater = inflater;
            this.Mydata = Mydata;
        }

        void setOnItemClickListener(com.example.private_lesson.TeacherPostRecyclerAdapter.OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public com.example.private_lesson.TeacherPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.post_list_row,parent,false);
            return new com.example.private_lesson.TeacherPostViewHolder(view,listener, Mydata);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.private_lesson.TeacherPostViewHolder holder, int position) {
            Post post = Mydata.get(position);
            holder.bind(post,position);
        }

        @Override
        public int getItemCount() {

            if(Mydata==null) {
                return 0;
            }
            return Mydata.size();
        }

    }




