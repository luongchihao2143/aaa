package com.ps14498.ailatrieuphu.Database;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ps14498.ailatrieuphu.Model.Question;

import java.util.ArrayList;

public class DBQuestion {
    Context context;
    ArrayList<Question> list;
    DatabaseReference mdata = FirebaseDatabase.getInstance().getReference();

    public DBQuestion(Context context, ArrayList<Question> list){
        this.context = context;
        this.list = list;
    }

   public void nhapdulieu(){
   }
}
