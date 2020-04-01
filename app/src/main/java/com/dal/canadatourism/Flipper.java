package com.dal.canadatourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class Flipper extends AppCompatActivity {


    ViewFlipper viewFlipper, viewFlipper1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        int images[] = {R.drawable.ig1, R.drawable.ig2, R.drawable.ig3, R.drawable.ig4};
        int images1[] = {R.drawable.image2, R.drawable.image1, R.drawable.image3, R.drawable.image4};
        viewFlipper = findViewById(R.id.flipper);
        viewFlipper1 = findViewById(R.id.flipper1);
        //HomePage activity = (HomePage) getActivity();

        textView = findViewById(R.id.load);

        for (int i = 0; i < images.length; i++) {
            imageFlipper(images[i]);
        }

        for (int i = 0; i < images1.length; i++) {
            imageFlipper1(images1[i]);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intentStart = new Intent(Flipper.this, Cities.class);
                startActivity(intentStart);

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

    public void imageFlipper(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        //viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);

    }

    public void imageFlipper1(int image) {
        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundResource(image);
        viewFlipper1.addView(imageView1);
        viewFlipper1.setFlipInterval(4000);
        viewFlipper1.setAutoStart(true);

        viewFlipper1.setInAnimation(this, android.R.anim.slide_in_left);
    }
}
