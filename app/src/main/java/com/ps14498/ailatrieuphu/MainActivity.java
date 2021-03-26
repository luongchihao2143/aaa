package com.ps14498.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ps14498.ailatrieuphu.Model.User;

public class MainActivity extends AppCompatActivity {
    Button btnchoi;
    EditText edtten;
    ImageView ivchucdanh;
    Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();


                btnchoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            if (edtten.getText().toString().isEmpty()) throw new Exception();
                            Intent i = new Intent(MainActivity.this, QuestionActivity.class);
                            i.putExtra("ten", edtten.getText().toString());
                            startActivity(i);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, "Vui lòng không bỏ trống tên", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ivchucdanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });
        }

    public void anhxa(){
        btnchoi = findViewById(R.id.btnbatdau);
        edtten = findViewById(R.id.edtten);
        ivchucdanh = findViewById(R.id.ivchucdanh);
    }

    public void openDialog(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_bangdiem);
        btnok = dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}