package com.example.pilgrim.ui.gallery;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pilgrim.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private Integer[] mThumbIds={R.drawable.peng1,R.drawable.peng2  };
    ArrayList<String> galleryList=new ArrayList<String>();
    Button btn_prev,btn_next;
    int i,count=0;
    ImageView imageView;
    String galleryUri;
    TextView test;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery2, container, false);
        btn_prev=root.findViewById(R.id.btn_prev);
        btn_next=root.findViewById(R.id.btn_next);
        imageView=root.findViewById(R.id.img_gallery);
        test=root.findViewById(R.id.textView3);
        Bundle extra=getArguments();
        if(extra!=null){

           galleryUri=extra.getString("uri");
        }
        test.setText(galleryUri);
        try {
            if(galleryUri!=null) {
                galleryList.add(galleryUri);
                i=galleryList.size();
                count=i-1;
                Glide.with(getActivity()).load(galleryList.get(0)).into(imageView);
            }
            else
                Toast.makeText(getActivity(), "업로드 이미지가 하나도 없습니다.", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){

        }


        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(count>-1) {
                        count--;
                        Glide.with(getActivity()).load(galleryList.get(count)).into(imageView);
                    }
                    else {
                        Toast.makeText(getActivity(), "처음입니다.", Toast.LENGTH_SHORT).show();
                        count=0;
                    }
                }catch(NullPointerException e){

                }

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count+1>=galleryList.size()){
                    Toast.makeText(getActivity(), "마지막입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    count++;
                    Glide.with(getActivity()).load(galleryList.get(count)).into(imageView);

                }

            }
        });
       /* GridView gridView=root.findViewById(R.id.gridview);
        gridView.setAdapter(new GalleryAdapter(getContext()));

        galleryList.add(galleryUri);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View Bigview = View.inflate(getActivity(), R.layout.fragment_gallery_big_size, null);
                ImageView bigPitcure = Bigview.findViewById(R.id.imageView4);
                bigPitcure.setImageResource(mThumbIds[position]);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("사진");
                dialog.setIcon(R.drawable.gallery);
                dialog.setView(Bigview);
                dialog.setNegativeButton("닫기", null);
                dialog.show();

            }
        });*/

return root;
    }
    public class GalleryAdapter extends BaseAdapter {
        private Context mContext;

        public GalleryAdapter(Context c){
            mContext=c;
        }
        public int getCount(){
            return mThumbIds.length;
        }
        public Object getItem(int position){
            return  null;
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300,300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }else{
                imageView=(ImageView)convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }


    }
}
