package com.sengun.ogrencim.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sengun.ogrencim.databinding.RecyclerRowBinding;
import com.sengun.ogrencim.model.Post;

import java.util.ArrayList;

public class Postadapter extends RecyclerView.Adapter<Postadapter.PostHolder> {

    private ArrayList<Post> postArrayList;

    public Postadapter (ArrayList<Post> postArrayList){
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowBinding.recyclerViewUserEmailText.setText(postArrayList.get(position).email);
        holder.recyclerRowBinding.recyclerViewCommentText.setText(postArrayList.get(position).comment);

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {


            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

}
