package com.dal.canadatourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListItem> listItems;

    private Context context;
    public MyAdapter(List<ListItem> listItems, Context context) {

        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ListItem listItem = listItems.get(position);

        holder.textView.setText(listItem.getHead());
        holder.textView1.setText(listItem.getDesc());
        //  holder.imageView.setImageResource(R.drawable.image1);

        Picasso.with(context).load(listItem.getImageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, textView1;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textview);
            textView1 = (TextView) v.findViewById(R.id.textdesc);
            imageView = (ImageView) v.findViewById(R.id.img);
        }
    }
}
