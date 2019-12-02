package com.example.pilgrim.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pilgrim.ManagerMode;
import com.example.pilgrim.R;

public class HomeFragment extends Fragment {
    private String managerPassword="123456";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton imgbtn_survey=root.findViewById(R.id.imgbtn_survey);
        ImageButton imgbtn_map=root.findViewById(R.id.imgbtn_map);
        ImageButton imgbtn_mannagerMode=root.findViewById(R.id.imgbtn_manager);
        imgbtn_mannagerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et=new EditText(getContext());
                FrameLayout container = new FrameLayout(getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
               /* alt_bld.setTitle("닉네임 변경").setMessage("변경할 닉네임을 입력하세요").setIcon(R.drawable.check_dialog_64).setCancelable(
                        false).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String inputPassword = et.getText().toString();
                                user_name.setText(value);
                            }
                        });*/

                AlertDialog alert = alt_bld.create();

                alert.show();

             /*   final EditText editText=new EditText(getContext());
                editText.setHint("비밀번호를 입력하세요");
                editText.setLayoutParams(getResources().getDimension());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("관리자 모드");
                dialog.setIcon(R.drawable.gallery);
                dialog.setView(editText);
                dialog.setNegativeButton("닫기", null);
                dialog.show();*/
                /*Intent intent=new Intent(getContext(), ManagerMode.class);
                startActivity(intent);*/
            }
        });
        return root;

    }

}