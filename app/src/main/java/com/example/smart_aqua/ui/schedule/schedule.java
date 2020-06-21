package com.example.smart_aqua.ui.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smart_aqua.R;
import com.example.smart_aqua.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class schedule extends Fragment {
    TextView txtdLedbd,txtdLedkt,
            txtdOxibd,txtdOxikt,txtdLocbd,txtdLockt,
            txtdAnchon,txthLedbd,txthLedkt,
            txthOxibd,txthOxikt,txthLocbd,txthLockt,
            txthAnchon;
    Button btnLedbd,btnLedkt,
            btnOxibd,btnOxikt,btnLocbd,btnLockt,
            btnAnchon;
    ToggleButton btnsave;
    Calendar calendar= Calendar.getInstance();
    int gio = calendar.get(Calendar.HOUR_OF_DAY);
    int phut = calendar.get(Calendar.MINUTE);
    int ngay = calendar.get(Calendar.DATE);
    int thang = calendar.get(Calendar.MONTH);
    int nam = calendar.get(Calendar.YEAR);
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        setID(view);
        check();
        setSchedule();
        clickSave();
        return view;
    }
    public void setID(View view){
                txtdLedbd=view.findViewById(R.id.txtdledbd);
                txtdLedkt=view.findViewById(R.id.txtdledkt);
                txtdOxibd=view.findViewById(R.id.txtdoxibd);
                txtdOxikt=view.findViewById(R.id.txtdoxikt);
                txtdAnchon=view.findViewById(R.id.txtdanchon);
                txtdLocbd=view.findViewById(R.id.txtdlocbd);
                txtdLockt=view.findViewById(R.id.txtdlockt);

                 txthLedbd=view.findViewById(R.id.txthledbd);
                 txthLedkt=view.findViewById(R.id.txthledkt);
                 txthOxibd=view.findViewById(R.id.txthoxibd);
                 txthOxikt=view.findViewById(R.id.txthoxikt);
                 txthAnchon=view.findViewById(R.id.txthanchon);
                 txthLocbd=view.findViewById(R.id.txthlocbd);
                 txthLockt=view.findViewById(R.id.txthlockt);

                btnLedbd=view.findViewById(R.id.btnledbd);
                btnLedkt=view.findViewById(R.id.btnledkt);
                btnOxibd=view.findViewById(R.id.btnoxibd);
                btnOxikt=view.findViewById(R.id.btnoxikt);
                btnAnchon=view.findViewById(R.id.btnanchon);
                btnsave=view.findViewById(R.id.btnsave);
                btnLocbd=view.findViewById(R.id.btnlocbd);
                btnLockt=view.findViewById(R.id.btnlockt);

    }
    public void Clickbutton(final TextView tvD, final TextView tvH, Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHour(tvH);
                setDay(tvD);
            }
        });

    }
    public void setHour(final TextView tvH){
        TimePickerDialog timePickerDialog= new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                SimpleDateFormat format= new SimpleDateFormat("kk:mm");
                tvH.setText(" "+format.format(calendar.getTime()));
            }
        },gio,phut,true);
        timePickerDialog.show();
    }
    public void setDay(final TextView tvD){
        DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DATE, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                SimpleDateFormat format= new SimpleDateFormat("dd-MM-YYYY");
                tvD.setText(format.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
    public void setSchedule(){
        Clickbutton(txtdLedbd,txthLedbd,btnLedbd);
        Clickbutton(txtdLedkt,txthLedkt,btnLedkt);

        Clickbutton(txtdLocbd,txthLocbd,btnLocbd);
        Clickbutton(txtdLockt,txthLockt,btnLockt);

        Clickbutton(txtdOxibd,txthOxibd,btnOxibd);
        Clickbutton(txtdOxikt,txthOxikt,btnOxikt);

        Clickbutton(txtdAnchon,txthAnchon,btnAnchon);
    }
    public void clickSave(){
        btnsave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    xulysaisot(txtdLedbd,txthLedbd,txtdLedkt,txthLedkt);
                    xulysaisot(txtdLocbd,txthLocbd,txtdLockt,txthLockt);
                    xulysaisot(txtdOxibd,txthOxibd,txtdOxikt,txthOxikt);
                    myRef.child("HoCa").child("Led").child("start").setValue(""+txtdLedbd.getText()+txthLedbd.getText());
                    myRef.child("HoCa").child("Led").child("stop").setValue(""+txtdLedkt.getText()+txthLedkt.getText());
                    myRef.child("HoCa").child("Oxi").child("start").setValue(""+txtdOxibd.getText()+txthOxibd.getText());
                    myRef.child("HoCa").child("Oxi").child("stop").setValue(""+txtdOxikt.getText()+txthOxikt.getText());
                    myRef.child("HoCa").child("Purifier").child("start").setValue(""+txtdLocbd.getText()+txthLocbd.getText());
                    myRef.child("HoCa").child("Purifier").child("stop").setValue(""+txtdLockt.getText()+txthLockt.getText());
                    myRef.child("HoCa").child("Feed").child("start").setValue(""+txtdAnchon.getText()+txthAnchon.getText());
                    myRef.child("HoCa").child("Schedule").setValue(true, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError!=null){
                                Toast.makeText(getActivity(), "hẹn giờ thất bại " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            else Toast.makeText(getActivity(), "Đã hẹn giờ thành công ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    clearSchedule();
                    check();
                }
            }
        });
    }
    public void clearSchedule(){
        myRef.child("HoCa").child("Led").child("start").setValue("");
        myRef.child("HoCa").child("Led").child("stop").setValue("");
        myRef.child("HoCa").child("Oxi").child("start").setValue("");
        myRef.child("HoCa").child("Oxi").child("stop").setValue("");
        myRef.child("HoCa").child("Purifier").child("start").setValue("");
        myRef.child("HoCa").child("Purifier").child("stop").setValue("");
        myRef.child("HoCa").child("Feed").child("start").setValue("");
        myRef.child("HoCa").child("Schedule").setValue(false);
    }
    public void check(){
        myRef.child("HoCa").child("Schedule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean k= (boolean) dataSnapshot.getValue();
                if(k) btnsave.setChecked(true);
                else {
                    btnsave.setChecked(false);
                    xoaTV(txtdAnchon,txthAnchon,txtdLedbd,txthLedbd);
                    xoaTV(txtdLedbd,txthLedbd,txtdLedkt,txthLedkt);
                    xoaTV(txtdOxibd,txthOxibd,txtdOxikt,txthOxikt);
                    xoaTV(txtdLocbd,txthLocbd,txtdLockt,txthLockt);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        TinhTrang("Feed","start",txtdAnchon,txthAnchon);
        TinhTrang("Led","start",txtdLedbd,txthLedbd);
        TinhTrang("Led","stop",txtdLedkt,txthLedkt);
        TinhTrang("Oxi","start",txtdOxibd,txthOxibd);
        TinhTrang("Oxi","stop",txtdOxikt,txthOxikt);
        TinhTrang("Purifier","start",txtdLocbd,txthLocbd);
        TinhTrang("Purifier","stop",txtdLockt,txthLockt);
    }
    public void TinhTrang(String S1, String S2, final TextView tv1, final TextView tv2){
        myRef.child("HoCa").child(S1).child(S2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                if(s.length()!=0) {
                    tv1.setText(s.substring(0, 10));
                    tv2.setText(s.substring(10));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void xulysaisot(TextView tvd1,TextView tvh1,TextView tvd2,TextView tvh2) {
        if(tvd1.length()!=0&&tvd2.length()!=0&&tvh1.length()!=0&&tvh2.length()!=0) {
            String S1 = "" + tvd1.getText() + tvh1.getText();
            String S2 = "" + tvd2.getText() + tvh2.getText();
            Calendar ca1 = Calendar.getInstance();
            Calendar ca2 = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            try {
                ca1.setTime(format.parse(S1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                ca2.setTime(format.parse(S2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int check = ca1.compareTo(ca2);
            if (check >= 0) {
                Toast.makeText(getActivity(), "nhập ngày giờ không phù hợp, vui lòng nhâp lại ", Toast.LENGTH_LONG).show();
                tvd1.setText("");
                tvd2.setText("");
                tvh1.setText("");
                tvh2.setText("");
            }
        }
    }
    public void xoaTV(final TextView s1,final  TextView s2,final TextView s3,final  TextView s4){
        s1.setText("");
        s2.setText("");
        s3.setText("");
        s4.setText("");
    }
}
