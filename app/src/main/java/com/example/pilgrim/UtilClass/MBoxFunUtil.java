package com.example.pilgrim.UtilClass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.pilgrim.MainActivity;

public class MBoxFunUtil extends AlertDialog {

    public MBoxFunUtil(Context context, String... message) {
        super(context); // != this.context = context;
        showMBox(null, message);
    }

    public MBoxFunUtil(Context context, String title, String message){
        super(context);
        showMBox(title, message);
    }

    void showMBox(String title, String... message) {
        Builder builder = new Builder(getContext());

        if(title == null)
            builder.setTitle("설문응답시간");
        else
            builder.setTitle(""+title.toString());

        builder.setMessage(message[0] + "분" + message[1] + " 초\n수고하셨습니다.");
        builder.setPositiveButton("확인",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);
                    }
                });
        builder.show();
    }
}
