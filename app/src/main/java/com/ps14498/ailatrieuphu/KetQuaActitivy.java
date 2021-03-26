package com.ps14498.ailatrieuphu;

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

import com.ps14498.ailatrieuphu.Model.User;

import java.util.ArrayList;

public class KetQuaActitivy extends AppCompatActivity {
    Button btntrangchu;
    TextView tvdiem, tvusername, tvchucdanh;
    String chucdanh = null;
    ImageView ivkq;
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
        tvusername.setText(ten+" đạt");
        if (Integer.parseInt(diem)<=50)  chucdanh = "Tập sự Bigcoin";
        else if (Integer.parseInt(diem)<=100) chucdanh = "Thanh niên Bigcoin";
        else if (Integer.parseInt(diem)<=150) chucdanh = "Lão làng Bigcoin";
        else if (Integer.parseInt(diem)<=200) chucdanh = "Triệu phú Bigcoin";
        else if (Integer.parseInt(diem)<=250) chucdanh = "Tỷ phú Bigcoin";
        else chucdanh = "Ông trùm Bigcoin";


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
}