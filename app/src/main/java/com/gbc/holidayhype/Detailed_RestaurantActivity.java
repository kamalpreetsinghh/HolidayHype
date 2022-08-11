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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.bumptech.glide.Glide;
import com.gbc.holidayhype.common.DatabaseHandler;
import com.gbc.holidayhype.model.DetailedRestaurant;
import com.gbc.holidayhype.model.Favorite;
import com.gbc.holidayhype.model.IsFavorite;
import com.gbc.holidayhype.model.Restaurant;
import com.gbc.holidayhype.util.ServerUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class Detailed_RestaurantActivity extends AppCompatActivity {
    Dialog dialog;
    ImageView imageViewSrc, imageViewBack, imageViewmap, imageView, imageViewmapback, imageViewUpload;
    TextView textViewLocationName, textViewTitle, textViewContact, textViewDescription, textViewType, textViewDistance;
    Context context;
    String value;
    RelativeLayout layoutMap, layoutsrc, layoutDescription, layoutscroll;
    Boolean isfav = false;
    LinearLayout MiddleContact, MiddleVenue, layoutBottom;
    SharedPreferences sharedPreferences;
    Double lati, longi;
    Typeface typefaceTitle;
    Typeface typefaceType;
    Typeface typefaceDescription;
    Typeface typefaceContact;
    public static ArrayList<Restaurant> restaurantArrayList1;
    RelativeLayout layout;
    RelativeLayout layoutInvisible;
    DetailedRestaurant detailedRestaurant;
    RelativeLayout.LayoutParams params;
    Double currlati = 0.0, currlongi = 0.0;
    Double latiDistance, longiDistance;
    ImageView imageViewFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed__restaurant);
        context = Detailed_RestaurantActivity.this;
        typefaceTitle = Typeface.createFromAsset(context.getAssets(), ServerUtility.appFontDetailTitle);
        typefaceType = Typeface.createFromAsset(context.getAssets(), ServerUtility.appFontDescription);
        typefaceDescription = Typeface.createFromAsset(context.getAssets(), ServerUtility.appFontDetailDescription);
        typefaceContact = Typeface.createFromAsset(context.getAssets(), ServerUtility.appFontContact);

        value = getIntent().getExtras().getString("nid");


        imageViewUpload = (ImageView) findViewById(R.id.imageViewUpload);
        imageViewSrc = (ImageView) findViewById(R.id.imageViewSrc);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewmap = (ImageView) findViewById(R.id.imageViewmap);
        imageViewmapback = (ImageView) findViewById(R.id.imageViewmapback);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewContact = (TextView) findViewById(R.id.textViewContact);
        textViewLocationName = (TextView) findViewById(R.id.textViewLocation);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDistance = (TextView) findViewById(R.id.textViewDistance);
        MiddleContact = (LinearLayout) findViewById(R.id.MiddleContact);
        MiddleVenue = (LinearLayout) findViewById(R.id.MiddleVenue);


        layoutsrc = (RelativeLayout) findViewById(R.id.layoutsrc);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
//        layoutDescription=(RelativeLayout)findViewById(R.id.layoutDescription);
//        layoutscroll=(RelativeLayout)findViewById(R.id.layoutscroll);
        sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        layout = (RelativeLayout) findViewById(R.id.layoutMap);
        layoutInvisible = (RelativeLayout) findViewById(R.id.layoutInvisible);


        params = (RelativeLayout.LayoutParams) layoutInvisible.getLayoutParams();
//        new getData().execute();
        getdatavolly();
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogzoom = new Dialog(Detailed_RestaurantActivity.this, R.style.TransparentBackground);
                dialogzoom.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialogzoom.setContentView(R.layout.sharing);

