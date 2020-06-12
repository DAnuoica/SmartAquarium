package com.example.smart_aqua.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_aqua.R;

import java.util.ArrayList;

public class dataAdapter2 extends RecyclerView.Adapter<dataAdapter2.Viewhoder>{
    ArrayList<com.example.smart_aqua.data> data;
    Context context;

    public dataAdapter2(ArrayList<com.example.smart_aqua.data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.item2_layout,parent,false);
        return new Viewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        if(data.get(position).getNhiet()>=30) holder.txtNhiet.setTextColor(Color.RED);
        else if(data.get(position).getNhiet()<30 && data.get(position).getNhiet()>20) holder.txtNhiet.setTextColor(Color.GREEN);
        else holder.txtNhiet.setTextColor(Color.BLUE);
        holder.txtTG.setText(data.get(position).getTGian());
        holder.txtNhiet.setText(String.valueOf(data.get(position).getNhiet())+"°C");
        holder.txtAs.setText(String.valueOf(data.get(position).getASang())+" Lux");
        holder.hinh.setImageResource(data.get(position).getHinh());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewhoder extends RecyclerView.ViewHolder{
        TextView txtAs,txtTG,txtNhiet;
        ImageView hinh;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            txtAs=itemView.findViewById(R.id.txtAs);
            txtNhiet=itemView.findViewById(R.id.txtNhiet);
            txtTG=itemView.findViewById(R.id.txtTg);
            hinh=itemView.findViewById(R.id.img);
        }
    }
}