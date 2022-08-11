package com.gbc.holidayhype.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gbc.holidayhype.Detailed_HotelandresortActivity;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.common.GPSTracker;
import com.gbc.holidayhype.model.HotelAndResort;


import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Agilution on 9/30/2015.
 */
public class HotelAndResortAdapter extends RecyclerView.Adapter<HotelAndResortAdapter.Holder> {
    Context context;
    ArrayList<HotelAndResort> arrayList;
    Double currlati = 0.0, currlongi = 0.0;
    Double latiDistance, longiDistance;
    SharedPreferences sharedPreferences;
    LayoutInflater inflater = null;

    public HotelAndResortAdapter(Context context, ArrayList<HotelAndResort> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sharedPreferences = context.getSharedPreferences("Language", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listView = layoutInflater.inflate(R.layout.listitem_hotel_and_resort, parent, false);
        Holder holder = new Holder(listView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        Picasso.get()
//                .load(arrayList.get(position).getImageUrl())
//                .into(holder.imageViewSrc);
        Glide.with(context)
                .load(arrayList.get(position).getImageUrl())
//                .load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg") // image url
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.drawable.ic_launcher_background)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(holder.imageViewSrc);
        holder.textViewTitle.setText(arrayList.get(position).getName().trim());
        holder.textViewLocationName.setText(arrayList.get(position).getAddress());
        holder.textViewDescription.setText(Html.fromHtml(arrayList.get(position).getDescription()));
        latiDistance = Double.valueOf("" + arrayList.get(position).getLatitude());

        longiDistance = Double.valueOf("" + arrayList.get(position).getLongitude());
        GPSTracker mGPS = new GPSTracker(context);

        if (mGPS.canGetLocation) {
            mGPS.getLocation();

            Log.v("Latitude",
                    mGPS.getLatitude() + "Longitude" + mGPS.getLongitude());

            currlati = Double.valueOf(mGPS.getLatitude()).doubleValue();
            currlongi = Double.valueOf(mGPS.getLongitude()).doubleValue();

        }

        double earthRadius = 6371;

        double dLat = Math.toRadians(currlati - latiDistance);
        double dLng = Math.toRadians(currlongi - longiDistance);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latiDistance))
                * Math.cos(Math.toRadians(currlati)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        DecimalFormat form = new DecimalFormat("0.00");
        String FormattedText = form.format(dist);
        holder.textViewDistance.setText(FormattedText + " " + context.getResources().getString(R.string.km));
//        if(sharedPreferences.getString("BTS","en").equals("ar")) {
//           holder.textViewDistance.setText(FormattedText+" كم");
//        }
//        else {
//            holder.textViewDistance.setText(FormattedText + " km");
//        }
        if (arrayList.get(position).getType().equals("1s")) {
            holder.imageViewStar1.setVisibility(View.VISIBLE);
            holder.imageViewStar2.setVisibility(View.GONE);
            holder.imageViewStar3.setVisibility(View.GONE);
            holder.imageViewStar4.setVisibility(View.GONE);
            holder.imageViewStar5.setVisibility(View.GONE);
        } else if (arrayList.get(position).getType().equals("2s")) {
            holder.imageViewStar1.setVisibility(View.VISIBLE);
            holder.imageViewStar2.setVisibility(View.VISIBLE);
            holder.imageViewStar3.setVisibility(View.GONE);
            holder.imageViewStar4.setVisibility(View.GONE);
            holder.imageViewStar5.setVisibility(View.GONE);
        } else if (arrayList.get(position).getType().equals("3s")) {
            holder.imageViewStar1.setVisibility(View.VISIBLE);
            holder.imageViewStar2.setVisibility(View.VISIBLE);
            holder.imageViewStar3.setVisibility(View.VISIBLE);
            holder.imageViewStar4.setVisibility(View.GONE);
            holder.imageViewStar5.setVisibility(View.GONE);
        } else if (arrayList.get(position).getType().equals("4s")) {
            holder.imageViewStar1.setVisibility(View.VISIBLE);
            holder.imageViewStar2.setVisibility(View.VISIBLE);
            holder.imageViewStar3.setVisibility(View.VISIBLE);
            holder.imageViewStar4.setVisibility(View.VISIBLE);
            holder.imageViewStar5.setVisibility(View.GONE);
        } else if (arrayList.get(position).getType().equals("5s")) {
            holder.imageViewStar1.setVisibility(View.VISIBLE);
            holder.imageViewStar2.setVisibility(View.VISIBLE);
            holder.imageViewStar3.setVisibility(View.VISIBLE);
            holder.imageViewStar4.setVisibility(View.VISIBLE);
            holder.imageViewStar5.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Detailed_HotelandresortActivity.class);
                intent.putExtra("nid",arrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageViewSrc, imageViewStar1, imageViewStar2, imageViewStar3, imageViewStar4, imageViewStar5;
        TextView textViewTitle, textViewLocationName, textViewDescription, textViewDistance;
        RatingBar ratingBar;

        public Holder(@NonNull View listView) {
            super(listView);

            imageViewSrc = (ImageView) listView.findViewById(R.id.imageViewSrc);
            textViewTitle = (TextView) listView.findViewById(R.id.textViewTitle);
            textViewLocationName = (TextView) listView.findViewById(R.id.textViewType);
            textViewDescription = (TextView) listView.findViewById(R.id.textViewDescription);
            textViewDistance = (TextView) listView.findViewById(R.id.textViewDistance);
            imageViewStar1 = (ImageView) listView.findViewById(R.id.imageViewStar1);
            imageViewStar2 = (ImageView) listView.findViewById(R.id.imageViewStar2);
            imageViewStar3 = (ImageView) listView.findViewById(R.id.imageViewStar3);
            imageViewStar4 = (ImageView) listView.findViewById(R.id.imageViewStar4);
            imageViewStar5 = (ImageView) listView.findViewById(R.id.imageViewStar5);
        }
    }

}
