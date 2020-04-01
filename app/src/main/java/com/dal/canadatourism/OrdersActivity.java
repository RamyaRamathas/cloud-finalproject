package com.dal.canadatourism;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {

    ListView lv = null;
    ArrayAdapter<String> adapter = null;
    ArrayList<String> source = new ArrayList<>();
    ArrayList<String> destination = new ArrayList<>();
    ArrayList<String> journeyDate = new ArrayList<>();
    ArrayList<String> duration = new ArrayList<>();
    ArrayList<String> bookingDate = new ArrayList<>();
    ArrayList<String> amt = new ArrayList<>();
    ArrayList<String> totalSeats = new ArrayList<>();
    ArrayList<String> company = new ArrayList<>();
    ArrayList<String> contact = new ArrayList<>();
    ArrayList<String> busType = new ArrayList<>();
    ArrayList<String> listVal = new ArrayList<>();
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        SharedPreferences pref = (OrdersActivity.this).getSharedPreferences("login",MODE_WORLD_WRITEABLE); // 0 - for private mode
        token = pref.getString("token","");
        Log.d("Order token",""+token);
        lv = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(OrdersActivity.this, android.R.layout.simple_list_item_1, listVal);

        lv.setAdapter(adapter);

        getBookingInfo();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String jsource = source.get(i);
                String jdest = String.valueOf(destination.get(i));
                String amount = String.valueOf(amt.get(i));
                String btime = String.valueOf(bookingDate.get(i));
                String totseats = String.valueOf(totalSeats.get(i));
                String jdate = String.valueOf(journeyDate.get(i));
                String jdur = String.valueOf(duration.get(i));
                String bType = String.valueOf(busType.get(i));

                Intent in = new Intent(OrdersActivity.this, InvoiceActivity.class);
                in.putExtra("source",jsource);
                in.putExtra("destination",jdest);
                in.putExtra("amount",amount);
                in.putExtra("bookTime",btime);
                in.putExtra("totalSeats",totseats);
                in.putExtra("journeyDate",jdate);
                in.putExtra("duration",jdur);
                in.putExtra("busType",bType);
                startActivity(in);

                //Toast.makeText(OrdersActivity.this, ""+jsource+"\n"+jdest+"\n"+amount+"\n"+btime+"\n"+totseats+"\n"+jdate+"\n"+jdur+"\n"+bType, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);

        MenuItem item=menu.add("Title"); //your desired title here
        item.setIcon(R.drawable.home); //your desired icon here
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                return false;
            }
        });

        return true;
    }

    public void getBookingInfo(){
        final String url = "https://kv80bjp518.execute-api.us-east-1.amazonaws.com/prod/booking/booking/bookingInfoByUserId";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONArray jsonObject = new JSONArray();
                                jsonObject = response.getJSONArray(i);

                                String bid = String.valueOf(jsonObject.get(0));
                                String tmode = String.valueOf(jsonObject.get(1));
                                String amount = String.valueOf(jsonObject.get(2));
                                String btime = String.valueOf(jsonObject.get(3));
                                String totseats = String.valueOf(jsonObject.get(4));
                                String jdate = String.valueOf(jsonObject.get(5));
                                String jdur = String.valueOf(jsonObject.get(6));
                                String bType = String.valueOf(jsonObject.get(7));
                                String cname = String.valueOf(jsonObject.get(8));
                                String ccontact = String.valueOf(jsonObject.get(9));
                                String jsource = String.valueOf(jsonObject.get(10));
                                String jdest = String.valueOf(jsonObject.get(11));

                                source.add(jsource);
                                destination.add(jdest);
                                journeyDate.add(jdate);
                                bookingDate.add(btime);
                                amt.add(amount);
                                totalSeats.add(totseats);
                                duration.add(jdur);
                                busType.add(bType);

                                listVal.add(jsource+" - "+jdest+" ("+jdate+")");
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Toast.makeText(OrdersActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(OrdersActivity.this, "No Routes Found!", Toast.LENGTH_SHORT).show();
            }
        }
        ){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", token);
                return params;
            }
        };
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}
