package com.dal.canadatourism;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Places extends AppCompatActivity {

    private ProgressDialog progressDialog;
    //private static final String url = "http://dev-info.us-east-1.elasticbeanstalk.com/touristLocations";
    private static final String urlq = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/info/cities/all";
    private static final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/info/touristLocations";
    private RecyclerView recyclerView;

    public String temp;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    public String a;
    // private List<ListCityItem> listItems1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_lay);

        recyclerView = (RecyclerView) findViewById(R.id.convo_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        //listItems1 = new ArrayList<>();
        a = MyAdapterCities.cityId;
        System.out.println("Hi ram"+ a);

        loadRecyclerViewData();
    }
    Cities cities = new Cities();
    String temp1 = cities.temp;

    private void loadRecyclerViewData()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Places.this).create();
        alertDialog.setTitle("Loading");
        alertDialog.setMessage("Please wait.....");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

//       progressDialog.setTitle("Loading");
        //     progressDialog.setMessage("Please wait");
        //   progressDialog.setCanceledOnTouchOutside(false);
        // progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                System.out.println("Hello");

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsonObject1 =jsonObject.getJSONObject("_embedded");
                    JSONArray array = jsonObject1.getJSONArray("touristLocations");
                    //JSONArray array = jsonObject1.getJSONArray("cities");
                    System.out.println("Hello2");

                    for (int i =0;i<array.length();i++)
                    {

                        JSONObject j = array.getJSONObject(i);
                       // temp = sp.getString("CityId", "");
                        if(j.getString("cityId") == a) {
                            ListItem item = new ListItem(j.getString("name"), j.getString("features"), j.getString("url"));

                            listItems.add(item);
                        }

                    }

                    adapter = new MyAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
