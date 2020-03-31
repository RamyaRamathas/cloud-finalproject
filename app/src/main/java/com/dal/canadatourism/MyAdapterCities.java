package com.dal.canadatourism;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterCities extends RecyclerView.Adapter<MyAdapterCities.ViewHolder> {
    private List<ListCityItem> listItems1;
    public String h1;
    public static String cityId="";

    private Context context;
    public MyAdapterCities(List<ListCityItem> listItems1, Context context) {

        this.listItems1 = listItems1;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cities,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final ListCityItem listItem1 = listItems1.get(position);

        holder.textView.setText(listItem1.getHead());
        holder.textView1.setText(listItem1.getDesc());
        //holder.textView2.setText(listItem1.getCityId());
        //holder.imageView.setImageResource(R.drawable.image2);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedata();
//                id = textView2.getText().toString();
//                System.out.println(id);
//                // SharedPreferences sharedPreferences = g
//

                // cityName = listItem1.getHead();
                cityId = listItem1.getCityId();

                Intent intentStart = new Intent(context, Places.class);
//
//
                intentStart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentStart);
                // cityURL = listItem1.getImageurl();
                //desc = listItem1.getDesc();

                //Toast.makeText(context, cityId+" "+cityName+" "+desc+" "+cityURL, Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(context).load(listItem1.getImageurl()).into(holder.imageView);

//        holder.textView.setText(listItem1.getHead());
//        holder.textView1.setText(listItem1.getDesc());

        //holder.imageView.setImageResource(R.drawable.image2);



    }

    @Override
    public int getItemCount() {
        return listItems1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, textView1,textView2;
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textView2 = (TextView) v.findViewById(R.id.cityId);
            textView = (TextView) v.findViewById(R.id.textview_c);
            textView1 = (TextView) v.findViewById(R.id.textdesc_c);
            //h1 = textView.length();
            //System.out.println("hi");

            imageView = (ImageView) v.findViewById(R.id.img_citi);
//
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // v.System.out.println("Naren");
//                    //h1 = textView.getText().toString();
//                    //System.out.println(h1.length());
//                    Intent intentStart = new Intent(context, Places.class);
//
//
//                    intentStart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intentStart);
//                }
//            });
        }
    }
}
