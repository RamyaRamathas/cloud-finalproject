package com.cloud.tourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        lv = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(OrdersActivity.this, android.R.layout.simple_list_item_1, listVal);

        lv.setAdapter(adapter);

        getBookingInfo("1");

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

    public void getBookingInfo(String userId){
        final String url = "http://192.168.2.16:8080/booking/bookingInfoByUserId?userId="+userId;
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
        );
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}
