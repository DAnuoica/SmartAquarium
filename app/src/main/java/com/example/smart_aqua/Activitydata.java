package com.example.smart_aqua;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_aqua.ui.adapter.dataAdapter2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activitydata extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydata);
        setID();
        initView(recyclerView);
    }
    public void setID(){
        recyclerView= findViewById(R.id.List);
    }
    public void initView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activitydata.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final ArrayList<data> arrayList = new ArrayList<>();
        final ArrayList<data> arrayList1= new ArrayList<>();
        final dataAdapter2 dataAdapter2= new dataAdapter2(arrayList,this.getApplicationContext());
        recyclerView.setAdapter(dataAdapter2);
        myRef.child("HoCa").child("logs").limitToLast(6*30).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                logs logs= dataSnapshot.getValue(com.example.smart_aqua.logs.class);
                int led;
                if(logs.isLed()) led=R.drawable.led_on;
                else led=R.drawable.led_off;
                arrayList.add(new data(led,logs.getLight(),logs.getTemp(),logs.getTime()));
                dataAdapter2.notifyDataSetChanged();
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
}
