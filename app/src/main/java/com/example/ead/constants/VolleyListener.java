package com.example.ead.constants;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class VolleyListener implements Response.Listener<String>, Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        System.out.println(response);
    }
}
