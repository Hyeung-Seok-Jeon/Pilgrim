package com.example.pilgrim.SurveyPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilgrim.PersonalRD;
import com.example.pilgrim.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> implements Filterable {
    RadioButton radioButton;
    ArrayList<PersonalRD> unfilteredList;
    ArrayList<PersonalRD> filteredList;
    ArrayList<PersonalRD> personalList;
    Context context;

    public ResultAdapter(Context context, ArrayList<PersonalRD> list) {
        super();
        this.context = context;
        this.unfilteredList = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.personal_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_name.setText(filteredList.get(position).name);
        holder.text_p_choice1.setText(filteredList.get(position).my_choice1);
        holder.text_p_choice2.setText(filteredList.get(position).my_choice2);
        holder.text_p_choice3.setText(filteredList.get(position).my_choice3);
        holder.text_p_choice4.setText(filteredList.get(position).my_choice4);
        holder.text_p_choice5.setText(filteredList.get(position).my_choice5);


    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                //에디트 텍스트에 써있는값을 불러옴 
                if(charString.isEmpty()){
                    filteredList=unfilteredList; //에디트텍스트에 아무것도 안써져있다면 모든 자료를 보여주기
                }else{
                    ArrayList<PersonalRD> filteringList=new ArrayList<>();  //필터링중일떄 ex)'전ㅎ' 일때 보여줄 리스트
                    for(PersonalRD personalRD:unfilteredList){
                        if(personalRD.name.toLowerCase().contains(charString.toLowerCase()))
                            filteringList.add(personalRD);
                    }
                    filteredList=filteringList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList=(ArrayList<PersonalRD>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
