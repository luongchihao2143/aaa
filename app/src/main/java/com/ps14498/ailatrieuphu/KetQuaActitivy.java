package com.ps14498.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Model.User;

import java.util.ArrayList;

public class KetQuaActitivy extends AppCompatActivity {
    Button btntrangchu;
    TextView tvdiem, tvusername, tvchucdanh;
    String chucdanh = null;
    ImageView ivkq;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_actitivy);
        anhxa();
        laydulieu();

        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KetQuaActitivy.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void anhxa(){
        btntrangchu = findViewById(R.id.btntrangchu);
        tvdiem = findViewById(R.id.tvdiem);
        tvusername = findViewById(R.id.tvusername);
        tvchucdanh = findViewById(R.id.tvchucdanh);
        ivkq = findViewById(R.id.ivkq);
    }

    public void laydulieu(){
        Intent i = getIntent();
        String diem = i.getStringExtra("diem");
        Log.d("point", diem);

        String ten = i.getStringExtra("ten");
        Log.d("ten2", ten+"");
        tvusername.setText(ten+" đào được: ");
        if (Integer.parseInt(diem)<=50)  chucdanh = "Tập sự Bigcoin";
        else if (Integer.parseInt(diem)<=100) chucdanh = "Thanh niên Bigcoin";
        else if (Integer.parseInt(diem)<=150) chucdanh = "Lão làng Bigcoin";
        else if (Integer.parseInt(diem)<=200) chucdanh = "Triệu phú Bigcoin";
        else if (Integer.parseInt(diem)<=250) chucdanh = "Tỷ phú Bigcoin";
        else chucdanh = "Ông trùm Bigcoin";
        String cap = chucdanh;
        adduserfirebase(ten, Integer.parseInt(diem), cap);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvdiem.setText(diem+" xu Bigcoin");
                ivkq.setImageResource(R.drawable.bigcoin);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvchucdanh.setText(chucdanh.toString());

            }
        }, 2000);


    }

    public void adduserfirebase(String ten, Integer diem, String cap) {
        User user = new User(ten, cap, diem);
        mData = FirebaseDatabase.getInstance().getReference();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && !snapshot.hasChild("User"+ten))
                {
                   mData.child("User"+ten).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mData.child("User"+ten).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("diem"))
                {
                    String diemCu = snapshot.child("diem").getValue().toString();
                    if(Integer.parseInt(diemCu) < diem)
                    {
                        mData.child("User"+ten).child("diem").setValue(diem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}