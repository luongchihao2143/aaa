package com.ps14498.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ps14498.ailatrieuphu.Model.User;

import java.util.ArrayList;

public class KetQuaActitivy extends AppCompatActivity {
    Button btntrangchu;
    TextView tvdiem, tvusername, tvchucdanh;
    ArrayList<User> listuser;
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
    }

    public void laydulieu(){
        Intent i = getIntent();
        String diem = i.getStringExtra("diem");
        Toast.makeText(this, diem, Toast.LENGTH_SHORT).show();
        tvdiem.setText(diem+" điểm");
        String ten = i.getStringExtra("ten");
        Log.d("ten2", ten+"");
        tvusername.setText(ten+" đạt");
        String chucdanh = null;
        if (Integer.parseInt(diem)<=10)
        {
            chucdanh = "Học sinh cấp 1";
        }
        else if (Integer.parseInt(diem)<=20) chucdanh = "Học sinh cấp 2";
        else if (Integer.parseInt(diem)<=30) chucdanh = "Học sinh cấp 3";
        tvchucdanh.setText(chucdanh.toString());
        listuser = new ArrayList<>();
        listuser.add(new User(ten, chucdanh, Integer.parseInt(diem)));

    }
}