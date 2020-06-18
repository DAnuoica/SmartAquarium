package com.example.smart_aqua.ui.home;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_aqua.Activitydata;
import com.example.smart_aqua.R;
import com.example.smart_aqua.data;
import com.example.smart_aqua.logs;
import com.example.smart_aqua.ui.adapter.dataAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Home extends Fragment {
    ImageView led;
    TextView nhietdo,thucan,Asang,More,tvDate1,tvDate2,tvMin1,tvMin2,tvMax1,tvMax2;
    RecyclerView recyclerView;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference();
    BarChart barChart;
    final Calendar calendar= Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SetID(view);
//        fakedata();
        TinhTrang();
        forecast();
        initView(recyclerView);
        graph();
        clickMore();
        return view ;
    }
    public void SetID(View view){
        Asang=view.findViewById(R.id.tvlight);
        led=view.findViewById(R.id.img_led);
        nhietdo=view.findViewById(R.id.txt_nhiet);
        thucan=view.findViewById(R.id.txt_thucan);
        recyclerView=view.findViewById(R.id.List);
        barChart=view.findViewById(R.id.bar_chart);
        More=view.findViewById(R.id.tvmore);
        tvDate1=view.findViewById(R.id.tvdate1);
        tvDate2=view.findViewById(R.id.tvdate2);
        tvMin1=view.findViewById(R.id.tvmin1);
        tvMin2=view.findViewById(R.id.tvmin2);
        tvMax1=view.findViewById(R.id.tvmax1);
        tvMax2=view.findViewById(R.id.tvmax2);
    }
    public void TinhTrang(){
        //hien thi led on off
        myRef.child("HoCa").child("Led").child("value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k = (boolean) dataSnapshot.getValue();
                if(k) { led.setImageResource(R.drawable.led_on);
                }
                else { led.setImageResource(R.drawable.led_off);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        // hien thi nhiet do thoi gian thuc
        myRef.child("HoCa").child("Temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k =Integer.parseInt(dataSnapshot.getValue().toString());
                if(k>=30) nhietdo.setTextColor(Color.RED);
                else if(k<30 && k>20) nhietdo.setTextColor(Color.GREEN);
                else nhietdo.setTextColor(Color.BLUE);
                nhietdo.setText(k+"°C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        //hien thi cuong do anh sang
        myRef.child("HoCa").child("Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k =Integer.parseInt(dataSnapshot.getValue().toString());
                Asang.setText(k+" lux");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        // load gia tri ngay mai va mot
        Calendar calendar2= Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
        SimpleDateFormat format= new SimpleDateFormat("dd/MM");
        tvDate1.setText(format.format(calendar.getTime()));
        tvDate2.setText(format.format(calendar2.getTime()));
    }
    //load du vao recyclerview
    public void initView(RecyclerView recyclerView) {
  //      final String time = calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
        final String time2="30-05-2020";
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final ArrayList<data> arrayList=new ArrayList<>();
        final dataAdapter dataAdapter=new dataAdapter(arrayList,getActivity().getApplicationContext());
        recyclerView.setAdapter(dataAdapter);
        myRef.child("HoCa").child("logs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                logs logs= dataSnapshot.getValue(com.example.smart_aqua.logs.class);
                int led;
                if(logs.isLed()) led=R.drawable.led_on;
                else led=R.drawable.led_off;
                if(logs.getTime().contains(time2)){
                arrayList.add(new data(led,logs.getLight(),logs.getTemp(),logs.getTime().substring(11)));
                dataAdapter.notifyDataSetChanged();}
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void graph(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        entries.add(new BarEntry(4f, 6));
        entries.add(new BarEntry(8f, 7));
        entries.add(new BarEntry(6f, 8));
        entries.add(new BarEntry(12f, 9));
        entries.add(new BarEntry(18f, 10));
        entries.add(new BarEntry(9f, 11));

        entries.add(new BarEntry(4f, 12));
        entries.add(new BarEntry(8f, 13));
        entries.add(new BarEntry(6f, 14));
        entries.add(new BarEntry(12f, 15));

        BarDataSet dataSet = new BarDataSet(entries, "data");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");

        BarData data = new BarData(labels, dataSet);
        barChart.setData(data);
        barChart.setDescription("nhiet do trung binh 15 ngay ");  // set the description
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barChart.animateY(3000);
    }

    public  void clickMore(){
        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Activitydata.class));
            }
        });
    }
    public void fakedata(){
        Calendar calendar= Calendar.getInstance();
        calendar.set(2020,04,10,01,00);
            for(int i=0;i<30*8;i++) {
                DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String time=dateFormat.format(calendar.getTime());
                calendar.add(Calendar.HOUR_OF_DAY,3);
                Random random= new Random();
                Boolean led = random.nextBoolean();
                int light= random.nextInt(3000);
                int nhiet=random.nextInt(20)+15;
                logs logs= new logs(led,light,nhiet,time);
                myRef.child("HoCa").child("logs").push().setValue(logs);
            }
    }
    public void forecast(){
        getvalueforecast("day1","min",tvMin1);
        getvalueforecast("day1","max",tvMax1);
        getvalueforecast("day2","min",tvMin2);
        getvalueforecast("day2","max",tvMax2);
    }
    public void getvalueforecast(String S1, String S2, final TextView tv){
        myRef.child("HoCa").child("forecast").child(S1).child(S2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k =Integer.parseInt(dataSnapshot.getValue().toString());
                if(k>=30) tv.setTextColor(Color.RED);
                else if(k<30 && k>20) tv.setTextColor(Color.GREEN);
                else tv.setTextColor(Color.BLUE);
                tv.setText(k+"°C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}