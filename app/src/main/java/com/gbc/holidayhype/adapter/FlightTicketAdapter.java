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
import com.gbc.holidayhype.FlightApi;
import com.gbc.holidayhype.FlightConfirmation;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.TicketInfoActivity;
import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightApiData;
import com.gbc.holidayhype.model.FlightApiData2;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;

import java.util.ArrayList;

public class FlightTicketAdapter extends RecyclerView.Adapter<FlightTicketAdapter.FlightsViewHolder> {

    Context context;
//    FlightApiData flightDataList;
    BookingData bookingData;
    ArrayList<FlightApiData2> flightDataList;
    private FlightApi flightApi;
    private final String TAG = "MainActivity";
    private int count = 2;


    public FlightTicketAdapter(Context context, ArrayList<FlightApiData2> recentDataList) {
        this.context = context;
        this.flightDataList = recentDataList;
//            count++;
    }

    @NonNull
    @Override
    public FlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.flight_ticket_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new FlightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsViewHolder holder, int position) {

        holder.flightName.setText(flightDataList.get(position).getFlightName());
        holder.firstPlace.setText(flightDataList.get(position).getFlightFrom());
        holder.secondPlace.setText(flightDataList.get(position).getFlightTo());
        Glide.with(context).load(flightDataList.get(position).getImageUrl()).into(holder.flightLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                ArrayList<TicketData> sendData = new ArrayList<>();
//                sendData.add(flightDataList);
                Intent i=new Intent(context, TicketInfoActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("flightID",flightDataList.get(position).getFlightID());
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
