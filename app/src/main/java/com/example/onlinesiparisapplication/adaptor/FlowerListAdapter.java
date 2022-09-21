package com.example.onlinesiparisapplication.adaptor;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import com.example.onlinesiparisapplication.model.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinesiparisapplication.R;
import com.example.onlinesiparisapplication.model.OnlineSiparisModel;

import java.util.List;

public class FlowerListAdapter extends RecyclerView.Adapter<FlowerListAdapter.MyViewHolder> {
    private List<OnlineSiparisModel> onlineSiparisModelList;
    private OnlineSiparisListClickListener clickListener;

    public FlowerListAdapter(List<OnlineSiparisModel> onlineSiparisModelList, OnlineSiparisListClickListener clickListener){
        this.onlineSiparisModelList = onlineSiparisModelList;
        this.clickListener = clickListener;
    }
    // to updata data from outside
    public void updateData(List<OnlineSiparisModel> onlineSiparisModelList){
        this.onlineSiparisModelList = onlineSiparisModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerListAdapter.MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
      holder.retaurantName.setText(onlineSiparisModelList.get(position).getName());
      holder.itemView.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
              clickListener.onItemClick(onlineSiparisModelList.get(position));
          }
      });
        Glide.with(holder.thumbImage)
                .load(onlineSiparisModelList.get(position).getImage())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return onlineSiparisModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView retaurantName;
        ImageView thumbImage;

        public MyViewHolder(View view){
            super(view);
            retaurantName = view.findViewById(R.id.restaurantName);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }
    public interface OnlineSiparisListClickListener{
        public void onItemClick(OnlineSiparisModel onlineSiparisModel);
    }
}
