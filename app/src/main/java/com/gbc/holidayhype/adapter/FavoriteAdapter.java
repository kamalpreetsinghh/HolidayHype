package com.gbc.holidayhype.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbc.holidayhype.R;
import com.gbc.holidayhype.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Agilution on 9/30/2015.
 */
public class FavoriteAdapter extends BaseAdapter {
    Context context;
    ArrayList<Favorite> arrayList;
    LayoutInflater inflater = null;

    public FavoriteAdapter(Context context, ArrayList<Favorite> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
     ImageView imageViewSrc,imageViewStar1,imageViewStar2,imageViewStar3,imageViewStar4,imageViewStar5,imageViewClock;
        TextView textViewTitle,textViewDescription,textViewType,textViewTime;


         }
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View listView;
        listView = inflater.inflate(R.layout.listitem_favorite, null);

        holder.imageViewSrc=(ImageView)listView.findViewById(R.id.imageViewSrc);
        holder.textViewTitle=(TextView)listView.findViewById(R.id.textViewTitle);

        holder.textViewDescription=(TextView)listView.findViewById(R.id.textViewDescription);
        holder.textViewType=(TextView)listView.findViewById(R.id.textViewType);
        holder.textViewTime=(TextView)listView.findViewById(R.id.textViewTime);

        holder.imageViewStar1=(ImageView)listView.findViewById(R.id.imageViewStar1);
        holder.imageViewStar2=(ImageView)listView.findViewById(R.id.imageViewStar2);
        holder.imageViewStar3=(ImageView)listView.findViewById(R.id.imageViewStar3);
        holder.imageViewStar4=(ImageView)listView.findViewById(R.id.imageViewStar4);
        holder.imageViewStar5=(ImageView)listView.findViewById(R.id.imageViewStar5);
        holder.imageViewClock=(ImageView)listView.findViewById(R.id.imageViewClock);


        Picasso.get()
            .load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg")
             .into(holder.imageViewSrc);
//        holder.imageViewSrc.setImageResource(Integer.parseInt(arrayList.get(position).getSrc()));
        holder.textViewTitle.setText(arrayList.get(position).getTitle().trim());
        holder.textViewType.setText(arrayList.get(position).getPage());
        holder.textViewDescription.setText(Html.fromHtml(arrayList.get(position).getDescription()));

        return listView;

    }
}
