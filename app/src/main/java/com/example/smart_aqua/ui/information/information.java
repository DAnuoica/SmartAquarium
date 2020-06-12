package com.example.smart_aqua.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smart_aqua.R;
import com.example.smart_aqua.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class information extends Fragment {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference();
    ListView listView;
    ArrayList arrayList = new ArrayList();
    ArrayAdapter adapter;

    FirebaseAuth mAuth;
    Button btnLogOut;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        btnLogOut=view.findViewById(R.id.btnlogout);
        mAuth = FirebaseAuth.getInstance();

        listView=view.findViewById(R.id.lv);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), login.class));
            }
        });
//        test();
        adapter =new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        return view;
    }
    public void test(){
        myRef.child("HoCa").child("logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.add(dataSnapshot.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"loi ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}