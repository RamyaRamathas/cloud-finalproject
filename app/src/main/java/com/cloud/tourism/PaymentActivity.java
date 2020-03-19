package com.cloud.tourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    EditText card, cname, mm, yy, cvv;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        card = (EditText) findViewById(R.id.cardNo);
        cname = (EditText) findViewById(R.id.cname);
        mm = (EditText) findViewById(R.id.mm);
        yy = (EditText) findViewById(R.id.yy);
        cvv = (EditText) findViewById(R.id.cvv);
        pay = (Button) findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strCard = card.getText().toString();
                if(strCard.length()==16) {
                    strCard = strCard.substring(0, 4) + "-" + strCard.substring(4, 8) + "-" + strCard.substring(8, 12) + "-" + strCard.substring(12, 16);
                }
                String strCname = cname.getText().toString();
                String strMM = mm.getText().toString();
                String strYY = yy.getText().toString();
                String strCVV = cvv.getText().toString();

                Intent i = getIntent();
                int journeyId = Integer.parseInt(i.getStringExtra("journeyId"));
                Double amount = Double.parseDouble(i.getStringExtra("amount"));
                int seats = Integer.parseInt(i.getStringExtra("seats"));

                book(1, journeyId, amount, seats, strCard, strCname, strMM, strYY, strCVV);

            }
        });
    }

    public void book(final int userId, final int journeyId, final Double amt, final int totalSeats, final String strCard, final String strCname, final String strMM, final String strYY, final String strCVV){

        final String url = "http://192.168.2.16:8080/booking/addBooking";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PaymentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId", userId+"");
                params.put("journeyId", journeyId+"");
                params.put("transactionMode", "Credit Card");
                params.put("amount", amt+"");
                params.put("totalSeats", totalSeats+"");
                params.put("cardNumber", strCard+"");
                params.put("holderName", strCname+"");
                params.put("mm", strMM+"");
                params.put("yy", strYY+"");
                params.put("cvv", strCVV+"");

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("Authorization", "Nintendo Gameboy");

                return params;
            }
        };
        RequestQueueSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}
