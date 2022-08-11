package com.gbc.holidayhype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.common.DatabaseHandler;
import com.gbc.holidayhype.model.DetailedHotel;
import com.gbc.holidayhype.model.Favorite;
import com.gbc.holidayhype.model.HotelAndResort;
import com.gbc.holidayhype.model.IsFavorite;
import com.gbc.holidayhype.util.ServerUtility;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Detailed_HotelandresortActivity extends AppCompatActivity {
    Dialog dialog;
    ImageView imageViewSrc,imageViewBack,imageView,imageViewmap,imageViewmapback,imageViewUpload;
    TextView textViewLocationName,textViewTitle,textViewContact,textViewDescription,textViewVenue,textViewDistance;
    Context context;
    String value;
    SharedPreferences sharedPreferences;
    RelativeLayout layoutMap,layoutsrc;
    ImageView imageViewStar1,imageViewStar2,imageViewStar3,imageViewStar4,imageViewStar5;
    LinearLayout MiddleContact,MiddleVenue,LayoutPage,layoutBottom;
    WebView webViewHotel;
    Double lati,longi;
    public MapFragment googleMap;
    Marker melbourne;
    MarkerOptions marker;
    Button bookNow;
    public  static ArrayList<HotelAndResort> hotelArrayList1;
    RelativeLayout layout;
    RelativeLayout layoutInvisible;
    static String longitude = "", latitude = "";
    Double currlati=0.0, currlongi=0.0;
    Double latiDistance,longiDistance;
    DetailedHotel detailedHotel;
    RelativeLayout.LayoutParams params;

    Boolean isfav=false;

    ImageView imageViewFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaied_hotelandresort);
        context=Detailed_HotelandresortActivity.this;


        value= getIntent().getExtras().getString("nid");

        imageViewUpload=(ImageView)findViewById(R.id.imageViewUpload);
        imageViewSrc=(ImageView)findViewById(R.id.imageViewSrc);
        imageViewBack=(ImageView)findViewById(R.id.imageViewBack);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageViewmapback=(ImageView)findViewById(R.id.imageViewmapback);
        imageViewmap=(ImageView)findViewById(R.id.imageViewmap);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);
        textViewContact=(TextView)findViewById(R.id.textViewContact);
        textViewVenue=(TextView)findViewById(R.id.textViewVenue);
        textViewLocationName=(TextView)findViewById(R.id.textViewType);
        textViewDescription=(TextView)findViewById(R.id.textViewDescription);
        textViewDistance=(TextView)findViewById(R.id.textViewDistance);
        sharedPreferences=getSharedPreferences("Language", Context.MODE_PRIVATE);
        MiddleContact=(LinearLayout)findViewById(R.id.MiddleContact);
        MiddleVenue=(LinearLayout)findViewById(R.id.MiddleVenue);
        LayoutPage=(LinearLayout)findViewById(R.id.LayoutPage);
        webViewHotel=(WebView)findViewById(R.id.webViewHotel);
        bookNow =(Button)findViewById(R.id.bookNow);

        layoutsrc=(RelativeLayout)findViewById(R.id.layoutsrc);
        layoutBottom=(LinearLayout)findViewById(R.id.layoutBottom);

        imageViewStar1=(ImageView)findViewById(R.id.imageViewStar1);
        imageViewStar2=(ImageView)findViewById(R.id.imageViewStar2);
        imageViewStar3=(ImageView)findViewById(R.id.imageViewStar3);
        imageViewStar4=(ImageView)findViewById(R.id.imageViewStar4);
        imageViewStar5=(ImageView)findViewById(R.id.imageViewStar5);
        layout = (RelativeLayout) findViewById(R.id.layoutMap);
        layoutInvisible = (RelativeLayout) findViewById(R.id.layoutInvisible);
        imageViewFavorite=(ImageView) findViewById(R.id.imageViewFavorite);
        DatabaseHandler db1 = new DatabaseHandler(context);
        isfav=db1.IsFavExist("Hotel",value);
        //fav
        if(isfav) {
            imageViewFavorite.setImageResource(R.drawable.favorite);

        }
        else {
            imageViewFavorite.setImageResource(R.drawable.detailedfavorite);

        }

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(context);
                if(!isfav) {

                    Favorite fav = new Favorite();
                    fav.setTitle(detailedHotel.getName());
                    fav.setType(detailedHotel.getType());
                    fav.setDescription(detailedHotel.getDescription());
                    fav.setSrc(detailedHotel.getImageUrl());
                    fav.setName(detailedHotel.getAddress());
                    fav.setNid(value);
                    fav.setPage("Hotel");
                    fav.setLatitude(detailedHotel.getLatitude());
                    fav.setLongitude(detailedHotel.getLongitude());
                    fav.setTime("");
                    db.addChat(fav);
                    IsFavorite isfavdata=new IsFavorite();
                    isfavdata.setNid(value);
                    isfavdata.setPage("Hotel");
                    db.addFav(isfavdata);
                    imageViewFavorite.setImageResource(R.drawable.favorite);
                    isfav=true;

            }else
                {

                    db.removeFavorite(value);
                    db.removeISFavorite(value);
                    imageViewFavorite.setImageResource(R.drawable.detailedfavorite);
                    isfav=false;

                }


            }
        });


        params = (RelativeLayout.LayoutParams) layoutInvisible.getLayoutParams();

        getdatavolly();
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detailed_HotelandresortActivity.this,HotelBookingActivity.class);
                intent.putExtra("detailData", detailedHotel);
                startActivity(intent);
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageViewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewmapback.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams adaptLayout = (RelativeLayout.LayoutParams) layout.getLayoutParams();
                adaptLayout.height =  ViewGroup.LayoutParams.MATCH_PARENT;
                layout.setLayoutParams(adaptLayout);
            }
        });
        imageViewmapback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageViewmapback.setVisibility(View.GONE);
