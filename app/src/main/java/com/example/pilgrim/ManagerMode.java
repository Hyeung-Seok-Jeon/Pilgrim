package com.example.pilgrim;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import com.example.pilgrim.FirebaseRD_Data.ImageRD;
import com.example.pilgrim.SurveyPackage.PersonalResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ManagerMode extends AppCompatActivity {
    Button btn_PictureChoice,btn_PictureUpload,btn_Notification,btn_Survey_Make,btn_personal_result;
    private String filename;
    private String imagePath;
    int GALLERY_CODE=0;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference myRef,myRef2;
    private String deleteSurveyConfirm="삭제확인";
    @SuppressLint("ObsoleteSdkInt")
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_mode);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//정방향 세로로 완전히 고정,회전불가
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("GalleryUris");
        myRef2=database.getReference("Question");
        storage=FirebaseStorage.getInstance();
        String[] PERMISSIONS ={
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        if(!hasPermissions(this, PERMISSIONS)){
            //권한요청
            ActivityCompat.requestPermissions(this, PERMISSIONS,1);
        }

        btn_PictureChoice=findViewById(R.id.btn_pictureChoice);
        btn_PictureUpload=findViewById(R.id.btn_gallleryUpdate);
        btn_Notification=findViewById(R.id.btn_manger_noti);
        btn_Survey_Make=findViewById(R.id.btn_survey_make);
        btn_personal_result=findViewById(R.id.btn_p_result);


        btn_PictureChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,GALLERY_CODE);
            }
        });
        btn_PictureUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadFile(imagePath);
                }catch(NullPointerException ignored){

                }
            }
        });
        btn_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NotiRegister.class);
                startActivity(intent);

            }
        });
        btn_Survey_Make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et=new EditText(ManagerMode.this);
                final FrameLayout container = new FrameLayout(ManagerMode.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                et.setHint("\"삭제확인\" 입력");
                et.setLayoutParams(params);
                container.addView(et);
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(ManagerMode.this,R.style.Theme_AppCompat_Light_Dialog);
                alt_bld.setTitle("설문조사 확인").setMessage("이전 설문조사를 삭제하시겠습니까?").setIcon(R.drawable.managermodepwdicon).setCancelable(
                        false).setView(container).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (deleteSurveyConfirm.equals(et.getText().toString())){
                                    Intent intent=new Intent(ManagerMode.this,SurveyRegister.class);
                                    startActivity(intent);
                                    myRef2.setValue(null);
                                }else {
                                    Toast.makeText(ManagerMode.this, "문장이 다릅니다.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.show();

            }
        });
        btn_personal_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PersonalResult.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            imagePath=getPath(data.getData());


        }
    }
    public String getPath(Uri uri){
        String[] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor=cursorLoader.loadInBackground();
        assert cursor != null;
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }
    private void uploadFile(String uri) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("업로드중...");
        if (imagePath != null)
            progressDialog.show();
        else
            Toast.makeText(this, "먼저 이미지를 선택해주세요", Toast.LENGTH_SHORT).show();


        StorageReference storageRef = storage.getReferenceFromUrl("gs://pilgrim-16e7a.appspot.com/");
        Uri file = Uri.fromFile(new File(uri));
        filename = file.getLastPathSegment();
        StorageReference riversRef = storageRef.child("images/" + filename);
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                Toast.makeText(getApplicationContext(), "데이터베이스 업로드 완료!", Toast.LENGTH_SHORT).show();
                imgDeliver(filename);


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");

            }
        });


    }
    //firebase storage에서 image uri를 가져와서 데이터베이스에 넣음
    private void imgDeliver(String name){
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://pilgrim-16e7a.appspot.com/").child("images");
        storageRef.child(name).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri downloadUri=task.getResult();
                ImageRD imageRD =new ImageRD();
                assert downloadUri != null;
                imageRD.imageUrI=downloadUri.toString();
               myRef.push().setValue(imageRD);

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagerMode.this, "Gallery 사진 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}




