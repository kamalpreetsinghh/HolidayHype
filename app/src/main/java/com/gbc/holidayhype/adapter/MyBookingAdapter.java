package com.gbc.holidayhype.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.UpdateHotelBookingActivity;
import com.gbc.holidayhype.model.MyBooking;
import com.gbc.holidayhype.util.ServerUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MyBooking> arrayList = new ArrayList<>();
    Dialog dialog;

    public MyBookingAdapter(Context context, ArrayList<MyBooking> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_current_booking, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String  noOfRooms, name, GuestName, hotelName,hotelBookingRefId;
        name = arrayList.get(holder.getAdapterPosition()).getName();
        hotelName = arrayList.get(holder.getAdapterPosition()).getHotelName();
        GuestName =arrayList.get(holder.getAdapterPosition()).getGuestName();
        noOfRooms =arrayList.get(holder.getAdapterPosition()).getNumberOfRooms();
        hotelBookingRefId =arrayList.get(holder.getAdapterPosition()).getHotelBookingRefId();
        //set view
        holder.edTitle.setText(name);
        holder.edNights.setText(new StringBuilder().append("Name: ").append(String.valueOf(hotelName)).toString());
        holder.edPrice.setText(new StringBuilder().append("Guest Name: ").append(String.valueOf(GuestName)).toString());
        holder.edTotal.setText(new StringBuilder().append("Total: ").append(String.valueOf(noOfRooms)).toString());
        //set the image
        Picasso.get().load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg").fit().into(holder.imageView);
        holder.iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = context.getSharedPreferences(ServerUtility.MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(ServerUtility.HotelBookingRefId, hotelBookingRefId);
                editor.apply();
                Intent intent =new Intent(context, UpdateHotelBookingActivity.class);
                context.startActivity(intent);
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequest(hotelBookingRefId,holder.getAdapterPosition());

            }
        });
    }
    public void deleteRequest(String hotelBookingId,int position) {
        dialog = new Dialog(context, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, "https://holidayhype.herokuapp.com/api/hotelbooking/"+hotelBookingId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            Log.e("response", "Success == " + response.toString());
                            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                            arrayList.remove(position);
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("catch", "" + e.getMessage());
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("catch", "" + error.getMessage());
                dialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView edTitle, edPrice, edTotal, edStartDate, edNights;
        private ImageView imageView,iv_update,iv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edTitle = itemView.findViewById(R.id.ccardTitle);
            edPrice = itemView.findViewById(R.id.ccardPrice);
            imageView = itemView.findViewById(R.id.ccardImage);
            edNights = itemView.findViewById(R.id.ccardDays);
            edStartDate = itemView.findViewById(R.id.ccardStartDate);
            edTotal = itemView.findViewById(R.id.ccardTotalPrice);
            iv_update =itemView.findViewById(R.id.iv_update);
            iv_delete =itemView.findViewById(R.id.iv_delete);
        }
    }
}