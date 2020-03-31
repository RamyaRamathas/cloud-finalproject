package com.dal.canadatourism;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
   ViewFlipper viewFlipper, viewFlipper1;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
      /*  int images [] = {R.drawable.image1, R.drawable.image2,R.drawable.image4, R.drawable.image3};
        int images1 [] = {R.drawable.image2, R.drawable.image1,R.drawable.image3, R.drawable.image4};
        viewFlipper = findViewById(R.id.flipper);
        viewFlipper1 = findViewById(R.id.flipper1);
        textView = findViewById(R.id.load);

        for (int i = 0; i <images.length;i++)
        {
            imageFlipper(images[i]);
        }

        for (int i = 0; i <images1.length;i++)
        {
            imageFlipper1(images1[i]);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intentStart = new Intent(MainActivity.this, HomePage.class);
                startActivity(intentStart);

            }
        });
    }

    public void imageFlipper(int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        //viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);

    }

    public void imageFlipper1(int image)
    {
        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundResource(image);
        viewFlipper1.addView(imageView1);
        viewFlipper1.setFlipInterval(10000);
        viewFlipper1.setAutoStart(true);

        viewFlipper1.setInAnimation(this, android.R.anim.slide_in_left);
        //viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);

    }
//        setContentView(R.layout.activity_main);


        //viewPager = (ViewPager) findViewById(R.id.viewPager);
//
        //ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
//
       //viewPager.setAdapter(viewPageAdapter);
//        System.out.println("hello1");
//        hello1

      /*  setContentView(R.layout.activity_main);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPageAdapter adapterView = new ViewPageAdapter(this);
        mViewPager.setAdapter(adapterView);
    }
    */




        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                    Intent intentStart = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intentStart);
                    finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

//    private void sendtoStart() {
//        Intent intentStart = new Intent(MainActivity.this, UserLogin.class);
//        startActivity(intentStart);
//        finish();
//    }





}