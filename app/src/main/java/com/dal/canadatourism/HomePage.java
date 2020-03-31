package com.dal.canadatourism;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private Toolbar mainBar;
    Button btn;
    ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    public static TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        System.out.println("hello2");

        tx = (TextView)findViewById(R.id.textView14);
        tx.setVisibility(View.VISIBLE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
         /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,new Message());
        fragmentTransaction.commit();*/
          bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                   switch (menuItem.getItemId())
                    {
                        case R.id.navigation_messages :
                            fragmentTransaction.replace(R.id.fragment,new Home());
                            menuItem.setChecked(true);
                            fragmentTransaction.commit();
                            tx.setVisibility(View.INVISIBLE);
                            Toast.makeText(HomePage.this, " Home", Toast.LENGTH_SHORT).show();
                            //Intent intentStart = new Intent(HomePage.this, ViewPageAdapter.class);
                            //startActivity(intentStart);
                            //finish();
                            System.out.println("hello21");
                            break;
                        case  R.id.navigation_find :
                            fragmentTransaction.replace(R.id.fragment,new Booking());
                            Toast.makeText(HomePage.this, " Booking", Toast.LENGTH_SHORT).show();
                            menuItem.setChecked(true);
                            tx.setVisibility(View.INVISIBLE);
                            fragmentTransaction.commit();

                            break;
                        case  R.id.navigation_profile :
                            fragmentTransaction.replace(R.id.fragment,new Profile());
                            Toast.makeText(HomePage.this, "Profile", Toast.LENGTH_SHORT).show();
                            menuItem.setChecked(true);
                            tx.setVisibility(View.INVISIBLE);
                            fragmentTransaction.commit();


                            break;
                    }

                    return false;
                }
            });


           /* mainBar = (Toolbar) findViewById(R.id.userMenu);
            setSupportActionBar(mainBar);
            getSupportActionBar().setTitle("Messages"); */



        }

       // System.out.println("hello1");
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }

     /*  private void sendtoStart() {

            Intent intentStart = new Intent(HomePage.this, ViewPageAdapter.class);
            startActivity(intentStart);
            finish();

        }*/

/*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            super.onOptionsItemSelected(item);

            if (item.getItemId() == R.id.logout){

                FirebaseAuth.getInstance().signOut();
                sendtoStart();

            }

            return true;
        }


*/

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        if (item.getItemId() == R.id.srchBooks){
//
//            Intent intentStart = new Intent(HomePage.this, Login.class);
//            startActivity(intentStart);
//            //finish();
//
//        }
//        else if (item.getItemId() == R.id.profileStart){
//            Toast.makeText( HomePage.this, "Still in Progress", Toast.LENGTH_LONG ).show();
//            Intent intentStart = new Intent(HomePage.this, Profile.class);
//            startActivity(intentStart);
//        }
//        /*else if (item.getItemId() == R.id.logout){
//            FirebaseAuth.getInstance().signOut();
//            sendtoStart();
//        }*/
//
//
//        return true;
//    }
    }

