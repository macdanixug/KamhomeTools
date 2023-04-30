package com.example.kamhometools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class blogAdapter extends RecyclerView.Adapter<blogAdapter.myViewHolder> {

    private Context context;
    private ArrayList<BlogModel> list;
    private blogAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(BlogModel item);
    }

    public blogAdapter(Context context, List<BlogModel> list) {
        this.context = context;
        this.list = (ArrayList<BlogModel>) list;
    }


    public void setOnItemClickListener(blogAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public blogAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blogs_design, parent, false);
        return new blogAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull blogAdapter.myViewHolder holder, int position) {
        BlogModel model = list.get(position);
        holder.blog_title.setText(model.getBlog_title());
        String imageUri;
        imageUri = model.getImageUrl();
        Picasso.get().load(imageUri).into(holder.blog_image);
        Picasso.get().load(imageUri).into(holder.icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, model.getBlog_title() + "Clicked", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new BlogDetailsFragment("Title","Image","Message")).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView blog_title, blog_message;
        ImageView blog_image,icon;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_message = itemView.findViewById(R.id.blog_message);
            blog_image = itemView.findViewById(R.id.blog_image);
            icon = itemView.findViewById(R.id.icon);

        }
    }

}