//                holder.textViewTitle.setText(arrayList.get(position).getTitle());
                dialogzoom.setCancelable(false);
                dialogzoom.show();
                TextView textViewCopyLink = (TextView) dialogzoom.findViewById(R.id.textViewCopyLink);
                TextView textViewClose = (TextView) dialogzoom.findViewById(R.id.textViewClose);
                ImageView imageViewCopyLink = (ImageView) dialogzoom.findViewById(R.id.imageViewCopyLink);
                ImageView imageViewClose = (ImageView) dialogzoom.findViewById(R.id.imageViewClose);
                ImageView imageViewFacebook = (ImageView) dialogzoom.findViewById(R.id.imageViewFacebook);
                ImageView imageViewWhatsapp = (ImageView) dialogzoom.findViewById(R.id.imageViewWhatsapp);
                ImageView imageViewInsta = (ImageView) dialogzoom.findViewById(R.id.imageViewInsta);
                LinearLayout layoutCopy = (LinearLayout) dialogzoom.findViewById(R.id.layoutCopy);
                LinearLayout layoutClose = (LinearLayout) dialogzoom.findViewById(R.id.layoutClose);

                imageViewFacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogzoom.dismiss();


                    }
                });
                layoutCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", ServerUtility.FacebookLink);
                        clipboard.setPrimaryClip(clip);
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
                        dialogzoom.dismiss();
                        new DownloadImageInsta().execute(detailedRestaurant.getImgSrc());
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

        //fav

        imageViewFavorite = (ImageView) findViewById(R.id.imageViewFavorite);

        DatabaseHandler db1 = new DatabaseHandler(context);
        isfav = db1.IsFavExist("Restaurant", value);

        if (isfav) {
            imageViewFavorite.setImageResource(R.drawable.favorite);

        } else {
            imageViewFavorite.setImageResource(R.drawable.detailedfavorite);

        }

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            DatabaseHandler db = new DatabaseHandler(context);

            @Override
            public void onClick(View view) {
                if (!isfav) {

                    Favorite fav = new Favorite();
                    fav.setTitle(detailedRestaurant.getTitle());
                    fav.setType("");
                    fav.setDescription(detailedRestaurant.getDescription());
                    fav.setSrc(detailedRestaurant.getImgSrc());
                    fav.setName(detailedRestaurant.getAddress());
                    fav.setNid(value);
                    fav.setPage("Restaurant");
                    fav.setLatitude(detailedRestaurant.getLatitude());
                    fav.setLongitude(detailedRestaurant.getLongitude());
                    fav.setTime("");
                    db.addChat(fav);
                    IsFavorite isfavdata = new IsFavorite();
                    isfavdata.setNid(value);
                    isfavdata.setPage("Restaurant");
                    db.addFav(isfavdata);
                    imageViewFavorite.setImageResource(R.drawable.favorite);
                    isfav = true;

                } else {

                    db.removeFavorite(value);
                    db.removeISFavorite(value);
                    imageViewFavorite.setImageResource(R.drawable.detailedfavorite);
                    isfav = false;


                }

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
                adaptLayout.height = ViewGroup.LayoutParams.MATCH_PARENT;
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

                final AlertDialog.Builder builder = new AlertDialog.Builder(Detailed_RestaurantActivity.this);
                builder.setMessage("Are You Sure Want To Call?");

                builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String uri = "tel:" + detailedRestaurant.getPhone();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

    }

    public void getdatavolly() {
        dialog = new Dialog(Detailed_RestaurantActivity.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/restaurantdetails/" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            Log.e("response", "Success == " + response.toString());

//                            JSONObject respObj = new JSONObject(response);

                            JSONObject tutorialsObject = new JSONObject(response);
//                                Log.e("APIRes", "Success == " + tutorialsObject.getString("title"));
                            detailedRestaurant = new DetailedRestaurant();
                            detailedRestaurant.setId(tutorialsObject.getString("_id"));
                            detailedRestaurant.setTitle(tutorialsObject.getString("title"));

                            detailedRestaurant.setDescription(tutorialsObject.getString("description"));

                            detailedRestaurant.setImgSrc(tutorialsObject.getString("imgSrc"));

                            detailedRestaurant.setRestaurantType(tutorialsObject.getString("restaurantType"));
//                    restaurant.setNid(tutorialsObject.getString("Nid"));
                            detailedRestaurant.setLatitude(tutorialsObject.getString("latitude"));
                            detailedRestaurant.setLongitude(tutorialsObject.getString("longitude"));
                            detailedRestaurant.setAddress(tutorialsObject.getString("address"));

                            detailedRestaurant.setPhone(tutorialsObject.getString("phone"));


                            dialog.dismiss();

                            textViewContact.setText(detailedRestaurant.getPhone());
                            textViewContact.setTypeface(typefaceContact);
                            textViewDescription.setText(Html.fromHtml(detailedRestaurant.getDescription()));
                            textViewDescription.setTypeface(typefaceDescription);
                            textViewLocationName.setText(detailedRestaurant.getAddress());
                            textViewLocationName.setTypeface(typefaceContact);
                            textViewTitle.setText(detailedRestaurant.getTitle());
                            textViewTitle.setTypeface(typefaceTitle);
                            textViewType.setText(detailedRestaurant.getRestaurantType());
                            textViewType.setTypeface(typefaceType);
                            Glide.with(context)
                                    .load(detailedRestaurant.getImgSrc())
//                                    .load("https://majestichotelgroup.com/web/majestic/homepage/slider_principal/majestic-1.jpg") // image url
                                    .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                                    .error(R.drawable.ic_launcher_background)  // any image in case of error
                                    .override(200, 200) // resizing
                                    .centerCrop()
                                    .into(imageViewSrc);

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


    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Detailed_RestaurantActivity.this, R.style.TransparentBackground);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_loading);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = detailedRestaurant.getImgSrc();

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                // Save file to sdcard
                File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String fname = "temp.png";
                File file = new File(myDir, fname);
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

            File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            String fname = "temp.png";
            File file = new File(myDir, fname);


            String whatsAppMessage = detailedRestaurant.getTitle();
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
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
                File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String fname = "temp.png";
                File file = new File(myDir, fname);
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
            progressDialog.dismiss();

            File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            String fname = "temp.png";
            File file = new File(myDir, fname);


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
