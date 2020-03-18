package com.cloud.tourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InvoiceActivity extends AppCompatActivity {

    TextView txtroute, txtamt, txtbtime, txtseats, txtjdate, txtdur, txtbtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        txtroute = (TextView) findViewById(R.id.city);
        txtamt = (TextView) findViewById(R.id.amt);
        txtbtime = (TextView) findViewById(R.id.btime);
        txtseats = (TextView) findViewById(R.id.seats);
        txtjdate = (TextView) findViewById(R.id.jdate);
        txtdur = (TextView) findViewById(R.id.jduration);
        txtbtype = (TextView) findViewById(R.id.bType);

        Intent in = getIntent();
        String jsource = in.getStringExtra("source");
        String jdest = in.getStringExtra("destination");
        String amount = in.getStringExtra("amount");
        String btime = in.getStringExtra("bookTime");
        String totseats = in.getStringExtra("totalSeats");
        String jdate = in.getStringExtra("journeyDate");
        String jdur = in.getStringExtra("duration");
        String btype = in.getStringExtra("busType");

        txtroute.setText(jsource+" - "+jdest);
        txtamt.setText(amount);
        txtbtype.setText(btime);
        txtseats.setText(totseats);
        txtjdate.setText(jdate);
        txtdur.setText(jdur);
        txtbtype.setText(btype);
        txtbtime.setText(btime.substring(0,10));
    }
}
