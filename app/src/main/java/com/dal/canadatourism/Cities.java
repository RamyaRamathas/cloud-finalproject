package com.dal.canadatourism;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cities extends AppCompatActivity {

    TextView textView;
    private RequestQueue mQueue;
    Button button;
    public String temp;
   public static String a;
    private ProgressDialog progressDialog;
    //private static final String url = "http://dev-info.us-east-1.elasticbeanstalk.com/touristLocations";
    private static final String urlq = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/info/cities/all";
    //private static final String url = "https://839z6wvnkc.execute-api.us-east-1.amazonaws.com/dev/info/touristLocations";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private List<ListCityItem> listItems1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_lay);

        recyclerView = (RecyclerView) findViewById(R.id.convo_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        listItems1 = new ArrayList<>();

        load();
        //   loadRecyclerViewData();


        //int images [] = {R.drawable.image1, R.drawable.image2};
        /*textView = findViewById(R.id.text);
        button = findViewById(R.id.btn);
        mQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*       HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) (new URL("http://dev-info.us-east-1.elasticbeanstalk.com/touristLocations/all")).openConnection();
                        connection.setRequestMethod("GET"); // or post
                        connection.connect();
                        InputStream is = connection.getInputStream();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                System.out.println("hello");
                jsonParse();
            }

        });
    }
   private void jsonParse()
   {
       String url = "http://dev-info.us-east-1.elasticbeanstalk.com/touristLocations";
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {

               System.out.println("hello1");
               try {
                   JSONArray jsonArray = response.getJSONArray("_embedded");
                  // System.out.println(jsonArray);
                   for (int i = 0;i<jsonArray.length();i++)
                   {
                       JSONObject cities = jsonArray.getJSONObject(i);

                       //String Name = cities.getString("touristLocations");
                       //String Province = cities.getString("province" + "\n\n");
                       //textView.append(Name + "," + Province);
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
           }
       });

   }*/
    }

    private void load()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Cities.this).create();
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

        JsonArrayRequest stringRequest1 = new JsonArrayRequest(Request.Method.GET, urlq, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray s1) {
                System.out.println("tik");
                try {
//                    JSONObject jsonObject2 = new JSONObject(s1);
//                    JSONObject jsonObject3 = jsonObject2.getJSONObject("_embedded");
//                    JSONArray array1 = jsonObject2.getJSONArray("cities");

                    for (int i =0;i<s1.length();i++)
                    {
                        JSONObject jsonObject = new JSONObject();
                        //jsonObject = s1.getJSONArray(i);
                        jsonObject = s1.getJSONObject(i);
                        a = String.valueOf(jsonObject.get("cityId"));
                        String b = String.valueOf(jsonObject.get("name"));
                        String c = String.valueOf(jsonObject.get("province"));
                        String d = String.valueOf(jsonObject.get("url"));
                        System.out.println(a);
                        //Toast.makeText(MainActivity.this,b,Toast.LENGTH_SHORT).show();
                        System.out.println("hello202");
                        ListCityItem item1 = new ListCityItem(a,b,c,d);
                        listItems1.add(item1);

                    }
                    System.out.println("tok");


                    adapter = new MyAdapterCities(listItems1,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                    System.out.println("ram");

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


        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);
    }
   /* private void loadRecyclerViewData()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

                            ListItem item = new ListItem(j.getString("name"), j.getString("features"), j.getString("url"));

                            listItems.add(item);

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
    */
}