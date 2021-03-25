package com.ps14498.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ps14498.ailatrieuphu.Model.Question;

import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    Button btna, btnb, btnc, btnd, btntieptuc, btnchoilai;
    TextView tvstt, tvcauhoi, tvdapan;
    ArrayList<Question> list;
    int idcauhoi = 1;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        anhxa();
        doidulieulist();
        random = new Random();
        int stt =  random.nextInt(list.size());
        daydulieu(list.get(stt));
        Log.d("stt", stt+"");
    }

    public void anhxa(){
        btna = findViewById(R.id.btnA);
        btnb = findViewById(R.id.btnB);
        btnc = findViewById(R.id.btnC);
        btnd = findViewById(R.id.btnD);
        tvcauhoi = findViewById(R.id.tvcauhoi);
        tvstt = findViewById(R.id.tvstt);
    }

    public void doidulieulist(){
        list = new ArrayList<>();
        list.add(new Question(0, "Hà Nội là thủ đô của nước nào? ", "Mỹ", "Campuchia", "Việt Nam", "Thái Lan", "Việt Nam"));
        list.add(new Question(1, "Đâu là gia cầm? ", "Heo", "Gà", "Khỉ", "Người", "Gà"));
        list.add(new Question(2, "TP.Hồ Chí Minh ở miền nào Việt Nam", "Bắc", "Trung", "Nam", "Không có đáp án", "Nam"));
    }

    public void daydulieu(Question question){
        if (question==null) return;
        tvstt.setText("Câu số: "+idcauhoi);
        tvcauhoi.setText(question.getCauhoi()+"");
        btna.setText(question.getA()+"");
        btnb.setText(question.getB()+"");
        btnc.setText(question.getC()+"");
        btnd.setText(question.getD()+"");

        btna.setBackgroundResource(R.drawable.btn_xanh);
        btnc.setBackgroundResource(R.drawable.btn_xanh);
        btnb.setBackgroundResource(R.drawable.btn_xanh);
        btnd.setBackgroundResource(R.drawable.btn_xanh);

        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btna, question);

            }
        });

        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnb, question);

            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnc, question);
            }
        });

        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ktcauhoi(btnd, question);
            }
        });


    }

    public void ktcauhoi(Button btn, Question question){
        btn.setBackgroundResource(R.drawable.btn_nhan);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (btn.getText().toString().equalsIgnoreCase(question.getDapan().toString()))
                {
                    hienthiDialog("dung", question);
                }
                else
                {
                        hienthiDialog("sai", question);
                }
            }
        },1000);
    }

    public void hienthiDialog(String trangthai, Question questions){
        Dialog dialog = new Dialog(this);
        if (trangthai.equalsIgnoreCase("dung"))
        {
            dialog.setContentView(R.layout.dialog_dung);
            idcauhoi++;
            btntieptuc = dialog.findViewById(R.id.btntieptuc);
            btntieptuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idcauhoi>list.size())
                    {
                        ketqua(questions);
                    }
                    else {
                        random = new Random();
                        int stt2 = random.nextInt(list.size());
                        Log.d("stt2", stt2+"");
                        daydulieu(list.get(stt2));
                    }
                    dialog.dismiss();
                }
            });
        }
        else
        {
            dialog.setContentView(R.layout.dialog_sai);
            tvdapan = dialog.findViewById(R.id.tvdapan);
            tvdapan.setText(questions.getDapan().toString());
            btnchoilai = dialog.findViewById(R.id.btnchoilai);
            btnchoilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ketqua(questions);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void ketqua(Question questions){
        Intent intent = getIntent();
        String ten =intent.getStringExtra("ten");
        Log.d("ten", ten);
        Toast.makeText(QuestionActivity.this, list.size()+"", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(QuestionActivity.this, KetQuaActitivy.class);
        Log.d("số câu hỏi", idcauhoi+"");
        int diem = (idcauhoi-1)*10;
        i.putExtra("diem", diem+"");
        i.putExtra("ten", ten+"");
        Log.d("Số câu hiện tại", diem+"");
        startActivity(i);
    }
}