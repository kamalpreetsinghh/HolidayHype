package com.gbc.holidayhype;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.model.User;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserApi {

    volleyUserInterface mResultCallback = null;
    Context mContext;

    UserApi(volleyUserInterface resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }


    User userDetails = new User();

    private final String TAG = this.getClass().getCanonicalName();

    public UserApi() {

    }

    public void addUser(Context context, User user) {


        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID",user.getUserID());
            jsonObject.put("firstName", user.getFirstName());
            jsonObject.put("lastName",user.getLastName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());

        }
        catch (JSONException e) {
            // handle exception
        }

        String url = "https://holidayhype.herokuapp.com/api/user/insert";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

//                loadingPB.setVisibility(View.GONE);
                Log.e("Data added to api", response.toString());

                Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.v("Data not added to api", error.toString());
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID",user.getUserID());
                params.put("firstName", user.getFirstName());
                params.put("lastName",user.getLastName());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void fetchUser(final String requestType,Context context) {

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://holidayhype.herokuapp.com/api/user/"+id;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User data = new User();


                        mResultCallback.notifySuccess(requestType,response);

                            try {

                                String id = response.getString("userID");
                                String fname = response.getString("firstName");
                                String lname = response.getString("lastName");
                                String email = response.getString("email");
                                String password = response.getString("password");

                                userDetails = new User(id,fname,lname,email,password);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        Log.e(TAG, "onResponse: User data----------"+userDetails );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );

            }
        });

        queue.add(jsonArrayRequest);

    }


    public void updateUser(Context context,User user) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID",user.getUserID());
            jsonObject.put("firstName", user.getFirstName());
            jsonObject.put("lastName",user.getLastName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            // handle exception
        }

        String url = "https://holidayhype.herokuapp.com/api/user/"+user.getUserID();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response) {

//                loadingPB.setVisibility(View.GONE);
                        Log.e("Data added to api------", response.toString());

                        Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.v("Data not added to api", error.toString());
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID",user.getUserID());
                params.put("firstName", user.getFirstName());
                params.put("lastName",user.getLastName());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}



