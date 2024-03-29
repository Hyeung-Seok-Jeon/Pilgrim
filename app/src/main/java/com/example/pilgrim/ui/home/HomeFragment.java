package com.example.pilgrim.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilgrim.GpsTracker;
import com.example.pilgrim.ManagerMode;
import com.example.pilgrim.OpenApiParse.OpenApiParsingTask;
import com.example.pilgrim.R;

import com.example.pilgrim.SurveyEnter;

import com.example.pilgrim.RankchkTask;
import com.example.pilgrim.FirebaseRD_Data.mainNotiRD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
   private GpsTracker gpsTracker;
    private SharedPreferences getSharedpr;
    private TextView title,body;
    private FirebaseDatabase database;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private List<mainNotiRD> mainNotiRDS=new ArrayList<>();

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }


        return true;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getSharedpr= this.getContext().getSharedPreferences("PrefName", MODE_PRIVATE);
        getSharedpr = Objects.requireNonNull(this.getContext()).getSharedPreferences("PrefName", MODE_PRIVATE);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        title=root.findViewById(R.id.txt_noti_title);
        body=root.findViewById(R.id.txt_noti_body);


        database=FirebaseDatabase.getInstance();
        database.getReference("main").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainNotiRDS.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mainNotiRD mainNotiRD=snapshot.getValue(mainNotiRD.class);
                    mainNotiRDS.add(mainNotiRD);
                }
                try {
                    title.setText("제목 : "+mainNotiRDS.get(mainNotiRDS.size() - 1).title);
                    body.setText(mainNotiRDS.get(mainNotiRDS.size() - 1).body);
                }catch (Exception e){

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //미세먼지 확인하는 코드
        new OpenApiParsingTask(root.getContext(), (TextView)root.findViewById(R.id.dustCheckText)).execute();

        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            //권한요청
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS, 1);
        }


        ImageButton imgbtn_survey=root.findViewById(R.id.imgbtn_survey);
        ImageButton imgbtn_map=root.findViewById(R.id.imgbtn_map);
        ImageButton imgbtn_mannagerMode=root.findViewById(R.id.imgbtn_manager);





        imgbtn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SurveyEnter.class));
            }
        });
        imgbtn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gpsTracker = new GpsTracker(getActivity());
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                Toast.makeText(getActivity(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=37.4180983,126.6733512"));
                startActivity(intent);
            }
        });
        imgbtn_mannagerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String idchk = getSharedpr.getString("ID", "");

                RankchkTask task = new RankchkTask(idchk);
                try {
                    String apply = task.execute().get();
                    if (apply.equals("1")) {
                        Intent intent = new Intent(getContext(), ManagerMode.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "관리자가 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }


                /*final AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog);
                alt_bld.setTitle("관리자 권한").setIcon(R.drawable.managermodepwdicon).setCancelable(
                        true).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String inputPassword = et.getText().toString();
                                if (managerPassword.equals(inputPassword)){

                                }else {
                                    Toast.makeText(getContext(), "비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                AlertDialog alert = alt_bld.create();

                alert.show();*/
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
