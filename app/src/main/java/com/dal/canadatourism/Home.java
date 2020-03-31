package com.dal.canadatourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {


    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        btn = view.findViewById(R.id.home_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(getActivity(), Flipper.class);
                startActivity(intentStart);
//        finish();
            }
        });



        return view;

    }

    /*ViewFlipper viewFlipper, viewFlipper1;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        int images [] = {R.drawable.image1, R.drawable.image2,R.drawable.image4, R.drawable.image3};
        int images1 [] = {R.drawable.image2, R.drawable.image1,R.drawable.image3, R.drawable.image4};
        viewFlipper = view.findViewById(R.id.flipper);
        viewFlipper1 = view.findViewById(R.id.flipper1);
        textView = view.findViewById(R.id.load);

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
                Intent intentStart = new Intent(getActivity(), HomePage.class);
                startActivity(intentStart);

            }
        });
        return view;
    }



    public void imageFlipper(int image)
    {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getContext(), android.R.anim.fade_in);
        //viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);

    }

    public void imageFlipper1(int image)
    {
        ImageView imageView1 = new ImageView(getContext());
        imageView1.setBackgroundResource(image);
        viewFlipper1.addView(imageView1);

        viewFlipper1.setFlipInterval(10000);
        viewFlipper1.setAutoStart(true);

        viewFlipper1.setInAnimation(getContext(), android.R.anim.slide_in_left);
        //viewFlipper.setInAnimation(this, android.R.anim.slide_out_right);

    }

*/

}
