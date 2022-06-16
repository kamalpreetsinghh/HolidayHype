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
import com.example.holidayhipe.model.FlightsData;

import java.util.ArrayList;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightsViewHolder> {

    Context context;
    ArrayList<FlightsData> flightDataList;


    public FlightAdapter(Context context, ArrayList<FlightsData> recentsDataList) {
        this.context = context;
        this.flightDataList = recentsDataList;
    }

    @NonNull
    @Override
    public FlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.flight_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new FlightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsViewHolder holder, int position) {

        holder.flightName.setText(flightDataList.get(position).getFlightName());
        holder.firstPlace.setText(flightDataList.get(position).getFrom());
        holder.secondPlace.setText(flightDataList.get(position).getTo());
        holder.departureTime.setText(flightDataList.get(position).getDepartureTiming());
        holder.landingTime.setText(flightDataList.get(position).getLandingTiming());
        holder.flightType.setText(flightDataList.get(position).getType());
        holder.flightFare.setText(flightDataList.get(position).getFare());
        holder.flightTime.setText(flightDataList.get(position).getTotalTiming());
        holder.flightLogo.setImageResource(flightDataList.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ArrayList<FlightsData> sendData = new ArrayList<>();
                sendData.add(flightDataList.get(position));
                Intent i=new Intent(context, DetailsActivity.class);
//                i.putParcelableArrayListExtra("placeData", sendData);
                Log.d("TAG", "Data : "+ flightDataList);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return flightDataList.size();
    }

    public static final class FlightsViewHolder extends RecyclerView.ViewHolder{

        ImageView flightLogo;
        TextView firstPlace, secondPlace, departureTime,landingTime,flightTime, flightName,flightType,flightFare;

        public FlightsViewHolder(@NonNull View itemView) {
            super(itemView);

            firstPlace = itemView.findViewById(R.id.firstPlace);
            secondPlace = itemView.findViewById(R.id.secondPlace);
            departureTime = itemView.findViewById(R.id.departureTime);
            landingTime = itemView.findViewById(R.id.landingTime);
            flightName = itemView.findViewById(R.id.flightName);
            flightFare = itemView.findViewById(R.id.flightFare);
            flightType = itemView.findViewById(R.id.flightType);
            flightTime = itemView.findViewById(R.id.flightTime);
            flightLogo = itemView.findViewById(R.id.plane_logo);

        }
    }
}
