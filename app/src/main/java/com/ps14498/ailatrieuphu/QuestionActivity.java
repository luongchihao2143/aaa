package com.ps14498.ailatrieuphu;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14498.ailatrieuphu.Model.Question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    Button btna, btnb, btnc, btnd, btntieptuc, btnchoilai;
    TextView tvstt, tvcauhoi, tvdapan, cau5, cau10, cau15, cau20, cau25, cau30;
    ArrayList<Question> list;
    int idcauhoi = 0;
    Random random;
    DatabaseReference mData;
    String []question = {"Question","Question2", "Question3", "Question4"};
    Button btnxn;
    ImageView ivchucdanh;
    int diem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        anhxa();
        themdulieufirebase();
        doidulieulist();
        random = new Random();
//        int stt =  random.nextInt(list.size());
        daydulieu(list.get(idcauhoi));
//        Log.d("stt", stt+"");

        ivchucdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    public void anhxa(){
        btna = findViewById(R.id.btnA);
        btnb = findViewById(R.id.btnB);
        btnc = findViewById(R.id.btnC);
        btnd = findViewById(R.id.btnD);
        tvcauhoi = findViewById(R.id.tvcauhoi);
        tvstt = findViewById(R.id.tvstt);
        ivchucdanh = findViewById(R.id.ivchucdanh);
    }

    public void doidulieulist(){
        list = new ArrayList<>();

        list.add(new Question(1, "TP.Hồ Chí Minh ở miền nào Việt Nam", "Bắc", "Trung", "Nam", "Không có đáp án", "Nam"));

        for(int i=1;i<50;i++) {
            mData = FirebaseDatabase.getInstance().getReference("Question"+i);
            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        //
////                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
////                        while (iterator.hasNext())
////                        {
////                            iterator.next().getKey();
//                            }
                        //
                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        String cauhoi = (String) map.get("cauhoi");
                        int id = Integer.parseInt(String.valueOf(map.get("id")));
                        String ans = (String) map.get("dapan");
                        String a = (String) map.get("a");
                        String b = (String) map.get("b");
                        String c = (String) map.get("c");
                        String d = (String) map.get("d");
                        list.add(new Question(id, cauhoi, a, b, c, d, ans));
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void daydulieu(Question question){
        if (question==null) return;
        int socau = idcauhoi+1;
        tvstt.setText("Câu số: "+socau);
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
                    Log.d("list sai", list.size()+"");
                    if (idcauhoi>=list.size())
                    {
                        ketqua(questions);
                    }
                    else {
                        random = new Random();
                        int stt2 = random.nextInt(list.size());
                        Log.d("stt2", stt2+"");
                        daydulieu(list.get(idcauhoi));
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
        Intent i = new Intent(QuestionActivity.this, KetQuaActitivy.class);
         diem = (idcauhoi)*10;
        i.putExtra("diem", diem+"");
        i.putExtra("ten", ten+"");
        Log.d("Số câu hiện tại", diem+"");
        startActivity(i);
    }

    public void themdulieufirebase(){
        mData = FirebaseDatabase.getInstance().getReference();
        Question question = new Question(2, "1MB(Mega byte) bằng:", "1024GB", "1024KB", "1000KB", "1000B","1024KB");
        mData.child("Question2").setValue(question);

        question = new Question(3, "Để cài đặt thêm các bộ Font trong môi trường windows, ta dùng chức năng: ", "Destop của Control Panel", "Regional Setting của Control Panel", "System của Control Panel", "Font  của Control Panel","Font  của Control Panel");
        mData.child("Question3").setValue(question);

        question = new Question(4, "Có thể hồi phục (restore) các tập tin bị xoá sau khi nhấn phím Delete bằng cách: ", "Mở Recycle Bin", "Mở Internet Explorer", "Mở My Computer", "Mở My Documents","Mở Recycle Bin");
        mData.child("Question4").setValue(question);

        question = new Question(5, "Đặc điểm chính của Virus máy tính là: ", "Phá hoại", "Lây lan", "Tự nhân bản", "Cả 3 câu A B C đều đúng","Cả 3 câu A B C đều đúng");
        mData.child("Question5").setValue(question);

        question = new Question(6, "Để chọn cửa sổ của chương trình cần làm việc trong Windows ta chọn", "Nhấn chọn biểu tượng chương trình trên thanh Taskbar", "Nhấn giữ phím Alt và gõ phím Tab cho đến khi chọn được chương trình", "(A) và (B) đúng", "(A) và (B) sai","(A) và (B) đúng");
        mData.child("Question6").setValue(question);

        question = new Question(7, "Để tạo biểu tượng (Shotcut) của chương trình lên màn hình Desktop, bấm chuột phải vào tập tin cần tạo shortcut và chọn:", "New/Folder", "Creat Shortcut", "Mở My Computer", "Cả (B) và (C) đều đúng","Creat Shortcut");
        mData.child("Question7").setValue(question);

        question = new Question(50, "Chương trình Windows Explore dùng để:", "Quản lý văn bản", "Quản lý thư mục ", "Quản lý tập tin, thư mục", "Chọn nền văn bản","Quản lý tập tin, thư mục");
        mData.child("Question50").setValue(question);

        question = new Question(49, "Để xoá ký tự đứng trước (con trỏ) điểm nháy, ta bấm phím:", "Page Up", "Page Down", "Delete", "Backspace","Backspace");
        mData.child("Question49").setValue(question);

        question = new Question(48, "Muốn gỡ bỏ một chương trình đã cài đặt vào trong máy. Sau khi vào Start/Settings/Control panel, ta thực hiện như sau: ","Mở File/Remove Program, chọn chương trình cần gỡ bỏ, nhấn nút Remove", "Mở New/ Accessories, chọn chương trình cần gỡ bỏ, nhấn Remove", "Mở Program/Accessories, chọn chương trình cần gỡ bỏ, nhấn nút emove",
                "Mở Add or Remove program, chọn chương trình cần gỡ bỏ, nhấn nút Remove",
                "Mở Add or Remove program, chọn chương trình cần gỡ bỏ, nhấn nút Remove");
        mData.child("Question48").setValue(question);

        question = new Question(47, "Để thực thi một chương trình trong Windows ta làm như sau:", "Nháy kép chuột trái vào File thực thi", "Bấm chuột phải vào File thực thi, chọn Open", "Nháy đơn chuột trái vào File thực thi, bấm phím Enter", "Cả 3 cách trên đều đúng","Cả 3 cách trên đều đúng");
        mData.child("Question47").setValue(question);

        question = new Question(46, "Để đánh dấu chọn một cách không liên tục các tập tin hay thư mục trong Windows, trong khi nhấn chuột trái (Left Click) và ta cần giữ phím:", "Shift", "Ctrl", "Alt", "Insert","Ctrl");
        mData.child("Question46").setValue(question);




    }

        public void openDialog(){
            Dialog dialog = new Dialog(QuestionActivity.this);
            dialog.setContentView(R.layout.dialog_bangdiem);
            btnxn = dialog.findViewById(R.id.btnok);
            cau5 = dialog.findViewById(R.id.cau5);
            cau10 = dialog.findViewById(R.id.cau10);
            cau15 = dialog.findViewById(R.id.cau15);
            cau20 = dialog.findViewById(R.id.cau20);
            cau25 = dialog.findViewById(R.id.cau25);
            cau30 = dialog.findViewById(R.id.cau30);
            if (diem<=50) cau5.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem<=100) cau10.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem<=150) cau15.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem<=200) cau20.setTextColor(Color.parseColor("#FFEB3B"));
            else if (diem<=250) cau25.setTextColor(Color.parseColor("#FFEB3B"));
            else                cau30.setTextColor(Color.parseColor("#FFEB3B"));
            btnxn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
}