package com.gbc.holidayhype;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface volleyFlightInterface {

        public void notifySuccess(String requestType, JSONArray response);
        public void notifyError(String requestType, VolleyError error);

}

interface volleyFlightInterface2 {

        public void notifySuccess(String requestType, JSONObject response);
        public void notifyError(String requestType, VolleyError error);

}

