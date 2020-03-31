package com.dal.canadatourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Booking extends Fragment {

    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_layout, container, false);
        HomePage activity = (HomePage) getActivity();
        textView = view.findViewById(R.id.booking_id);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(getActivity(), Login.class);
                startActivity(intentStart);
//        finish();
            }
        });



        return view;

    }
}
