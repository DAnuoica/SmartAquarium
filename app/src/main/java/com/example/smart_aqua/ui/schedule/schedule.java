package com.example.smart_aqua.ui.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smart_aqua.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class schedule extends Fragment {
    TextView txtdLedbd,txtdLedkt,
            txtdOxibd,txtdOxikt,txtdLocbd,txtdLockt,
            txtdAnchon,txthLedbd,txthLedkt,
            txthOxibd,txthOxikt,txthLocbd,txthLockt,
            txthAnchon;
    Button btnLedbd,btnLedkt,
            btnOxibd,btnOxikt,btnLocbd,btnLockt,
            btnAnchon,btnsave,btncancel;
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
        setSchedule();
        clickSave();
        clickCancel();
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
                btncancel=view.findViewById(R.id.btnCancel);
                btnLocbd=view.findViewById(R.id.btnlocbd);
                btnLockt=view.findViewById(R.id.btnlockt);

    }
    public void Clickbutton(final TextView tvD, final TextView tvH, Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        SimpleDateFormat format= new SimpleDateFormat("hh:mm");
                        tvH.setText(format.format(calendar.getTime()));
                    }
                },gio,phut,true);
                timePickerDialog.show();
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
        });

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
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("HoCa").child("Led").child("start").setValue(txtdLedbd.getText()+" "+txthLedbd.getText());
                myRef.child("HoCa").child("Led").child("stop").setValue(txtdLedkt.getText()+" "+txthLedkt.getText());

                myRef.child("HoCa").child("Oxi").child("start").setValue(txtdOxibd.getText()+" "+txthOxibd.getText());
                myRef.child("HoCa").child("Oxi").child("stop").setValue(txtdOxikt.getText()+" "+txthOxikt.getText());

                myRef.child("HoCa").child("Purifier").child("start").setValue(txtdLocbd.getText()+" "+txthLocbd.getText());
                myRef.child("HoCa").child("Purifier").child("stop").setValue(txtdLockt.getText()+" "+txthLockt.getText());

                myRef.child("HoCa").child("Feed").child("start").setValue(txtdAnchon.getText()+" "+txthAnchon.getText());

                myRef.child("HoCa").child("schedule").setValue(true);
            }
        });
    }
    public void clickCancel(){
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("HoCa").child("schedule").setValue(false);
            }
        });
    }
}
