package com.ps14498.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ps14498.ailatrieuphu.Model.User;

public class MainActivity extends AppCompatActivity {
    Button btnchoi;
    EditText edtten;
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
        }

    public void anhxa(){
        btnchoi = findViewById(R.id.btnbatdau);
        edtten = findViewById(R.id.edtten);
    }
}