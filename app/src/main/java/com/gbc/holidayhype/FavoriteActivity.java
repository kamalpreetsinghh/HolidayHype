package com.gbc.holidayhype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.gbc.holidayhype.adapter.FavoriteAdapter;
import com.gbc.holidayhype.common.DatabaseHandler;
import com.gbc.holidayhype.model.Favorite;
import com.gbc.holidayhype.model.Restaurant;

import java.util.ArrayList;


public class FavoriteActivity extends AppCompatActivity {
    Dialog dialog;
    ScrollView scrollView;
    TextView textViewDescription, textViewTitle, textViewNoData;
    SharedPreferences sharedPreferences;
    ImageView imageViewBack;
    TextView textViewLearnMore, textView3, textView2, textView1;
    ListView listViewFavorite;
    ArrayList<Favorite> arrFavorite;
    FavoriteAdapter favoriteAdapter;
    //    public  static ArrayList<HotelAndResort> hotelArrayList;
    public static ArrayList<Restaurant> restaurantArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favorite);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        textViewNoData = (TextView) findViewById(R.id.textViewNoData);
        arrFavorite = new ArrayList<Favorite>();
//        hotelArrayList=new ArrayList<HotelAndResort>();
        restaurantArrayList = new ArrayList<Restaurant>();


        listViewFavorite = (ListView) findViewById(R.id.listViewFavorite);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        arrFavorite = db.getAllFavorite();
        favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, arrFavorite);
        if (arrFavorite.size() > 0) {
            listViewFavorite.setAdapter(favoriteAdapter);
            favoriteAdapter.notifyDataSetChanged();
            textViewNoData.setVisibility(View.GONE);
            listViewFavorite.setVisibility(View.VISIBLE);
        } else {
            listViewFavorite.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }

        listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (arrFavorite.get(i).getPage().equals("Hotel")) {

                } else if (arrFavorite.get(i).getPage().equals("Restaurant")) {
                    Intent intent = new Intent(getApplicationContext(), Detailed_RestaurantActivity.class);
                    for (int j = 0; j < arrFavorite.size(); j++) {
                        Restaurant restaurant = new Restaurant();
                        restaurant.setId(arrFavorite.get(j).getNid());
                        restaurant.setLatitude(arrFavorite.get(j).getLatitude());
                        restaurant.setLongitude(arrFavorite.get(j).getLongitude());
                        restaurantArrayList.add(restaurant);
                    }
                   Detailed_RestaurantActivity.restaurantArrayList1 = restaurantArrayList;
                    intent.putExtra("nid", arrFavorite.get(i).getNid());
                    startActivity(intent);
                } else if (arrFavorite.get(i).getPage().equals("Attraction")) {
                }

            }
        });


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}