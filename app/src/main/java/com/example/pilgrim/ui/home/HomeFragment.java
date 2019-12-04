package com.example.pilgrim.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
            public void onClick(final View v) {
                final EditText et=new EditText(getContext());
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et.setHint("비밀번호를 입력하세요");
                final FrameLayout container = new FrameLayout(getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog);
                alt_bld.setTitle("관리자 권한").setIcon(R.drawable.managermodepwdicon).setCancelable(
                        true).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String inputPassword = et.getText().toString();
                                if (managerPassword.equals(inputPassword)){
                                    Intent intent=new Intent(getContext(),ManagerMode.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getContext(), "비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                AlertDialog alert = alt_bld.create();

                alert.show();
                /*    Intent intent=new Intent(getActivity(), ManagerAuthority.class);
                    startActivity(intent);*/
            }
        });
        return root;

    }

}
/* final EditText et=new EditText(getContext());
                final FrameLayout container = new FrameLayout(getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog);
                alt_bld.setTitle("닉네임 변경").setMessage("변경할 닉네임을 입력하세요").setIcon(R.drawable.managermodepwdicon).setCancelable(
                        false).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String inputPassword = et.getText().toString();




                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert = alt_bld.create();

                alert.show();*/

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