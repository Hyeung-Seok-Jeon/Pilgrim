package com.example.pilgrim.SurveyPackage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pilgrim.FirebaseRD_Data.PersonalRD;
import com.example.pilgrim.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalResult extends AppCompatActivity implements TextWatcher {
    ArrayList<PersonalRD>personalList=new ArrayList<>();
    private FirebaseDatabase database;
    ResultAdapter resultAdapter;
    EditText edit_search;
    ArrayList<PersonalRD> Filteredlist;
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
        Filteredlist=new ArrayList<>();
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