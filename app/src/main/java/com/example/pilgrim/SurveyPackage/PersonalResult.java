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
    List<PersonalRD>personalList=new ArrayList<>();
    private FirebaseDatabase database;
//    ResultAdapter resultAdapter;
    EditText edit_search;

    List<PersonalRD> Filteredlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_result);

        RecyclerView recyclerView=findViewById(R.id.recycler_personal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        resultAdapter=new ResultAdapter();
//        recyclerView.setAdapter(resultAdapter);

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
//                    filteredList.add(personalRD.name);
                }


//                resultAdapter.notifyDataSetChanged();//새로고침
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
//            resultAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

//    class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> implements Filterable {
        RadioButton radioButton;



//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Context context = parent.getContext();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.personal_list, parent, false);
//            ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
////            holder.text_name.setText(personalList.get(position));
//            holder.text_p_choice1.setText(personalList.get(position).my_choice1);
//            holder.text_p_choice2.setText(personalList.get(position).my_choice2);
//            holder.text_p_choice3.setText(personalList.get(position).my_choice3);
//            holder.text_p_choice4.setText(personalList.get(position).my_choice4);
//            holder.text_p_choice5.setText(personalList.get(position).my_choice5);
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return personalList.size();
//        }

//        @Override
////        public Filter getFilter() {
////            return new Filter() {
////                @Override
////                protected FilterResults performFiltering(CharSequence constraint) {
////                    String charString=constraint.toString();
////                    if(charString.isEmpty()){
////                        Filteredlist=personalList;
////                    }else{
////                        ArrayList<String> filteringList=new ArrayList<>();
////                        for(List<PersonalRD>:personalList){
////                            if(name.toLowerCase().contains(charString.toLowerCase()))
////                                filteringList.add(name);
////                        }
////                        filteredList=filteringList;
////                    }
////                    FilterResults filterResults=new FilterResults();
////                    filterResults.values=filteredList;
////                    return filterResults;
////                }
////
////                @Override
////                protected void publishResults(CharSequence constraint, FilterResults results) {
////                    filteredList=(ArrayList<String>)results.values;
////                    notifyDataSetChanged();
////                }
////            };
////        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_name,text_p_choice1,text_p_choice2,text_p_choice3,text_p_choice4,text_p_choice5;


            ViewHolder(View view) {
                super(view);
                text_name=view.findViewById(R.id.pl_txt_name);
                text_p_choice1=view.findViewById(R.id.pl_txt1);
                text_p_choice2=view.findViewById(R.id.pl_txt2);
                text_p_choice3=view.findViewById(R.id.pl_txt3);
                text_p_choice4=view.findViewById(R.id.pl_txt4);
                text_p_choice5=view.findViewById(R.id.pl_txt5);

            }
        }


    }
//}
