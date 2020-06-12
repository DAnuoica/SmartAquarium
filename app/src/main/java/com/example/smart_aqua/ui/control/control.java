package com.example.smart_aqua.ui.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smart_aqua.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class control extends Fragment {
    int Nhiet;
    ToggleButton btnLed,btnAn,btnO2,btnLoc;
    Button btnTangNhiet,btnGiamNhiet;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference();
    TextView tvNhiet;
    public control() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        SetID(view);
        TinhTrang();
        dieukhien();
        return view;
    }
    public void SetID(View view){
        tvNhiet=view.findViewById(R.id.tvnhietdo);
        btnLed=view.findViewById(R.id.btnLed);
        btnTangNhiet=view.findViewById(R.id.btntang);
        btnGiamNhiet=view.findViewById(R.id.btngiam);
        btnAn=view.findViewById(R.id.btnAn);
        btnO2=view.findViewById(R.id.btno2);
        btnLoc=view.findViewById(R.id.btnloc);
    }
    public void TinhTrang(){
        // hien thi nhiet do thoi gian thuc
        myRef.child("HoCa").child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k =Integer.parseInt(dataSnapshot.getValue().toString());
                Nhiet=k;
                tvNhiet.setText(k+"°C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child("HoCa").child("Led").child("value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k= (boolean) dataSnapshot.getValue();
                if(k) btnLed.setChecked(true);
                else btnLed.setChecked(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child("HoCa").child("Oxi").child("value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k= (boolean) dataSnapshot.getValue();
                if(k) btnO2.setChecked(true);
                else btnO2.setChecked(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child("HoCa").child("Feed").child("value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k = (boolean) dataSnapshot.getValue();
                if(k) btnAn.setChecked(true);
                else btnAn.setChecked(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child("HoCa").child("Purifier").child("value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k = (boolean) dataSnapshot.getValue();
                if(k) btnLoc.setChecked(true);
                else btnLoc.setChecked(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dieukhien(){
        CompoundButton.OnCheckedChangeListener listener=
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(buttonView.getId()==R.id.btnLed){
                            if(isChecked) myRef.child("HoCa").child("Led").child("value").setValue(true);
                            else myRef.child("HoCa").child("Led").child("value").setValue(false);
                        }
                        if(buttonView.getId()==R.id.btnAn){
                            if(isChecked) myRef.child("HoCa").child("Feed").child("value").setValue(true);
                            else myRef.child("HoCa").child("Feed").child("value").setValue(false);
                        }
                        if(buttonView.getId()==R.id.btno2){
                            if(isChecked) myRef.child("HoCa").child("Oxi").child("value").setValue(true);
                            else myRef.child("HoCa").child("Oxi").child("value").setValue(false);
                        }
                        if(buttonView.getId()==R.id.btnloc){
                            if(isChecked) myRef.child("HoCa").child("Purifier").child("value").setValue(true);
                            else myRef.child("HoCa").child("Purifier").child("value").setValue(false);
                        }
                    }
                };
        btnLed.setOnCheckedChangeListener(listener);
        btnAn.setOnCheckedChangeListener(listener);
        btnO2.setOnCheckedChangeListener(listener);
        btnLoc.setOnCheckedChangeListener(listener);

        btnGiamNhiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nhiet=Nhiet-1;
                tvNhiet.setText(Nhiet+"°C");
                myRef.child("HoCa").child("Request").setValue(Nhiet);
            }
        });
        btnTangNhiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nhiet=Nhiet+1;
                tvNhiet.setText(Nhiet+"°C");
                myRef.child("HoCa").child("Request").setValue(Nhiet);
            }
        });
    }
}