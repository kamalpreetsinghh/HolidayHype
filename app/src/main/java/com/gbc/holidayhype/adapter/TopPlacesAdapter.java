package com.gbc.holidayhype.adapter;

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

import com.bumptech.glide.Glide;
import com.gbc.holidayhype.DetailsActivity2;
import com.gbc.holidayhype.R;
//import com.rajendra.vacationtourapp.R;
//import com.rajendra.vacationtourapp.model.TopPlacesData;

import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.TopPlacesData;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<TopPlacesAdapter.TopPlacesViewHolder> {

    Context context;
    List<AttractionData> topPlacesDataList;

    public TopPlacesAdapter(Context context, List<AttractionData> topPlacesDataList) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.top_places_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, int position) {

        holder.countryName.setText(topPlacesDataList.get(position).getCountry());
        holder.placeName.setText(topPlacesDataList.get(position).getPlaceName());
        Glide.with(context).load(topPlacesDataList.get(position).getPlaceImage()).into(holder.placeImage);
//        holder.price.setText(topPlacesDataList.get(position).getPrice());
//        holder.placeImage.setImageResource(topPlacesDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String id = topPlacesDataList.get(position).getId();
                String placeName = topPlacesDataList.get(position).getPlaceName();
                String country = topPlacesDataList.get(position).getCountry();
                String desc = topPlacesDataList.get(position).getDescription();
                String image = topPlacesDataList.get(position).getPlaceImage();
                String visitPlace1 = topPlacesDataList.get(position).getVisitPlace1();
                String visitPlace2 = topPlacesDataList.get(position).getVisitPlace2();
                String visitPlace3 = topPlacesDataList.get(position).getVisitPlace3();
                AttractionData sendData = new AttractionData(id,placeName,country,desc,image,visitPlace1,visitPlace2,visitPlace3);
//                sendData.add(topPlacesDataList.get(position));
                Intent i=new Intent(context, DetailsActivity2.class);
                i.putExtra("TopPlaceData", sendData);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topPlacesDataList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
//            price = itemView.findViewById(R.id.price);

        }
    }
}
