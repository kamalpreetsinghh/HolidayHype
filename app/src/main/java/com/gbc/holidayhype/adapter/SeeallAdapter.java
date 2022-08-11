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
import com.gbc.holidayhype.DetailsActivity;
import com.gbc.holidayhype.DetailsActivity2;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.RecentsData;

import org.w3c.dom.Attr;

import java.util.ArrayList;

public class SeeallAdapter extends RecyclerView.Adapter<SeeallAdapter.RecentsViewHolder> {

    Context context;
    ArrayList<AttractionData> recentsDataList;


    public SeeallAdapter(Context context, ArrayList<AttractionData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.seeall_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.countryName.setText(recentsDataList.get(position).getCountry());
        holder.placeName.setText(recentsDataList.get(position).getPlaceName());
//        holder.price.setText(recentsDataList.get(position).getPrice());
        Glide.with(context).load(recentsDataList.get(position).getPlaceImage()).into(holder.placeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String id = recentsDataList.get(position).getId();
                String placeName = recentsDataList.get(position).getPlaceName();
                String country = recentsDataList.get(position).getCountry();
                String desc = recentsDataList.get(position).getDescription();
                String image = recentsDataList.get(position).getPlaceImage();
                String visitPlace1 = recentsDataList.get(position).getVisitPlace1();
                String visitPlace2 = recentsDataList.get(position).getVisitPlace2();
                String visitPlace3 = recentsDataList.get(position).getVisitPlace3();
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


        }
    }
}