//                RelativeLayout.LayoutParams adaptLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
//                layout.setLayoutParams(adaptLayout);
//                layout.setGravity(Gravity.BOTTOM);


                RelativeLayout.LayoutParams adaptLayout = (RelativeLayout.LayoutParams) layout.getLayoutParams();
                adaptLayout.height = 80;
                layout.setLayoutParams(adaptLayout);

            }
        });
        MiddleContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Detailed_HotelandresortActivity.this);
                builder.setMessage("Are You Sure Want To Call?");

                builder.setPositiveButton("Call",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String uri = "tel:" +detailedHotel.getPhone();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });
        MiddleVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutPage.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                webViewHotel.setVisibility(View.VISIBLE);
                if(detailedHotel.getWebsite().startsWith("http://"))
                {

                    webViewHotel.loadUrl(detailedHotel.getWebsite());
                }
                else {
//                    Log.v("come1","come1");
                    webViewHotel.loadUrl("http://" + detailedHotel.getWebsite());

                }
            }
        });


        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog  dialogzoom = new Dialog(Detailed_HotelandresortActivity.this, R.style.TransparentBackground);
                dialogzoom.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialogzoom.setContentView(R.layout.sharing);

//                holder.textViewTitle.setText(arrayList.get(position).getTitle());
                dialogzoom.setCancelable(false);
                dialogzoom.show();
          TextView  textViewCopyLink=(TextView)dialogzoom.findViewById(R.id.textViewCopyLink);
          TextView  textViewClose=(TextView)dialogzoom.findViewById(R.id.textViewClose);
          ImageView  imageViewCopyLink=(ImageView) dialogzoom.findViewById(R.id.imageViewCopyLink);
          ImageView  imageViewClose=(ImageView) dialogzoom.findViewById(R.id.imageViewClose);
          ImageView    imageViewFacebook=(ImageView) dialogzoom.findViewById(R.id.imageViewFacebook);
          ImageView   imageViewWhatsapp=(ImageView) dialogzoom.findViewById(R.id.imageViewWhatsapp);
          ImageView imageViewInsta=(ImageView)dialogzoom.findViewById(R.id.imageViewInsta);
          LinearLayout layoutCopy=(LinearLayout)dialogzoom.findViewById(R.id.layoutCopy);
           LinearLayout layoutClose=(LinearLayout)dialogzoom.findViewById(R.id.layoutClose);


                layoutCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", ServerUtility.FacebookLink);
                        clipboard.setPrimaryClip(clip);
                    }
                });
     imageViewFacebook.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             dialogzoom.dismiss();
         }
     });

     imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             dialogzoom.dismiss();
             new DownloadImage().execute();

         }


     });
                imageViewInsta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new DownloadImageInsta().execute(detailedHotel.getImageUrl());
                        dialogzoom.dismiss();
                    }
                });
                layoutClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogzoom.dismiss();
                    }
                });
            }
        });


    }


    public void getdatavolly() {
        dialog = new Dialog(Detailed_HotelandresortActivity.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/hoteldetails/"+ value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            Log.e("response", "Success == " + response.toString());

//                            JSONObject respObj = new JSONObject(response);

                                JSONObject tutorialsObject = new JSONObject(response);
                                Log.e("APIRes", "Success == " + tutorialsObject.getString("description"));
                                detailedHotel = new DetailedHotel();

                                detailedHotel.setId(tutorialsObject.getString("_id"));
                                detailedHotel.setName(tutorialsObject.getString("name"));
                                detailedHotel.setAddress(tutorialsObject.getString("address"));
                                detailedHotel.setDescription(tutorialsObject.getString("description"));
                                detailedHotel.setPrice(tutorialsObject.getString("price"));
                                detailedHotel.setType(tutorialsObject.getString("type"));
                                detailedHotel.setLatitude(tutorialsObject.getString("latitude"));
                                detailedHotel.setLongitude(tutorialsObject.getString("longitude"));
                                detailedHotel.setDate(tutorialsObject.getString("date"));
                                detailedHotel.setNoOfRooms(tutorialsObject.getString("numberOfRooms"));
                                detailedHotel.setPhone(tutorialsObject.getString("phone"));
                                detailedHotel.setImageUrl(tutorialsObject.getString("imageUrl"));
                                detailedHotel.setWebsite(tutorialsObject.getString("website"));
                                detailedHotel.setBooking("0");

//                                SharedPreferences sharedPref = Detailed_HotelandresortActivity.this.getPreferences(Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPref.edit();
//                                editor.putString(ServerUtility.HotelId, detailedHotel.getId());
//                                editor.apply();
                            SharedPreferences.Editor editor = getSharedPreferences(ServerUtility.MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString(ServerUtility.HotelId, detailedHotel.getId());
                            editor.putString(ServerUtility.HotelRoom, detailedHotel.getNoOfRooms());
                            editor.apply();
                                dialog.dismiss();
                                textViewContact.setText(detailedHotel.getPhone());
                                textViewDescription.setText(Html.fromHtml(detailedHotel.getDescription()));
                                textViewLocationName.setText(detailedHotel.getAddress());
                                textViewTitle.setText(detailedHotel.getName());
                                Picasso.get()
                                        .load(detailedHotel.getImageUrl())
//                                        .load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg")
                                        .into(imageViewSrc);



                                if (detailedHotel.getType().equals("1 Stars"))
                                {
                                    imageViewStar1.setVisibility(View.VISIBLE);
                                    imageViewStar2.setVisibility(View.GONE);
                                    imageViewStar3.setVisibility(View.GONE);
                                    imageViewStar4.setVisibility(View.GONE);
                                    imageViewStar5.setVisibility(View.GONE);
                                }
                                else if (detailedHotel.getType().equals("2 Stars"))
                                {
                                    imageViewStar1.setVisibility(View.VISIBLE);
                                    imageViewStar2.setVisibility(View.VISIBLE);
                                    imageViewStar3.setVisibility(View.GONE);
                                    imageViewStar4.setVisibility(View.GONE);
                                    imageViewStar5.setVisibility(View.GONE);
                                }
                                else if (detailedHotel.getType().equals("3 Stars"))
                                {
                                    imageViewStar1.setVisibility(View.VISIBLE);
                                    imageViewStar2.setVisibility(View.VISIBLE);
                                    imageViewStar3.setVisibility(View.VISIBLE);
                                    imageViewStar4.setVisibility(View.GONE);
                                    imageViewStar5.setVisibility(View.GONE);
                                }
                                else if (detailedHotel.getType().equals("4 Stars"))
                                {
                                    imageViewStar1.setVisibility(View.VISIBLE);
                                    imageViewStar2.setVisibility(View.VISIBLE);
                                    imageViewStar3.setVisibility(View.VISIBLE);
                                    imageViewStar4.setVisibility(View.VISIBLE);
                                    imageViewStar5.setVisibility(View.GONE);
                                }
                                else if (detailedHotel.getType().equals("5 Stars"))
                                {
                                    imageViewStar1.setVisibility(View.VISIBLE);
                                    imageViewStar2.setVisibility(View.VISIBLE);
                                    imageViewStar3.setVisibility(View.VISIBLE);
                                    imageViewStar4.setVisibility(View.VISIBLE);
                                    imageViewStar5.setVisibility(View.VISIBLE);
                                }

//            Log.v("lati",""+detailedHotel.getLatitude());
//            Log.v("longi",""+detailedHotel.getLongitude());






                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch", "" + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("catch", "" + error.getMessage());
                dialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Detailed_HotelandresortActivity.this, R.style.TransparentBackground);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_loading);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = detailedHotel.getImageUrl();

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                // Save file to sdcard
                File myDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String fname = "temp.png";
                File file = new File (myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            dialog.dismiss();

            File myDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            String fname = "temp.png";
            File file = new File (myDir, fname);


            String whatsAppMessage = detailedHotel.getName();
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM,uri);
            intent.setType("image/jpeg");
            intent.setPackage("com.whatsapp");
            startActivity(intent);

        }
    }
    private class DownloadImageInsta extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                // Save file to sdcard
                File myDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String fname = "temp.png";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            progressDialog.dismiss();

            File myDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            String fname = "temp.png";
            File file = new File (myDir, fname);


            // Create the new Intent using the 'Send' action.
            Intent share = new Intent(Intent.ACTION_SEND);
            // Set the MIME type
            share.setType("image/*");
            share.setPackage("com.instagram.android");

            // Create the URI from the media
            Uri imageUri = Uri.fromFile(file);

            // Add the URI and the caption to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            share.putExtra(Intent.EXTRA_TEXT, ServerUtility.title);
            try {
                // Broadcast the Intent.
                startActivity(share);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "Instragram have not been installed.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
