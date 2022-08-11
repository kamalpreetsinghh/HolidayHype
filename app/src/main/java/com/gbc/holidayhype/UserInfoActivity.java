package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.gbc.holidayhype.databinding.ActivityUserInfoBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityUserInfoBinding binding;
    private final String TAG = "MainActivity";
    private UserApi userApi;
    private volleyUserInterface mResultCallback = null;

    @Override
    protected void onStart()
    {
        super.onStart();
        initVolleyCallback();
        userApi = new UserApi(mResultCallback,this);
        userApi.fetchUser("GetCall",this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goBackToAccount5.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToAccount5: {
                    finish();
                    break;
                }
            }
        }
    }

    private void initVolleyCallback(){
        mResultCallback = new volleyUserInterface() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.e(TAG, "Volley requester " + requestType);
                Log.e(TAG, "Volley JSON post" + response);
                try{
                    String name = response.getString("firstName") + " " + response.getString("lastName");
                    binding.txtFullName.setText(name);

                    binding.txtEmail.setText(response.getString("email"));
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}