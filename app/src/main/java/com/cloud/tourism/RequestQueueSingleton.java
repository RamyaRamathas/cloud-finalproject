package com.cloud.tourism;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    private static RequestQueueSingleton mInst;
    private RequestQueue mRequestqueue;
    private static Context mCon;

    private RequestQueueSingleton(Context con){
        mCon = con.getApplicationContext();
        mRequestqueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context con){
        if(mInst == null){
            mInst = new RequestQueueSingleton(con.getApplicationContext());
        }
        return mInst;
    }

    private RequestQueue getRequestQueue() {

        if(mRequestqueue == null){
            mRequestqueue = Volley.newRequestQueue(mCon.getApplicationContext());
        }
        return mRequestqueue;
    }

    public <T> void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }
}

