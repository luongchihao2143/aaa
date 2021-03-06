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

        list.add(new Question(1, "TP.H??? Ch?? Minh ??? mi???n n??o Vi???t Nam", "B???c", "Trung", "Nam", "Kh??ng c?? ????p ??n", "Nam"));

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
        tvstt.setText("C??u s???: "+socau);
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
        Log.d("S??? c??u hi???n t???i", diem+"");
        startActivity(i);
    }

    public void themdulieufirebase(){
        mData = FirebaseDatabase.getInstance().getReference();
        Question question = new Question(2, "1MB(Mega byte) b???ng:", "1024GB", "1024KB", "1000KB", "1000B","1024KB");
        mData.child("Question2").setValue(question);

        question = new Question(3, "????? c??i ?????t th??m c??c b??? Font trong m??i tr?????ng windows, ta d??ng ch???c n??ng: ", "Destop c???a Control Panel", "Regional Setting c???a Control Panel", "System c???a Control Panel", "Font  c???a Control Panel","Font  c???a Control Panel");
        mData.child("Question3").setValue(question);

        question = new Question(4, "C?? th??? h???i ph???c (restore) c??c t???p tin b??? xo?? sau khi nh???n ph??m Delete b???ng c??ch: ", "M??? Recycle Bin", "M??? Internet Explorer", "M??? My Computer", "M??? My Documents","M??? Recycle Bin");
        mData.child("Question4").setValue(question);

        question = new Question(5, "?????c ??i???m ch??nh c???a Virus m??y t??nh l??: ", "Ph?? ho???i", "L??y lan", "T??? nh??n b???n", "C??? 3 c??u A B C ?????u ????ng","C??? 3 c??u A B C ?????u ????ng");
        mData.child("Question5").setValue(question);

        question = new Question(6, "????? ch???n c???a s??? c???a ch????ng tr??nh c???n l??m vi???c trong Windows ta ch???n", "Nh???n ch???n bi???u t?????ng ch????ng tr??nh tr??n thanh Taskbar", "Nh???n gi??? ph??m Alt v?? g?? ph??m Tab cho ?????n khi ch???n ???????c ch????ng tr??nh", "(A) v?? (B) ????ng", "(A) v?? (B) sai","(A) v?? (B) ????ng");
        mData.child("Question6").setValue(question);

        question = new Question(7, "????? t???o bi???u t?????ng (Shotcut) c???a ch????ng tr??nh l??n m??n h??nh Desktop, b???m chu???t ph???i v??o t???p tin c???n t???o shortcut v?? ch???n:", "New/Folder", "Creat Shortcut", "M??? My Computer", "C??? (B) v?? (C) ?????u ????ng","Creat Shortcut");
        mData.child("Question7").setValue(question);

        question = new Question(50, "Ch????ng tr??nh Windows Explore d??ng ?????:", "Qu???n l?? v??n b???n", "Qu???n l?? th?? m???c ", "Qu???n l?? t???p tin, th?? m???c", "Ch???n n???n v??n b???n","Qu???n l?? t???p tin, th?? m???c");
        mData.child("Question50").setValue(question);

        question = new Question(49, "????? xo?? k?? t??? ?????ng tr?????c (con tr???) ??i???m nh??y, ta b???m ph??m:", "Page Up", "Page Down", "Delete", "Backspace","Backspace");
        mData.child("Question49").setValue(question);

        question = new Question(48, "Mu???n g??? b??? m???t ch????ng tr??nh ???? c??i ?????t v??o trong m??y. Sau khi v??o Start/Settings/Control panel, ta th???c hi???n nh?? sau: ","M??? File/Remove Program, ch???n ch????ng tr??nh c???n g??? b???, nh???n n??t Remove", "M??? New/ Accessories, ch???n ch????ng tr??nh c???n g??? b???, nh???n Remove", "M??? Program/Accessories, ch???n ch????ng tr??nh c???n g??? b???, nh???n n??t emove",
                "M??? Add or Remove program, ch???n ch????ng tr??nh c???n g??? b???, nh???n n??t Remove",
                "M??? Add or Remove program, ch???n ch????ng tr??nh c???n g??? b???, nh???n n??t Remove");
        mData.child("Question48").setValue(question);

        question = new Question(47, "????? th???c thi m???t ch????ng tr??nh trong Windows ta l??m nh?? sau:", "Nh??y k??p chu???t tr??i v??o File th???c thi", "B???m chu???t ph???i v??o File th???c thi, ch???n Open", "Nh??y ????n chu???t tr??i v??o File th???c thi, b???m ph??m Enter", "C??? 3 c??ch tr??n ?????u ????ng","C??? 3 c??ch tr??n ?????u ????ng");
        mData.child("Question47").setValue(question);

        question = new Question(46, "????? ????nh d???u ch???n m???t c??ch kh??ng li??n t???c c??c t???p tin hay th?? m???c trong Windows, trong khi nh???n chu???t tr??i (Left Click) v?? ta c???n gi??? ph??m:", "Shift", "Ctrl", "Alt", "Insert","Ctrl");
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