package com.example.pilgrim.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.txt_noti);
        ImageButton imgbtn_survey=root.findViewById(R.id.imgbtn_survey);
        ImageButton imgbtn_map=root.findViewById(R.id.imgbtn_map);
        ImageButton imgbtn_mannagerMode=root.findViewById(R.id.imgbtn_manager);
        imgbtn_mannagerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManagerMode.class);
                startActivity(intent);
            }
        });
        return root;

    }

}