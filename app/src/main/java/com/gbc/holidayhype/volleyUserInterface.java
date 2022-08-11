package com.gbc.holidayhype;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface volleyUserInterface {

        public void notifySuccess(String requestType, JSONObject response);
        public void notifyError(String requestType, VolleyError error);

}
