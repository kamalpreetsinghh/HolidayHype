package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.gbc.holidayhype.databinding.ActivityAccountBinding;
import com.gbc.holidayhype.model.User;
import com.gbc.holidayhype.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAccountBinding binding;
    private final String TAG = "AccountActivity";
    private UserViewModel userViewModel;
    private RequestQueue requestQueue;
    private UserApi userApi;
    private User userDetails;
    private volleyUserInterface mResultCallback = null;

    @Override
    protected void onStart()
    {
        super.onStart();
        initVolleyCallback();
        userApi = new UserApi(mResultCallback,this);
        userApi.fetchUser("GetCall",AccountActivity.this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        this.binding.logOut.setOnClickListener(this::onClick);
        this.binding.button2.setOnClickListener(this::onClick);
        this.binding.button3.setOnClickListener(this::onClick);
        this.binding.button4.setOnClickListener(this::onClick);

        this.binding.button6.setOnClickListener(this::onClick);

        binding.bottomNavigationView.setSelectedItemId(R.id.fifthFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.secondFragment:

                    break;
                case R.id.thirdFragment:

                    break;

                case R.id.fourthFragment:

                    break;
                case R.id.fifthFragment:
                    startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return true;
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.logOut: {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.button2: {
                    startActivity(new Intent(getApplicationContext(),UserInfoActivity.class));
                    break;
                }
                case R.id.button3: {
                    startActivity(new Intent(getApplicationContext(),UpdateUserInfoActivity.class));
                    break;
                }
                case R.id.button4: {
                    startActivity(new Intent(getApplicationContext(),FlightTicketActivity.class));
                    break;
                }
                case R.id.button6: {
                    startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                    break;
                }
            }
        }
    }

   private void initVolleyCallback(){
        mResultCallback = new volleyUserInterface() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.e(TAG, "Volley requester " + requestType);
                Log.e(TAG, "Volley JSON post" + response);
                try{
                    String name = response.getString("firstName") + " " + response.getString("lastName");
                    binding.textView15.setText(name);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}