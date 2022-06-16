package com.example.holidayhipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.holidayhipe.DetailsActivity;
import com.example.holidayhipe.R;
//import com.rajendra.vacationtourapp.DetailsActivity;
//import com.rajendra.vacationtourapp.R;
//import com.rajendra.vacationtourapp.model.RecentsData;

import com.example.holidayhipe.model.RecentsData;

import java.util.ArrayList;
import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    Context context;

    List<RecentsData> recentsDataList;

    public void setFilteredList(List<RecentsData> filteredList){
        this.recentsDataList = filteredList;
        notifyDataSetChanged();
    }


    public RecentsAdapter(Context context, ArrayList<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.countryName.setText(recentsDataList.get(position).getCountryName());
        holder.placeName.setText(recentsDataList.get(position).getPlaceName());
//        holder.price.setText(recentsDataList.get(position).getPrice());
        holder.placeImage.setImageResource(recentsDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ArrayList<RecentsData> sendData = new ArrayList<>();
                sendData.add(recentsDataList.get(position));
                Intent i=new Intent(context, DetailsActivity.class);
                i.putParcelableArrayListExtra("placeData", sendData);
                Log.d("TAG", "Data : "+ recentsDataList);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}