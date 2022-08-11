package com.gbc.holidayhype.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gbc.holidayhype.Detailed_RestaurantActivity;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.model.Restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdaper extends RecyclerView.Adapter<RestaurantListAdaper.NoteHolder> {
    private List<Restaurant> notes = new ArrayList<>();
    private OnItemClickListener listener;
    Double currlati=0.0, currlongi=0.0;
    Double latiDistance,longiDistance;
    Context ctx;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_restaurant, parent, false);
        return new NoteHolder(itemView);
    }

    public RestaurantListAdaper(Context context, ArrayList<Restaurant> arrayList) {
        notes = arrayList;
        ctx = context;
    }
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Restaurant currentNote = notes.get(position);
        Glide.with(ctx)
//                .load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg") // image url
                .load(currentNote.getHeaderImage())
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.drawable.ic_launcher_background)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(holder.imageViewSrc);
       //        holder.imageViewSrc.setImageResource(currentNote.getHeaderImage());
        holder.textViewTitle.setText(currentNote.getTitle().trim());
        holder.textViewType.setText(currentNote.getRestaurantType());
        holder.textViewDescription.setText(Html.fromHtml(currentNote.getDescription()));
        latiDistance=Double.valueOf(""+ currentNote.getLatitude());

        longiDistance=Double.valueOf(""+ currentNote.getLongitude());

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
//        holder.textViewDistance.setText(FormattedText + " " + context.getResources().getString(R.string.km));
//        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Restaurant> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Restaurant getNoteAt(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSrc;
        TextView textViewTitle,textViewType,textViewDescription;

        public NoteHolder(View itemView) {
            super(itemView);
            imageViewSrc=(ImageView)itemView.findViewById(R.id.imageViewSrc);
            textViewTitle=(TextView)itemView.findViewById(R.id.textViewTitle);
            textViewType=(TextView)itemView.findViewById(R.id.textViewType);
            textViewDescription=(TextView)itemView.findViewById(R.id.textViewDescription);
//            textViewDistance=(TextView)itemView.findViewById(R.id.textViewDistance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent=new Intent(ctx, Detailed_RestaurantActivity.class);
                    intent.putExtra("nid",notes.get(position).getId());
                    ctx.startActivity(intent);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Restaurant note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
