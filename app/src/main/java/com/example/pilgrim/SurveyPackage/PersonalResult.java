package com.example.pilgrim.SurveyPackage;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilgrim.PersonalRD;
import com.example.pilgrim.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalResult extends AppCompatActivity implements TextWatcher {
    ArrayList<PersonalRD>personalList=new ArrayList<>();
    private FirebaseDatabase database;
    ResultAdapter resultAdapter;
    EditText edit_search;

    ArrayList<String> Filteredlist;
    ArrayList<String> unfilteredList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_result);

        RecyclerView recyclerView=findViewById(R.id.recycler_personal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter=new ResultAdapter(this,personalList);
        recyclerView.setAdapter(resultAdapter);

        edit_search=findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(this);

        resultAdapter.getFilter().filter(edit_search.getText().toString());
        database=FirebaseDatabase.getInstance();
        database.getReference("PersonalResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   personalList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PersonalRD personalRD = snapshot.getValue(PersonalRD.class);
                    personalList.add(personalRD);
                }


                resultAdapter.notifyDataSetChanged();//새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
