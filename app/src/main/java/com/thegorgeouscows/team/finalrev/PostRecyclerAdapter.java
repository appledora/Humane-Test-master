package com.thegorgeouscows.team.finalrev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    public List<Posts>posts_lists;
    public PostRecyclerAdapter(List<Posts>posts_list){
            this.posts_lists = posts_list;
    }
    public Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("my","inside Viewholder of Adapter");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_items,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    Log.i("my","ViewHolder Binding");
        String user_id = posts_lists.get(position).getUserid();
        String pickup_address = posts_lists.get(position).getAddress();
        String image_url = posts_lists.get(position).getImage_url();
        long millisec = posts_lists.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisec)).toString();        //Log.i("my","recieved address: "+user_id);
        holder.setName(user_id);
        holder.setAddress(pickup_address);
        holder.setBlogImage(image_url);
        holder.setTime(dateString);
    }

    @Override
    public int getItemCount() {
        return posts_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView addressText,postDate;
        private View mView;
        private ImageView blogImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String text){
            Log.i("my","set Text = "+text);
            addressText = mView.findViewById(R.id.blog_user_name);
            addressText.setText(text);
        }
        public void setAddress(String text){
            Log.i("my","set Text = "+text);
            addressText = mView.findViewById(R.id.location);
            addressText.setText(text);
        }
       public void setTime(String text){
            Log.i("my","set Text = "+text);
            postDate = mView.findViewById(R.id.blog_date);
            postDate.setText(text);
        }

        public void setBlogImage(String downloadUri) {
            blogImage = mView.findViewById(R.id.blog_image);
            Glide.with(context).load(downloadUri).into(blogImage);
        }

        /* public void setAddress(String text){
            Log.i("my","set Text = "+text);
            addressText = mView.findViewById(R.id.blog_user_name);
            addressText.setText(text);
        }
*/
    }
}
