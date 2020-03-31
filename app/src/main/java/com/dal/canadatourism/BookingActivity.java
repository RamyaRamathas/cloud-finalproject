package com.dal.canadatourism;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingActivity extends AppCompatActivity{

    Button b = null;

    int seatsLeft = 0;
    String from;
    String to;
    String amt="0";
    String date;
    String sourceId;
    String seatType;
    String destinationId;

    long duration;
    long tax;

    public static String token;

    String journeyId;
    double amount;
    int totalTickets;

    Map<String, String> cityNames = new HashMap<String, String>();
    Map<String, String> cityProvinces = new HashMap<String, String>();
    Map<String, List<String>> dateSeatType = new HashMap<String, List<String>>();
    Map<String, List<String>> seatTypeAmount = new HashMap<String, List<String>>();
    Map<String, List<String>> seatTypeDuration = new HashMap<String, List<String>>();

    ArrayList<String> locations = new ArrayList<String>();
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<String> seats = new ArrayList<String>();
    ArrayList<String> jduration = new ArrayList<String>();
    ArrayList<String> cost = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> company = new ArrayList<String>();
    ArrayList<String> contact = new ArrayList<String>();
    ArrayList<String> journeyIds = new ArrayList<String>();

    ArrayAdapter<String> locationAdapter = null;
    ArrayAdapter<String> dateAdapter = null;
    ArrayAdapter<String> typeAdapter = null;
    ArrayAdapter<String> seatsAdapter = null;

    TextView txt_tickets=null;
    TextView txt_date=null;
    TextView txt_duration=null;
    TextView txt_tax=null;
    TextView txt_amount=null;

    Spinner fromSpinner = null;
    Spinner toSpinner = null;
    Spinner dateSpinner = null;
    Spinner typeSpinner = null;
    Spinner seatsSpinner = null;

    Button submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //Toast.makeText(this, ""+getIntent().getStringExtra("token"), Toast.LENGTH_SHORT).show();

        to = "Ottawa";

        token = getIntent().getStringExtra("token");
        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();

        b = (Button) findViewById(R.id.button);

        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);
        dateSpinner = (Spinner) findViewById(R.id.date);
        typeSpinner = (Spinner) findViewById(R.id.type);
        seatsSpinner = (Spinner) findViewById(R.id.spinnerSeats);

        submit = (Button) findViewById(R.id.submit);

        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_tickets = (TextView) findViewById(R.id.txt_ticket);
        txt_tax = (TextView) findViewById(R.id.txt_tax);
        txt_duration = (TextView) findViewById(R.id.txt_duration);
        txt_date = (TextView) findViewById(R.id.txt_date);

        locationAdapter = new ArrayAdapter<String>(BookingActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, locations);

        dateAdapter = new ArrayAdapter<String>(BookingActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, dates);

        typeAdapter = new ArrayAdapter<String>(BookingActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, type);

        for(int i=0;i<9;i++)
            seats.add((i+1)+"");

        seatsAdapter = new ArrayAdapter<String>(BookingActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, seats);

        CityAsyncTask asyncTask=new CityAsyncTask();
        asyncTask.execute();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this,OrdersActivity.class));
            }
        });


        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                from=fromSpinner.getSelectedItem().toString();
                sourceId = cityNames.get(from);

                if(toSpinner.getSelectedItem().toString().length()>0){



                    JourneyAsyncTask asyncTask2=new JourneyAsyncTask("journeyBySourceDestination?sourceId="+sourceId+"&destinationId="+destinationId);
                    asyncTask2.execute();
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                to=toSpinner.getSelectedItem().toString();
                destinationId = cityNames.get(to);
                if(fromSpinner.getSelectedItem().toString().length()>0){



                    JourneyAsyncTask asyncTask2=new JourneyAsyncTask("journeyBySourceDestination?sourceId="+sourceId+"&destinationId="+destinationId);
                    asyncTask2.execute();
                }

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                date = dateSpinner.getSelectedItem().toString();
                txt_date.setText(date);
                //Toast.makeText(BookingActivity.this, ""+date, Toast.LENGTH_SHORT).show();
                //Toast.makeText(BookingActivity.this, ""+type, Toast.LENGTH_SHORT).show();


                JourneyAsyncTaskType asyncTask2=new JourneyAsyncTaskType("journeyBySourceDestinationDate?sourceId="+sourceId+"&destinationId="+destinationId+"&date="+date);
                asyncTask2.execute();

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                seatType = typeSpinner.getSelectedItem().toString();
                //Toast.makeText(BookingActivity.this, ""+jduration, Toast.LENGTH_SHORT).show();

                String dur = jduration.get(type.indexOf(seatType));

                txt_duration.setText(dur);

                amt = cost.get(type.indexOf(seatType));
                txt_tickets.setText(totalTickets+"\n($"+amt+" per ticket)");
                //Toast.makeText(BookingActivity.this, ""+journeyIds, Toast.LENGTH_SHORT).show();
                journeyId = journeyIds.get(type.indexOf(seatType));
                long price = Integer.parseInt(amt)*totalTickets;
                double tax = price*0.15;
                txt_tax.setText(tax+"");
                amount = price + tax;
                txt_amount.setText((price+tax)+"");

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        seatsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                totalTickets = Integer.parseInt(seatsSpinner.getSelectedItem().toString());
                txt_tickets.setText(totalTickets+"\n($"+amt+" per ticket)");

                long price = Integer.parseInt(amt)*totalTickets;
                double tax = price*0.15;
                txt_tax.setText(tax+"");
                amount = price + tax;
                txt_amount.setText((price+tax)+"");

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(BookingActivity.this, ""+journeyId+" "+amount+" "+totalTickets, Toast.LENGTH_SHORT).show();

                CheckSeatsAsyncTask a = new CheckSeatsAsyncTask(journeyId, totalTickets);
                a.execute();
            }
        });

    }

    public class CityAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog p = new ProgressDialog(BookingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            //p.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                getCities();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            p.dismiss();
        }
    }

    public class JourneyAsyncTask extends AsyncTask<String, String, String> {

        String command;

        public JourneyAsyncTask(String command){
            this.command = command;
        }

        ProgressDialog p = new ProgressDialog(BookingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dates.clear();
            jduration.clear();
            type.clear();
            cost.clear();
            company.clear();
            contact.clear();
            journeyIds.clear();

            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            //p.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                getJourneys(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            p.dismiss();
        }
    }

    public class JourneyAsyncTaskType extends AsyncTask<String, String, String> {

        String command;

        public JourneyAsyncTaskType(String command){
            this.command = command;
        }

        ProgressDialog p = new ProgressDialog(BookingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            jduration.clear();
            type.clear();
            cost.clear();
            company.clear();
            contact.clear();
            journeyIds.clear();

            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            //p.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                getJourneysSeatType(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            p.dismiss();
        }
    }

    public class CheckSeatsAsyncTask extends AsyncTask<String, String, String> {

        String jid;
        int totTickets;
        String s2="-1";

        public CheckSeatsAsyncTask(String jouenyId, int totTickets){
            this.jid = jouenyId;
            this.totTickets = totTickets;
        }

        ProgressDialog p = new ProgressDialog(BookingActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            //p.show();
        }
        @Override
        protected String doInBackground(String... strings) {

            try {
                s2 = checkSeats(journeyId, totalTickets)+"";
            } catch (final Exception e) {

                e.printStackTrace();
            }
            return s2;
        }
        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            p.dismiss();

        }
    }

    public void getCities(){
        final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/info/cities/all";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int cityId = -1;
                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject = response.getJSONObject(i);

                                cityId = jsonObject.getInt("cityId");
                                String name = jsonObject.getString("name");
                                String province = jsonObject.getString("province");

                                cityNames.put(name, cityId+"");
                                cityProvinces.put(name, province);
                                //Toast.makeText(BookingActivity.this, ""+cityId+" "+jsonObject.getString("name")+" "+jsonObject.getString("province"), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(BookingActivity.this, ""+cityNames, Toast.LENGTH_SHORT).show();
                            }

                            locations.addAll(cityNames.keySet());
                            locationAdapter.notifyDataSetChanged();
                            fromSpinner.setAdapter(locationAdapter);
                            toSpinner.setAdapter(locationAdapter);

                            fromSpinner.setSelection(locations.indexOf(to));

                        } catch (JSONException e) {
                            Toast.makeText(BookingActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(BookingActivity.this, "Could not retrieve data!", Toast.LENGTH_SHORT).show();
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

    public void getJourneys(String command){
        final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/booking/journey/"+command;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int cityId = -1;

                        dateSpinner.setAdapter(dateAdapter);
                        typeSpinner.setAdapter(typeAdapter);
                        seatsSpinner.setAdapter(seatsAdapter);


                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONArray jsonObject = new JSONArray();
                                jsonObject = response.getJSONArray(i);

                                String da = String.valueOf(jsonObject.get(0));
                                String du = String.valueOf(jsonObject.get(1));
                                String co = String.valueOf(jsonObject.get(2));
                                String ty = String.valueOf(jsonObject.get(3));
                                String com = String.valueOf(jsonObject.get(5));
                                String con = String.valueOf(jsonObject.get(6));
                                String jid = String.valueOf(jsonObject.get(7));

//                                Toast.makeText(BookingActivity.this, ""+da+"\n"+du+"\n"+co+"\n"+ty+"\n"+com+"\n"+con+"\n", Toast.LENGTH_SHORT).show();

                                String untildate=da;
                                SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
                                Calendar cal = Calendar.getInstance();
                                cal.setTime( dateFormat.parse(untildate));
                                cal.add( Calendar.DATE, -1 );
                                da=dateFormat.format(cal.getTime());

                                if(!dates.contains(da))
                                    dates.add(da);
                                jduration.add(du);
                                cost.add(co);
                                type.add(ty);
                                company.add(com);
                                contact.add(con);
                                journeyIds.add(jid);
                            }
                            dateAdapter.notifyDataSetChanged();
                            typeAdapter.notifyDataSetChanged();

                        } catch (JSONException | ParseException e) {
                            Toast.makeText(BookingActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(BookingActivity.this, "No Routes Found!", Toast.LENGTH_SHORT).show();
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

    public void getJourneysSeatType(String command) {
        final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/booking/journey/" + command;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int cityId = -1;

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray jsonObject = new JSONArray();
                                jsonObject = response.getJSONArray(i);

                                String da = String.valueOf(jsonObject.get(0));
                                String du = String.valueOf(jsonObject.get(1));
                                String co = String.valueOf(jsonObject.get(2));
                                String ty = String.valueOf(jsonObject.get(3));
                                String com = String.valueOf(jsonObject.get(5));
                                String con = String.valueOf(jsonObject.get(6));
                                String jid = String.valueOf(jsonObject.get(7));

                                //Toast.makeText(BookingActivity.this, "det: "+da+"\n"+du+"\n"+co+"\n"+ty+"\n"+com+"\n"+con+"\n", Toast.LENGTH_SHORT).show();


                                jduration.add(du);
                                cost.add(co);


                                type.add(ty);

                                company.add(com);
                                contact.add(con);
                                journeyIds.add(jid);
                                //Toast.makeText(BookingActivity.this, ""+jid, Toast.LENGTH_SHORT).show();

                            }
                            //Toast.makeText(BookingActivity.this, ""+type, Toast.LENGTH_SHORT).show();
                            typeAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Toast.makeText(BookingActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(BookingActivity.this, "No Routes Found!", Toast.LENGTH_SHORT).show();
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

    public int checkSeats(final String journeyId, final int totalSeats){


        final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/booking/booking/getBookingConfirmation?journeyId="+journeyId+"&totalSeats="+totalSeats;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("Error")){
                            Toast.makeText(BookingActivity.this, "Please select/insert all fields!", Toast.LENGTH_SHORT).show();
                        }else {

                            seatsLeft = Integer.parseInt(response);

                            if (seatsLeft - totalTickets >= 0) {
                                Intent i = new Intent(BookingActivity.this, PaymentActivity.class);
                                i.putExtra("journeyId", journeyId);
                                i.putExtra("amount", amount + "");
                                i.putExtra("seats", totalTickets + "");
                                startActivity(i);
                                startActivity(i);
                            } else {
                                Toast.makeText(BookingActivity.this, "Insufficient seats! (" + seatsLeft + " available!)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", token);
                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                if(dateSpinner.getSelectedItem() == null){
                    params.put("journeyId", "-1");
                }else{
                    params.put("journeyId", journeyId+"");
                }

                params.put("totalSeats", totalSeats+"");

                return params;
            }
        };
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        return seatsLeft;
    }
}
